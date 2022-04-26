package com.familypassword.service;

import com.familypassword.dto.GroupDto;
import com.familypassword.dto.SecretDto;
import com.familypassword.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto register(String name, String email);

    GroupDto createGroup(Long id, String groupName);

    SecretDto addSecret(Long id, Long parentId, SecretDto secretDto);

    List<SecretDto> getSecrets(Long id);

    SecretDto updateSecret(Long id, SecretDto passwordDto);

    void deleteSecret(Long id, Long secretId);

    UserDto isRegister(String username, String token);
}
