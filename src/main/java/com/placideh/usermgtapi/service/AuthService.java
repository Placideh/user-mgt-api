package com.placideh.usermgtapi.service;

import com.placideh.usermgtapi.Dto.UserDto;
import com.placideh.usermgtapi.entity.PasswordReset;
import com.placideh.usermgtapi.entity.User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface AuthService {
    User createUserAccount(UserDto user) throws Exception;
    boolean verifyEmail(String otp);
    Map<String,String> login(String email, String password);

    boolean resetPassword(PasswordReset passwordReset);

    boolean resetPasswordLink(String email);

}
