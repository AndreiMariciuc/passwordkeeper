package com.familypassword.repository;

import com.familypassword.models.Secret;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface SecretRepository extends Neo4jRepository<Secret, Long> {
}
