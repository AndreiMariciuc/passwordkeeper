package com.familypassword.mapper;

import com.familypassword.dto.GroupDto;
import com.familypassword.models.Group;

public class GroupMapper {

    public static GroupDto defaultPopulating(Group group) {
        GroupDto groupDto = new GroupDto();

        groupDto.setName(group.getName());
        groupDto.setToken(group.getToken());
        groupDto.setAdmin(group.getAdmin());
        groupDto.setMembers(group.getMembers());
        groupDto.setSecrets(group.getSecrets());

        return groupDto;
    }
}
