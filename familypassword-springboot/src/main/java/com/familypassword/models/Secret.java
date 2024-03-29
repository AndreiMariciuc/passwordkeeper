package com.familypassword.models;

import lombok.Data;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@Data
public class Secret {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
