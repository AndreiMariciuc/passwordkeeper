package com.familypassword.repository;

import com.familypassword.models.Group;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface GroupRepository extends Neo4jRepository<Group, String> {

}
