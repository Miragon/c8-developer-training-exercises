# 🔄 Exercise 2: Outbox & Idempotency Patterns (Kotlin)

Master distributed transaction patterns with Camunda 8.

> 💡 **Java version available**: `exercises/exercise-2-outbox-java`

## 🎯 What You'll Build

Two critical patterns for reliable distributed systems:

1. **Outbox Pattern** - Never lose a message to Zeebe
2. **Idempotency Pattern** - Handle retries without duplicate side effects

## 🚀 Getting Started

### 1. Start Infrastructure

```bash
cd ../../stack
docker-compose up -d
```

### 2. Run Application

```bash
cd exercises/exercise-2-outbox-kotlin
../../gradlew bootRun
```

App runs on: http://localhost:8081

### 3. Test It Out

```bash
# Subscribe to newsletter
curl -X POST http://localhost:8081/api/subscriptions/subscribe \
  -H "Content-Type: application/json" \
  -d '{"email": "test@example.com", "name": "Test User", "newsletterId": "550e8400-e29b-41d4-a716-446655440000"}'
```

## 📝 Part 1: Outbox Pattern (30-45 min)

### Your Tasks

Implement 4 files to reliably deliver messages:

1. **ProcessMessageEntity.java** - Design the outbox table
    - Fields: messageId (random uuid), messageName, correlationId, variables, status, retryCount

2. **ProcessMessageJpaRepository.kt** - Add pessimistic locking
    - Implement `findFirstByStatusWithLock()` method

3. **ProcessMessagePersistenceAdapter.kt** - Save to DB instead of direct Zeebe
    - Write messages with PENDING status

4. **ProcessEngineOutboxScheduler.kt** - Background message sender
    - Process outbox every 200ms

### 🎯 Key Concept

DB writes + outbox writes = **same transaction** = guaranteed delivery! 🎉

### ✅ Test It

```sql
-- Watch messages flow through
SELECT *
FROM process_message
ORDER BY created_at DESC;
```

Status should change: `PENDING` → `SENT`

## 📝 Part 2: Idempotency Pattern (20-30 min)

### Your Tasks

Prevent duplicate operations from job retries:

1. **ProcessedOperationEntity.kt** - Track completed operations
    - Composite key: subscriptionId + elementId

2. **ProcessedOperationPersistenceAdapter.kt** - Check & record
    - `existsById()` - already processed?
    - `save()` - mark as done

3. **Update 3 services** - Add Check-Execute-Record pattern
    - SendConfirmationMailService.kt
    - SendWelcomeMailService.kt
    - IncrementSubscriptionCounterService.kt

### 🎯 Key Concept

**Check** if processed → **Execute** business logic → **Record** completion = no duplicates! 🛡️

### ✅ Test It

```sql
-- See what's been processed
SELECT *
FROM processed_operations
ORDER BY processed_at DESC;
```

Kill the app mid-flight and restart - no duplicate emails or counter increments!

## 🔧 Tools & Access

- **Backend**: http://localhost:8081
- **Camunda Operate**: http://localhost:9081 (demo/demo)
- **PostgreSQL**: localhost:5432 (admin/admin)

## 💡 Hints in the Code

Each TODO file has detailed hints - check the comments! 💬

## ✅ Success Criteria

- Messages reach Zeebe even if app crashes ✅
- Job retries don't cause duplicates ✅
- Counter increments exactly once ✅
- Process completes successfully ✅

---

**Challenge mode**: Try deliberately crashing the app to test resilience! 💥
