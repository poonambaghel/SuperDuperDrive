package com.udacity.jwdnd.SuperDuperDrive.service;

import com.udacity.jwdnd.SuperDuperDrive.model.User;
import com.udacity.jwdnd.SuperDuperDrive.model.UserVO;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    private UserService userService;

    public AuthorizationService(UserService userService) {
        this.userService = userService;
    }

    public boolean signupUser(UserVO userVO) {

        String username = userVO.getUsername();

        if (!this.userService.isUsernameAvailable(username)) {
            return false;
        }

        this.userService.createUser(userVO);

        return true;
    }
}
