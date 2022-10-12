package com.wizeline.maven.learningjava.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.wizeline.gradle.learningjava.model.PaisesDTO;

public interface RepositorioPais extends MongoRepository<PaisesDTO,String> {
}
