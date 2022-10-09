package com.familypassword.service;

import com.familypassword.dto.SecretDto;
import com.familypassword.dto.UserDto;
import com.familypassword.models.Group;

import java.util.List;

public interface UserService {

    UserDto register(String name, String email);

    Group createGroup(Long id, String groupName);

    SecretDto addSecret(Long id, Long parentId, SecretDto secretDto);

    List<SecretDto> getSecrets(Long id);

    SecretDto updateSecret(Long id, SecretDto passwordDto);

    void deleteSecret(Long id, Long secretId);

    UserDto isRegister(String username, String token);

    List<Group> getAllGroup(String officialUserName);

    void saveImageUrl(Long id, String imgUrl);
}
