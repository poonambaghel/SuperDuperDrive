package com.udacity.jwdnd.SuperDuperDrive.mappers;

import com.udacity.jwdnd.SuperDuperDrive.model.Credentials;
import com.udacity.jwdnd.SuperDuperDrive.model.User;
import com.udacity.jwdnd.SuperDuperDrive.model.UserCredentials;
import com.udacity.jwdnd.SuperDuperDrive.mapper.UserMapper;
import com.udacity.jwdnd.SuperDuperDrive.mapper.CredentialMapper;
import com.udacity.jwdnd.SuperDuperDrive.mapper.UserCredentialMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@MybatisTest
public class UserCredentialMapperTests {

    public static final String USERNAME_U = "byu00";
    public static final String USERNAME_C = "frances90";
    public static final String PASSWORD = "hello-world";
    public static final String FIRSTNAME = "hello";
    public static final String LASTNAME = "world";
    public static final String SALT = "sdfadjfkhlahfa";
    public static final String URL = "http://www.google.com";
    public static final String URL_I = "https://github.com/";

    private Logger logger = LoggerFactory.getLogger(UserCredentialMapperTests.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CredentialMapper credentialMapper;

    @Autowired
    private UserCredentialMapper userCredentialMapper;

    @Before
    public void before() {

        this.insertUser();
    }

    private void insertUser() {

        User user = new User(USERNAME_U, SALT, PASSWORD, FIRSTNAME, LASTNAME);

        this.userMapper.insert(user);
    }

    @Test
    public void getCredentialsByUsername() {

        User user = this.userMapper.getUserByUsername(USERNAME_U);

        Assertions.assertNotNull(user);

        Integer userId = user.getUserid();

        Credentials newCredential = new Credentials(
                null, URL, USERNAME_C, SALT, PASSWORD, userId);

        this.credentialMapper.insert(newCredential);

        Integer credentialId = newCredential.getCredentialID();

        Assertions.assertNotNull(credentialId);

        List<UserCredentials> userCredentialVOList =
                this.userCredentialMapper.getCredentialsByUsername(USERNAME_U);

        Assertions.assertFalse(userCredentialVOList.isEmpty());

        UserCredentials userCredentialVO = userCredentialVOList.get(0);

        Assertions.assertEquals(USERNAME_C, userCredentialVO.getUsername());
        Assertions.assertEquals(SALT, userCredentialVO.getKey());
        Assertions.assertEquals(PASSWORD, userCredentialVO.getPassword());
    }

    @Test
    public void insertCredentialByUsername() {

        User user = this.userMapper.getUserByUsername(USERNAME_U);

        Assertions.assertNotNull(user);

        this.userCredentialMapper.insertCredentialByUsername(URL_I, USERNAME_C, SALT, PASSWORD, USERNAME_U);

        List<UserCredentials> userCredentialVOList =
                this.userCredentialMapper.getCredentialsByUsername(USERNAME_U);

        Assertions.assertFalse(userCredentialVOList.isEmpty());

        UserCredentials userCredentialVO = userCredentialVOList.get(0);

        Assertions.assertEquals(USERNAME_C, userCredentialVO.getUsername());
        Assertions.assertEquals(SALT, userCredentialVO.getKey());
        Assertions.assertEquals(PASSWORD, userCredentialVO.getPassword());
        Assertions.assertEquals(URL_I, userCredentialVO.getUrl());
    }
}
