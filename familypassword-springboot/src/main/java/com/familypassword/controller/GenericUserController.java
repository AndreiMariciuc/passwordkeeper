package com.familypassword.controller;

import com.familypassword.dto.FolderDto;
import com.familypassword.dto.PasswordDto;
import com.familypassword.dto.SecretDto;
import com.familypassword.dto.UserDto;
import com.familypassword.models.Group;
import com.familypassword.service.UserService;
import com.familypassword.utils.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
    public Group createGroup(@PathVariable Long userId, @RequestParam String groupName) {
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

    @GetMapping("/{id}/groups")
    public List<Group> getGroups(@PathVariable Long id, @RequestParam String name) {
        return userService.getAllGroup(name + "[" + id + "]");
    }

    @Value("${local.system.imgs.dir}")
    private String localFileSystemImagesDir;

    @PostMapping(value = "/{id}/upload-image", consumes = "multipart/form-data")
    public String saveUploadedImage(@PathVariable Long id, @RequestParam("image") MultipartFile image) throws IOException {
        Optional<String> upload = ImageUtil.upload(image, localFileSystemImagesDir);
        upload.ifPresent(v -> userService.saveImageUrl(id, v));
        return upload.orElse(null);
    }
}
