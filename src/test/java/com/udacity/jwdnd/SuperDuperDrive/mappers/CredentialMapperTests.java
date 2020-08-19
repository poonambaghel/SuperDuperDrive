package com.udacity.jwdnd.SuperDuperDrive.mappers;

import com.udacity.jwdnd.SuperDuperDrive.model.Credentials;
import com.udacity.jwdnd.SuperDuperDrive.mapper.CredentialMapper;
import org.testng.annotations.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@MybatisTest
public class CredentialMapperTests {

    private Logger logger = LoggerFactory.getLogger(CredentialMapperTests.class);

    @Autowired
    private CredentialMapper credentialMapper;

    @Test
    public void insertCredential() {

        Credentials newCredential = new Credentials(
                null,
                "www.google.com",
                "hello",
                "sasfdasdfasdf#$#",
                "worldsdfsdf");

        this.credentialMapper.insert(newCredential);

        Integer credentialId = newCredential.getCredentialID();

        Assertions.assertNotNull(credentialId);

        Credentials credential = this.credentialMapper.getCredentialByCredentialId(credentialId);

        Assertions.assertNotNull(credential);
        Assertions.assertEquals(newCredential.getUrl(), credential.getUrl());
        Assertions.assertEquals(newCredential.getKey(), credential.getKey());
        Assertions.assertEquals(newCredential.getPassword(), credential.getPassword());
    }

    @Test
    public void deleteCredential() {

        Credentials newCredential = new Credentials(
                null,
                "www.google.com",
                "hello1",
                "sasfdasdfasdf#$#",
                "worldsdfsdf");

        this.credentialMapper.insert(newCredential);

        Integer credentialId = newCredential.getCredentialID();

        Assertions.assertNotNull(credentialId);

        this.credentialMapper.delete(credentialId);

        Credentials credential = this.credentialMapper.getCredentialByCredentialId(credentialId);

        Assertions.assertNull(credential);
    }

    @Test
    public void updateCredential() {

        Credentials newCredential = new Credentials(
                null,
                "www.google.com",
                "hello1",
                "sasfdasdfasdf#$#",
                "worldsdfsdf");

        this.credentialMapper.insert(newCredential);

        Integer credentialId = newCredential.getCredentialID();

        Assertions.assertNotNull(credentialId);

        this.credentialMapper.update("", "hello", "asdfasdfasf", "123", credentialId);

        Credentials credential =
                this.credentialMapper.getCredentialByCredentialId(credentialId);

        Assertions.assertEquals("", credential.getUrl());
        Assertions.assertEquals("hello", credential.getUsername());
        Assertions.assertEquals("123", credential.getPassword());
    }
}
