package com.udacity.jwdnd.SuperDuperDrive.service;

import com.udacity.jwdnd.SuperDuperDrive.mapper.UserMapper;
import com.udacity.jwdnd.SuperDuperDrive.model.User;
import com.udacity.jwdnd.SuperDuperDrive.model.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserMapper userMapper;
    private final HashService hashService;

    public UserService(
            UserMapper userMapper,
            HashService hashService) {

        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public boolean isUsernameAvailable(String username) {
        return this.userMapper.getUserByUsername(username) == null;
    }

    public int createUser(UserVO userVo) {

        SecureRandom random = new SecureRandom();

        byte[] salt = new byte[16];

        random.nextBytes(salt);

        String encodedSalt = Base64.getEncoder().encodeToString(salt);

        String hashedPassword = hashService.getHashedValue(userVo.getPassword(), encodedSalt);

        User newUser = new User(
                null,
                userVo.getUsername(),
                encodedSalt,
                hashedPassword,
                userVo.getFirstName(),
                userVo.getLastName());

        return userMapper.insert(newUser);
    }

    public User getUser(String username) {
        return this.userMapper.getUserByUsername(username);
    }
}