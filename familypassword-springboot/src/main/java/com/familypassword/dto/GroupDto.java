package com.familypassword.dto;

import com.familypassword.models.Secret;
import com.familypassword.models.User;
import lombok.Data;

import java.util.List;

@Data
public class GroupDto {

    private String token;
    private String name;
    private List<User> members;
    private User admin;
    private List<Secret> secrets;
}
