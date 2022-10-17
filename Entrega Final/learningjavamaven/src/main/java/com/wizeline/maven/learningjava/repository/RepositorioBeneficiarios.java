package com.wizeline.maven.learningjava.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.wizeline.maven.learningjava.model.BeneficiariosDTO;

public interface RepositorioBeneficiarios extends MongoRepository<BeneficiariosDTO,String> {
}
