package com.udacity.jwdnd.SuperDuperDrive.service;

import com.udacity.jwdnd.SuperDuperDrive.mapper.CredentialMapper;
import com.udacity.jwdnd.SuperDuperDrive.mapper.UserCredentialMapper;
import com.udacity.jwdnd.SuperDuperDrive.model.UserCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CredentialService {

    private Logger logger = LoggerFactory.getLogger(CredentialService.class);

    private EncryptionService encryptionService;
    private CredentialMapper credentialMapper;
    private UserCredentialMapper userCredentialMapper;

    public CredentialService(
            EncryptionService encryptionService,
            CredentialMapper credentialMapper,
            UserCredentialMapper userCredentialMapper
    ) {

        this.encryptionService = encryptionService;
        this.credentialMapper = credentialMapper;
        this.userCredentialMapper = userCredentialMapper;
    }

    public List<UserCredentials> getCredentialsByUsername(String username) {

        List<UserCredentials> userCredentialVOList =
                this.userCredentialMapper.getCredentialsByUsername(username);

        return userCredentialVOList.stream().map(userCredentialVO -> {

            String encodedKey = userCredentialVO.getKey();
            String encryptedPassword = userCredentialVO.getPassword();

            String decryptedPassword = encryptionService.decryptValue(encryptedPassword, encodedKey);

            userCredentialVO.setDecryptedPassword(decryptedPassword);

            return userCredentialVO;
        }).collect(Collectors.toList());
    }

    public Boolean insertOrUpdateCredential(
            UserCredentials userCredentialVO, String username) {

        String password = userCredentialVO.getPassword();

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);

        Integer credentialId = userCredentialVO.getCredentialId();

        if (credentialId == null) {

            this.userCredentialMapper.insertCredentialByUsername(
                    userCredentialVO.getUrl(),
                    userCredentialVO.getUsername(),
                    encodedKey,
                    encryptedPassword,
                    username);
        } else {

            this.credentialMapper.update(
                    userCredentialVO.getUrl(),
                    userCredentialVO.getUsername(),
                    encodedKey,
                    encryptedPassword,
                    credentialId);
        }

        return true;
    }

    public Boolean deleteCredential(Integer credentialId) {

        this.credentialMapper.delete(credentialId);

        return true;
    }
}
