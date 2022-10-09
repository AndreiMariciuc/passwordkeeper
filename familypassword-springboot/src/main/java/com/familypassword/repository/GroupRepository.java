package com.familypassword.repository;

import com.familypassword.models.Group;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface GroupRepository extends Neo4jRepository<Group, String> {
    @Query("MATCH (g:Group)-[:IS_OWNED]-> (u:User) WHERE u.officialName=$userOfficialName RETURN g.token " +
            "UNION " +
            "MATCH (g:Group) -[:HAS_MEMBERS]-> (u:User) WHERE u.officialName=$userOfficialName RETURN g.token")
    List<String> findAllGroups(String userOfficialName);
}
