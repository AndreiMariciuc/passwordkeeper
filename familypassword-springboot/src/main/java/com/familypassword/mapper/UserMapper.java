package com.familypassword.mapper;

import com.familypassword.models.User;
import com.familypassword.dto.UserDto;

public class UserMapper {

    public static UserDto defaultPopulating(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setOfficialName(user.getOfficialName());
        userDto.setName(user.getName());
        userDto.setToken(user.getToken());
        userDto.setEmail(user.getEmail());

        return userDto;
    }
}
