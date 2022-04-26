package com.familypassword.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.data.neo4j.core.schema.Node;

@EqualsAndHashCode(callSuper = true)
@Node
@Data
public class Password extends Secret {

    private String link;
    private String username;
    private String password;
}
