package de.emaarco.example.adapter.out.db.operation

import de.emaarco.example.application.port.out.ProcessedOperationRepository
import de.emaarco.example.domain.OperationId
import org.springframework.stereotype.Component

@Component
class ProcessedOperationPersistenceAdapter(
    private val repository: ProcessedOperationJpaRepository
) : ProcessedOperationRepository {

    override fun existsById(operationId: OperationId): Boolean {
        TODO("Check if operation already processed")
        // HINT: Use repository.existsById(operationId.value)
        // HINT: Returns true if operation was already completed, false otherwise
    }

    override fun save(operationId: OperationId) {
        TODO("Record operation as completed")
        // HINT: Create ProcessedOperationEntity with operationId.value
        // HINT: Save to repository using repository.save()
        // HINT: ProcessedOperationEntity will automatically set processedAt timestamp
    }
}
