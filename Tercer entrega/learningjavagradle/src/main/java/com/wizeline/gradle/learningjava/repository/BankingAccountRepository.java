package com.wizeline.gradle.learningjava.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.wizeline.gradle.learningjava.model.BankAccountDTO;

public interface BankingAccountRepository extends MongoRepository<BankAccountDTO, Long> {
}
