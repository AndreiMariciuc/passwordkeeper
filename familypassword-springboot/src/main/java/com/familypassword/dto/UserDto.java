package com.familypassword.dto;

import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String officialName;
    private String name;
    private String token;
    private String email;
}
