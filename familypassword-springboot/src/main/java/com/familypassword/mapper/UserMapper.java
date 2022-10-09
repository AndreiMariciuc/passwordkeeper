package com.familypassword.mapper;

import com.familypassword.dto.UserDto;
import com.familypassword.models.User;

public class UserMapper {

    public static UserDto defaultPopulating(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setOfficialName(user.getOfficialName());
        userDto.setName(user.getName());
        userDto.setToken(user.getToken());
        userDto.setEmail(user.getEmail());
        userDto.setImageUrl(user.getImageUrl() == null ? "img/placeholder.png" : user.getImageUrl());

        return userDto;
    }
}
