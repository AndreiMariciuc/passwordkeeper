package com.familypassword.service.impl;

import com.familypassword.dto.SecretDto;
import com.familypassword.exception.ModelProblemException;
import com.familypassword.mapper.SecretMapper;
import com.familypassword.models.Folder;
import com.familypassword.models.Group;
import com.familypassword.models.Secret;
import com.familypassword.models.User;
import com.familypassword.repository.GroupRepository;
import com.familypassword.repository.SecretRepository;
import com.familypassword.repository.UserRepository;
import com.familypassword.service.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
@Slf4j
public class DefaultGroupService implements GroupService {
    @Resource
    private GroupRepository groupRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private SecretRepository secretRepository;

    @Override
    public void requestJoinToAGroup(String token, Long id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isEmpty()) {
            log.error("Nu am gasit userul in requrst join to a group");
            return;
        }

        Optional<Group> optionalGroup = groupRepository.findById(token);

        if (optionalGroup.isEmpty()) {
            log.error("Nu am gasit group in request join to a group");
            return;
        }

        Group group = optionalGroup.get();
        group.getRequests().add(byId.get());

        groupRepository.save(group);
    }

    @Override
    public void requestConfirmation(String token, Long id, Boolean flag) {
        if (flag == null) {
            log.error("Flag is null");
            return;
        }

        Optional<User> byId = userRepository.findById(id);
        if (byId.isEmpty()) {
            log.error("Nu am gasit userul in requrst join to a group");
            return;
        }

        Optional<Group> optionalGroup = groupRepository.findById(token);

        if (optionalGroup.isEmpty()) {
            log.error("Nu am gasit group in request join to a group");
            return;
        }

        Group group = optionalGroup.get();
        User user = byId.get();
        group.getRequests().remove(user);

        if (flag) {
            group.getMembers().add(user);
        }

        groupRepository.save(group);
    }

    @Override
    public void deleteMember(String token, Long id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isEmpty()) {
            log.error("Nu am gasit userul in requrst join to a group");
            return;
        }
        Optional<Group> optionalGroup = groupRepository.findById(token);

        if (optionalGroup.isEmpty()) {
            log.error("Nu am gasit group in request join to a group");
            return;
        }

        Group group = optionalGroup.get();
        User user = byId.get();

        group.getMembers().remove(user);

        groupRepository.save(group);
    }

    @Override
    public SecretDto addSecretToGroup(String token, Long parentId, SecretDto secretDto) {
        Secret secret = SecretMapper.defaultReversePopulating(secretDto);
        if (parentId == null) {
            Optional<Group> groupOptional = groupRepository.findById(token);
            if (groupOptional.isEmpty()) {
                throw new ModelProblemException("Nu am gasit user-ul");
            }
            Group group = groupOptional.get();

            Secret newSecret = secretRepository.save(secret);
            group.getSecrets().add(newSecret);
            groupRepository.save(group);
        } else {

            Optional<Secret> optionalFolder = secretRepository.findById(parentId);
            if (optionalFolder.isEmpty() || !(optionalFolder.get() instanceof Folder)) {
                throw new ModelProblemException("Nu am gasit folderul");
            }

            Folder folder = (Folder) optionalFolder.get();
            folder.getChildren().add(secret);
            secretRepository.save(folder);
        }

        return SecretMapper.defaultPopulating(secret);
    }
}
