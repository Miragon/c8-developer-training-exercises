package io.miragon.example.adapter.out.db.operation

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

/**
 * Idempotency table that tracks completed operations.
 * Prevents duplicate processing when Zeebe retries jobs.
 */
@Entity
@Table(name = "processed_operations")
data class ProcessedOperationEntity(

    @Id
    val dummy: String = ""

    // TODO: Should have at least a field, identifying the operation as well as a timestamp
)
