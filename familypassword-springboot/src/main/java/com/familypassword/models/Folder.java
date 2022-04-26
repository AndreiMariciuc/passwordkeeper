package com.familypassword.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@EqualsAndHashCode(callSuper = true)
@Node
@Data
public class Folder extends Secret {

    @Relationship(type = "CONTAINS", direction = OUTGOING)
    private Set<Secret> children;
}
