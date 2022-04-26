package com.familypassword.controller;

import com.familypassword.dto.FolderDto;
import com.familypassword.dto.GroupDto;
import com.familypassword.dto.PasswordDto;
import com.familypassword.dto.SecretDto;
import com.familypassword.dto.UserDto;
import com.familypassword.service.UserService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/user")
@CrossOrigin
public class GenericUserController {

    private final UserService userService;

    @Autowired
    public GenericUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDto login(@RequestBody UserDto userDto) {
        String name = userDto.getOfficialName();
        String token = userDto.getToken();
        return userService.isRegister(name, token);
    }

    @PostMapping("/{userId}/create-group")
    public GroupDto createGroup(@PathVariable Long userId, @RequestParam String groupName) {
        return userService.createGroup(userId, groupName);
    }

    @PostMapping(value = "/{userId}/add-secret-password/{parentId}")
    public PasswordDto addSecret(@PathVariable Long userId,
                                 @PathVariable Long parentId,
                                 @RequestBody PasswordDto passwordDto) {
        return (PasswordDto) userService.addSecret(userId, parentId, passwordDto);
    }

    @PostMapping(value = {"/{userId}/add-secret-folder/{parentId}", "/{userId}/add-secret-folder"})
    public FolderDto addSecret(@PathVariable Long userId,
                               @PathVariable(required = false) Long parentId,
                               @RequestBody FolderDto folderDto) {
        return (FolderDto) userService.addSecret(userId, parentId, folderDto);
    }

    @GetMapping("/{id}/get-secrets")
    public List<SecretDto> getSecrets(@PathVariable Long id) {
        return userService.getSecrets(id);
    }

    @PutMapping("/{id}/update-password")
    public PasswordDto updatePassword(@PathVariable Long id, @RequestBody PasswordDto passwordDto) {
        return (PasswordDto) userService.updateSecret(id, passwordDto);

    }

    @PutMapping("/{id}/update-folder")
    public FolderDto updateFolder(@PathVariable Long id, @RequestBody FolderDto folderDto) {
        return (FolderDto) userService.updateSecret(id, folderDto);

    }

    @DeleteMapping("{id}/delete-secret/{secretId}")
    public ResponseEntity<?> deleteSecret(@PathVariable Long id, @PathVariable Long secretId) {
        userService.deleteSecret(id, secretId);
        return ResponseEntity.ok().build();
    }
}
