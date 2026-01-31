package de.emaarco.example.adapter.out.db.operation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessedOperationJpaRepository extends JpaRepository<ProcessedOperationEntity, String> {
}
