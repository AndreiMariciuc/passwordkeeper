package com.familypassword.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PasswordDto extends SecretDto {

    private String link;
    private String username;
    private String password;
}
