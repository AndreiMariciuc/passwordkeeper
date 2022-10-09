package com.familypassword.service;

import com.familypassword.dto.SecretDto;

public interface GroupService {
    void requestJoinToAGroup(String token, Long id);

    void requestConfirmation(String token, Long id, Boolean flag);

    void deleteMember(String token, Long id);

    SecretDto addSecretToGroup(String token, Long parentId, SecretDto secretDto);
}
