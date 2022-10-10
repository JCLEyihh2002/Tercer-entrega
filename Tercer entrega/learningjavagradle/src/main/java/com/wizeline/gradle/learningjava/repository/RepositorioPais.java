package com.wizeline.gradle.learningjava.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.wizeline.gradle.learningjava.model.PaisesDTO;

public interface RepositorioPais extends MongoRepository<PaisesDTO,String> {
}
