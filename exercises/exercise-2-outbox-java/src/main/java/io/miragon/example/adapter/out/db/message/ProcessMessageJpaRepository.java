package io.miragon.example.adapter.out.db.message;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProcessMessageJpaRepository extends JpaRepository<ProcessMessageEntity, UUID> {

    // TODO: Implement query with pessimistic locking to support concurrent schedulers
    // HINT: Use @Lock(LockModeType.PESSIMISTIC_WRITE) for row-level locking
    // HINT: Use @QueryHints with @QueryHint(name = "jakarta.persistence.lock.timeout", value = "0") for SKIP LOCKED behavior
    // HINT: Query: SELECT m FROM process_message m WHERE m.status = :status ORDER BY m.createdAt
    // HINT: Return type: ProcessMessageEntity (nullable - returns null if no pending messages)
    // HINT: Method signature: ProcessMessageEntity findFirstByStatusWithLock(MessageStatus status);
    //
    // Why pessimistic locking?
    // - Multiple scheduler instances may run concurrently
    // - Each should pick a different message to avoid duplicate sends
    // - SKIP LOCKED ensures instant failure instead of waiting for locks
}
