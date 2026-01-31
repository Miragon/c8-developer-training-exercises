package de.emaarco.example.adapter.out.db.operation;

import de.emaarco.example.application.port.out.ProcessedOperationRepository;
import de.emaarco.example.domain.OperationId;
import org.springframework.stereotype.Component;

@Component
public class ProcessedOperationPersistenceAdapter implements ProcessedOperationRepository {

    private final ProcessedOperationJpaRepository repository;

    public ProcessedOperationPersistenceAdapter(ProcessedOperationJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean existsById(OperationId operationId) {
        throw new UnsupportedOperationException("TODO: Check if operation already processed");
        // HINT: Use repository.existsById(operationId.value())
        // HINT: Returns true if operation was already completed, false otherwise
    }

    @Override
    public void save(OperationId operationId) {
        throw new UnsupportedOperationException("TODO: Record operation as completed");
        // HINT: Create ProcessedOperationEntity with operationId.value()
        // HINT: Save to repository using repository.save()
        // HINT: ProcessedOperationEntity will automatically set processedAt timestamp
    }
}
