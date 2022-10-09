package com.familypassword.controller;

import com.familypassword.dto.FolderDto;
import com.familypassword.dto.PasswordDto;
import com.familypassword.models.User;
import com.familypassword.repository.UserRepository;
import com.familypassword.service.GroupService;
import com.familypassword.service.UserService;

import java.util.Optional;

import org.springframework.data.util.Pair;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/group")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GroupController {
    @Resource
    private GroupService groupService;

    @Resource
    private UserRepository userRepository;

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/{token}/{id}")
    void requestJoinToAGroup(@PathVariable String token, @PathVariable Long id) {
        Optional<User> id1 = userRepository.findById(id);
        simpMessagingTemplate.convertAndSend("/websockets", Pair.of(id1.get(), token));
        groupService.requestJoinToAGroup(token, id);
    }

    @PutMapping("/{token}/{id}/{flag}")
    void requestConfirmation(@PathVariable String token, @PathVariable Long id, @PathVariable Boolean flag) {
        groupService.requestConfirmation(token, id, flag);
    }

    @DeleteMapping("/{token}/{id}")
    void deleteMember(@PathVariable String token, @PathVariable Long id) {
        groupService.deleteMember(token, id);
    }

    @PostMapping(value = "/{token}/add-secret-password/{parentId}")
    public PasswordDto addSecret(@PathVariable String token,
                                 @PathVariable Long parentId,
                                 @RequestBody PasswordDto passwordDto) {
        return (PasswordDto) groupService.addSecretToGroup(token, parentId, passwordDto);
    }

    @PostMapping(value = {"/{token}/add-secret-folder/{parentId}", "/{token}/add-secret-folder"})
    public FolderDto addSecret(@PathVariable String token,
                               @PathVariable(required = false) Long parentId,
                               @RequestBody FolderDto folderDto) {
        return (FolderDto) groupService.addSecretToGroup(token, parentId, folderDto);
    }

    @MessageMapping("/notification")
    @SendTo("/websockets")
    public Pair<User, String> sendNotification(Pair<User, String> message) throws Exception {
        return message;
    }
}
