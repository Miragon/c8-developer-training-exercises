package de.emaarco.example.adapter.out.db.message

import jakarta.persistence.LockModeType
import jakarta.persistence.QueryHint
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.QueryHints
import java.util.UUID

interface ProcessMessageJpaRepository : JpaRepository<ProcessMessageEntity, UUID> {

    // TODO: Implement query with pessimistic locking to support concurrent schedulers
    // HINT: Use @Lock(LockModeType.PESSIMISTIC_WRITE) for row-level locking
    // HINT: Use @QueryHints with "jakarta.persistence.lock.timeout" = "0" for SKIP LOCKED behavior
    // HINT: Query: SELECT m FROM process_message m WHERE m.status = :status ORDER BY m.createdAt
    // HINT: Return type: ProcessMessageEntity? (nullable - returns null if no pending messages)
    // HINT: Method signature: fun findFirstByStatusWithLock(status: MessageStatus): ProcessMessageEntity?
    //
    // Why pessimistic locking?
    // - Multiple scheduler instances may run concurrently
    // - Each should pick a different message to avoid duplicate sends
    // - SKIP LOCKED ensures instant failure instead of waiting for locks
}
