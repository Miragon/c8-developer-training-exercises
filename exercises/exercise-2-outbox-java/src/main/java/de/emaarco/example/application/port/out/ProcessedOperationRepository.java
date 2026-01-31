package de.emaarco.example.application.port.out;

import de.emaarco.example.domain.OperationId;

public interface ProcessedOperationRepository {
    boolean existsById(OperationId operationId);
    void save(OperationId operationId);
}
