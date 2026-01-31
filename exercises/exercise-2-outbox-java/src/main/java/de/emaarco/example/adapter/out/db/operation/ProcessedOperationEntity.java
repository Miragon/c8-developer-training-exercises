package de.emaarco.example.adapter.out.db.operation;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Idempotency table that tracks completed operations.
 * Prevents duplicate processing when Zeebe retries jobs.
 */
@Entity
@Table(name = "processed_operations")
public class ProcessedOperationEntity {

    @Id
    private String dummy = "";

    // TODO: Should have at least a field, identifying the operation as well as a timestamp

    public ProcessedOperationEntity() {
    }

    public ProcessedOperationEntity(String dummy) {
        this.dummy = dummy;
    }

    public String getDummy() {
        return dummy;
    }

    public void setDummy(String dummy) {
        this.dummy = dummy;
    }
}
