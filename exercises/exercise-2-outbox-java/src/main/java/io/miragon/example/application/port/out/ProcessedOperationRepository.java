package io.miragon.example.application.port.out;

import io.miragon.example.domain.OperationId;

public interface ProcessedOperationRepository {
    boolean existsById(OperationId operationId);
    void save(OperationId operationId);
}
