package com.example.black_horse.infrastructure.repository;

import com.example.black_horse.infrastructure.repository.entity.CompensateEntity;
import org.springframework.data.repository.CrudRepository;

public interface CompensateRepository extends CrudRepository<CompensateEntity, String> {
    Long countByContractId(String contractId);
}
