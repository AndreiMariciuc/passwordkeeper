package com.familypassword.models;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node
@Data
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String token;
    private String email;
    private String officialName;
    private String imageUrl = "img/placeholder";

    @Relationship(type = "PART_OF")
    private List<Group> groups;

    @Relationship(type = "HAS_PERMISSION", direction = Relationship.Direction.OUTGOING)
    private List<Secret> secrets;
}
