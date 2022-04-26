package com.familypassword.models;

import lombok.Data;

import java.util.List;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

@Node
@Data
public class Group {

    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    private String token;

    private String name;

    @Relationship(type = "HAS_MEMBERS", direction = Relationship.Direction.OUTGOING)
    private List<User> members;

    @Relationship(type = "IS_OWNED", direction = Relationship.Direction.OUTGOING)
    private User admin;

    @Relationship(type = "KEEP_SECRETS", direction = Relationship.Direction.OUTGOING)
    private List<Secret> secrets;
}
