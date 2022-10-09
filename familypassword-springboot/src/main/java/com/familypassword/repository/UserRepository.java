package com.familypassword.repository;

import com.familypassword.models.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.Optional;

public interface UserRepository extends Neo4jRepository<User, Long> {

    @Query("MATCH(user:User) WHERE user.officialName=$officialName AND user.token=$token RETURN user")
    Optional<User> findByNameAndToken(String officialName, String token);
}
