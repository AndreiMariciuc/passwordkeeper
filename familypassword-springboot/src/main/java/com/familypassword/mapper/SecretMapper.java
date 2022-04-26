package com.familypassword.mapper;

import com.familypassword.dto.FolderDto;
import com.familypassword.dto.PasswordDto;
import com.familypassword.dto.SecretDto;
import com.familypassword.models.Folder;
import com.familypassword.models.Password;
import com.familypassword.models.Secret;

public class SecretMapper {

    public static Secret defaultReversePopulating(SecretDto secretDto) {
        Secret secret = null;
        if (secretDto instanceof PasswordDto) {
            secret = new Password();
            secret.setId(secretDto.getId());
            secret.setName(secretDto.getName());
            ((Password) secret).setPassword(((PasswordDto) secretDto).getPassword());
            ((Password) secret).setUsername(((PasswordDto) secretDto).getUsername());
            ((Password) secret).setLink(((PasswordDto) secretDto).getLink());
        } else if (secretDto instanceof FolderDto) {
            secret = new Folder();
            secret.setId(secretDto.getId());
            secret.setName(secretDto.getName());
            ((Folder) secret).setChildren(((FolderDto) secretDto).getChildren());
        }
        return secret;
    }

    public static SecretDto defaultPopulating(Secret secret) {
        SecretDto secretDto = null;
        if (secret instanceof Password) {
            secretDto = new PasswordDto();
            secretDto.setId(secret.getId());
            secretDto.setName(secret.getName());
            ((PasswordDto) secretDto).setPassword(((Password) secret).getPassword());
            ((PasswordDto) secretDto).setUsername(((Password) secret).getUsername());
            ((PasswordDto) secretDto).setLink(((Password) secret).getLink());
        } else if (secret instanceof Folder) {
            secretDto = new FolderDto();
            secretDto.setId(secret.getId());
            secretDto.setName(secret.getName());
            ((FolderDto) secretDto).setChildren(((Folder) secret).getChildren());
        }
        return secretDto;
    }
}
