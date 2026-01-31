# Exercise 2: Outbox and Idempotency Patterns (Java Version)

Learn to implement distributed transaction patterns with Camunda 8 (Zeebe) using Java.

> **Note**: This is the Java version. A Kotlin version is available at `exercises/exercise-2-outbox`.

## Learning Objectives

1. **Outbox Pattern** - Ensure reliable message delivery to Zeebe
2. **Idempotency Pattern** - Prevent duplicate processing of retried jobs

## Prerequisites

- Complete Exercise 1 (BPMN testing)
- Understanding of transactions and distributed systems
- Docker for infrastructure

## Quick Start

### 1. Start Infrastructure

Use the root stack directory:

```bash
cd ../../stack
docker-compose up -d
```

This starts:
- PostgreSQL database
- Camunda Platform 8.8 (Zeebe + Operate + Tasklist)
- Elasticsearch

### 2. Run the Application

```bash
cd exercises/exercise-2-outbox-java
../../gradlew bootRun
```

Application runs on: http://localhost:8081

### 3. Test with Bruno API

Use Bruno API files from the root `bruno/` directory, or use curl:

```bash
# Subscribe to newsletter
curl -X POST http://localhost:8081/api/subscriptions/subscribe \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "name": "Test User",
    "newsletterId": "550e8400-e29b-41d4-a716-446655440000"
  }'

# Confirm subscription
curl -X POST http://localhost:8081/api/subscriptions/{subscriptionId}/confirm
```

## Exercise Structure

### Part 1: Outbox Pattern (30-45 min)

Implement reliable message delivery through database outboxing.

#### Files to Implement

1. **ProcessMessageEntity.kt** - Outbox table structure
   - Define fields: messageId, messageName, correlationId, variables, status, retryCount, timestamps
   - Use JPA annotations for entity mapping

2. **ProcessMessageJpaRepository.kt** - Query with pessimistic locking
   - Implement `findFirstByStatusWithLock()` method
   - Use `@Lock(LockModeType.PESSIMISTIC_WRITE)`
   - Use QueryHints for SKIP LOCKED behavior

3. **ProcessMessagePersistenceAdapter.kt** - Save messages to DB
   - Replace direct Zeebe calls with database writes
   - Serialize process variables to JSON
   - Save messages with PENDING status

4. **ProcessEngineOutboxScheduler.kt** - Background message sender
   - Implement `@Scheduled` method (200ms fixed delay)
   - Process messages one-at-a-time with transaction boundaries
   - Handle Zeebe failures with retry counter

#### Key Concepts

- **Atomic write** to DB + outbox table in same transaction
- **Background scheduler** processes outbox asynchronously
- **Pessimistic locking** prevents concurrent schedulers from picking same message
- **Retry mechanism** with retry counter for failed sends
- **Message deduplication** using messageId in Zeebe

### Part 2: Idempotency Pattern (20-30 min)

Prevent duplicate side effects from Zeebe job retries.

#### Files to Implement

1. **ProcessedOperationEntity.kt** - Track completed operations
   - Define composite key: `operationId` (subscriptionId-elementId)
   - Add timestamp for audit trail

2. **ProcessedOperationPersistenceAdapter.kt** - Check/record operations
   - Implement `existsById()` - check if operation processed
   - Implement `save()` - record operation completion

3. **Service Layer** - Add Check-Execute-Record pattern
   - `SendConfirmationMailService.kt`
   - `SendWelcomeMailService.kt`
   - `IncrementSubscriptionCounterService.kt`

#### Key Concepts

- **OperationId**: Composite key (subscriptionId-elementId)
- **Check → Execute → Record** in single transaction
- **Early return** if operation already processed
- **Prevents**: Duplicate emails, counter increments, etc.

## Testing Your Implementation

### Test Outbox Pattern

1. Subscribe to newsletter via API
2. Check `process_message` table:
   ```sql
   SELECT * FROM process_message ORDER BY created_at DESC;
   ```
3. Watch status change: `PENDING` → `SENT`
4. Verify process started in Operate UI

### Test Idempotency Pattern

1. Subscribe and let process run
2. Check `processed_operations` table:
   ```sql
   SELECT * FROM processed_operations ORDER BY processed_at DESC;
   ```
3. Kill application mid-flight (force job retry)
4. Restart application
5. Verify no duplicate emails or counter increments

## Hints

### Outbox Scheduler Loop

```kotlin
@Scheduled(fixedDelay = 200)
fun sendMessages() {
    while (processNextMessage()) {
        // Keep processing until no messages left
    }
}
```

### Pessimistic Locking

```kotlin
@Lock(LockModeType.PESSIMISTIC_WRITE)
@QueryHints(QueryHint(name = "jakarta.persistence.lock.timeout", value = "0"))
@Query("SELECT m FROM process_message m WHERE m.status = :status ORDER BY m.createdAt")
fun findFirstByStatusWithLock(status: MessageStatus): ProcessMessageEntity?
```

### Message Deduplication

```kotlin
val messageId = "${correlationId}-${messageName}"
// Prevents Zeebe from processing same message twice
```

### Idempotency Check

```kotlin
if (processedOperationRepository.existsById(operationId)) {
    log.info { "Already processed" }
    return // Early exit
}
// ... execute business logic ...
processedOperationRepository.save(operationId)
```

## Infrastructure

### Access Points
- **Backend API**: http://localhost:8081
- **Operate UI**: http://localhost:9081 (demo/demo)
- **PostgreSQL**: localhost:5432 (admin/admin)

### Using Root Infrastructure

This exercise uses shared infrastructure from the root directory:
- `../../stack/docker-compose.yml` - Infrastructure stack
- `../../bruno/` - API testing files

Both exercise-1 and exercise-2 can run against the same infrastructure.

## Success Criteria

✅ Messages reliably reach Zeebe even if app crashes
✅ Duplicate job executions don't cause duplicate side effects
✅ Counter increments exactly once despite retries
✅ Process completes successfully in Operate

## Distributed Transaction Problems Solved

| Problem | Outbox | Idempotency |
|---------|--------|-------------|
| Premature execution (process starts before DB commits) | ✅ | - |
| Out-of-sync states (DB fails after notifying Zeebe) | ✅ | - |
| Conflicting data (tasks execute out of order) | ✅ | - |
| Duplicate calls (retries create duplicates) | - | ✅ |
| Network issues (job completion lost) | ✅ | ✅ |

See `CHALLENGES.md` for detailed explanations of distributed transaction challenges.

---

**Need help?** Check the detailed TODO comments in each file for step-by-step implementation hints.
