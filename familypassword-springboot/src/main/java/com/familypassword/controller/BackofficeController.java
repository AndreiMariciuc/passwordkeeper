package com.familypassword.controller;

import com.familypassword.models.Group;
import com.familypassword.models.Secret;
import com.familypassword.models.User;
import com.familypassword.repository.GroupRepository;
import com.familypassword.repository.SecretRepository;
import com.familypassword.repository.UserRepository;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class BackofficeController {

    @Resource
    private UserRepository userRepository;

    @Resource
    private SecretRepository secretRepository;

    @Resource
    private GroupRepository groupRepository;

    @GetMapping("/all-users")
    List<User> getAllUser() {
        return userRepository.findAll();
    }

    @GetMapping("/secrets")
    List<Secret> getAllSecrets() {
        return secretRepository.findAll();
    }

    @GetMapping("/groups")
    List<Group> getGroups() {
        return groupRepository.findAll();
    }
}
