package com.placideh.usermgtapi.service;

import com.placideh.usermgtapi.entity.OneTimePassword;
import com.placideh.usermgtapi.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface OneTimePasswordService {
    OneTimePassword getOtp(String otp);
    void deleteOtp(String otp);
    void createOtp(OneTimePassword otp);
    OneTimePassword getOtpByUser(User user);
}
