package com.udacity.jwdnd.SuperDuperDrive.mapper;

import com.udacity.jwdnd.SuperDuperDrive.model.UserCredentials;
import com.udacity.jwdnd.SuperDuperDrive.model.UserCredentials;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface UserCredentialMapper {

    List<UserCredentials> getCredentialsByUsername(String username);

    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insertCredentialByUsername(
            String url,
            String usernameC,
            String key,
            String password,
            String username);
}
