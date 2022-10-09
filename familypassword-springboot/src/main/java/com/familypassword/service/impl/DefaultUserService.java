package com.familypassword.service.impl;

import com.familypassword.dto.PasswordDto;
import com.familypassword.dto.SecretDto;
import com.familypassword.dto.UserDto;
import com.familypassword.exception.ModelProblemException;
import com.familypassword.mapper.SecretMapper;
import com.familypassword.mapper.UserMapper;
import com.familypassword.models.*;
import com.familypassword.repository.GroupRepository;
import com.familypassword.repository.SecretRepository;
import com.familypassword.repository.UserRepository;
import com.familypassword.service.UserService;
import com.familypassword.utils.TokenGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultUserService implements UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private GroupRepository groupRepository;

    @Resource
    private SecretRepository secretRepository;

    @Override
    public UserDto register(String name, String email) {
        User newUser = new User();
        newUser.setName(name);
        newUser.setToken(TokenGenerator.generateToken());
        newUser.setEmail(email);

        User savedUser = userRepository.save(newUser);
        savedUser.setOfficialName(name + "[" + savedUser.getId() + "]");
        savedUser = userRepository.save(newUser);

        return UserMapper.defaultPopulating(savedUser);
    }

    @Override
    public Group createGroup(Long id, String groupName) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            throw new ModelProblemException("Nu am gasit user-ul!");
        }

        User admin = optionalUser.get();
        Group group = new Group();
        group.setName(groupName);
        group.setAdmin(admin);
        group.setMembers(List.of(admin));
        group.setSecrets(Collections.emptyList());

        return groupRepository.save(group);
    }

    public List<Group> getAllGroup(String officialUserName) {
        List<String> groups = groupRepository.findAllGroups(officialUserName);

        return groups.stream().map(groupRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Override
    public SecretDto addSecret(Long id, Long parentId, SecretDto secretDto) {
        Secret secret = SecretMapper.defaultReversePopulating(secretDto);
        if (parentId == null) {
            Optional<User> optionalUser = userRepository.findById(id);
            if (optionalUser.isEmpty()) {
                throw new ModelProblemException("Nu am gasit user-ul");
            }
            User user = optionalUser.get();

            Secret newSecret = secretRepository.save(secret);
            user.getSecrets().add(newSecret);
            userRepository.save(user);
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

    @Override
    public List<SecretDto> getSecrets(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new ModelProblemException("Nu am gasit user-ul!");
        }

        User user = optionalUser.get();
        return user.getSecrets().stream()
                .map(SecretMapper::defaultPopulating)
                .collect(Collectors.toList());
    }

    @Override
    public SecretDto updateSecret(Long id, SecretDto secretDto) {
        Optional<Secret> opSecret = secretRepository.findById(secretDto.getId());

        if (opSecret.isEmpty()) {
            return null;
        }

        Secret secret1 = opSecret.get();

        secret1.setName(secretDto.getName());
        if (secret1 instanceof Password) {
            ((Password) secret1).setPassword(((PasswordDto) secretDto).getPassword());

            ((Password) secret1).setUsername(((PasswordDto) secretDto).getUsername());
            ((Password) secret1).setLink(((PasswordDto) secretDto).getLink());
        }

        secretRepository.save(secret1);

        return SecretMapper.defaultPopulating(secret1);
    }

    @Override
    public void deleteSecret(Long id, Long secretId) {
        secretRepository.deleteById(secretId);
    }

    @Override
    public UserDto isRegister(String username, String token) {
        Optional<User> byNameAndToken = userRepository.findByNameAndToken(username, token);
        if (byNameAndToken.isEmpty()) {
            return null;
        }

        return UserMapper.defaultPopulating(byNameAndToken.get());
    }

    @Override
    public void saveImageUrl(Long id, String imgUrl) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(value -> {
            value.setImageUrl(imgUrl);
            userRepository.save(value);
        });
    }
}
