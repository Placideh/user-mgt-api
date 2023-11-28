package com.placideh.usermgtapi.service.impl;


import com.placideh.usermgtapi.entity.OneTimePassword;
import com.placideh.usermgtapi.entity.User;
import com.placideh.usermgtapi.exception.NotFoundException;
import com.placideh.usermgtapi.repository.OneTimePasswordRepo;
import com.placideh.usermgtapi.service.OneTimePasswordService;
import org.springframework.stereotype.Component;

@Component
public class OneTimePasswordServiceImpl implements OneTimePasswordService {


    private final OneTimePasswordRepo otpRepo;

    public OneTimePasswordServiceImpl(OneTimePasswordRepo otpRepo) {
        this.otpRepo = otpRepo;
    }

    @Override
    public OneTimePassword getOtp(String otp) {
        return otpRepo.findByOtp(otp)
                .orElseThrow(()-> new NotFoundException("OTP :"+otp+" not found"));
    }

    @Override
    public void deleteOtp(String otp) {
        otpRepo.findByOtp(otp)
                .orElseThrow(()-> new NotFoundException("OTP:"+otp+" not found"));

        otpRepo.deleteByOtp(otp);

    }


    @Override
    public void createOtp(OneTimePassword otp) {
        otpRepo.save(otp);
    }

    @Override
    public OneTimePassword getOtpByUser(User user) {
        return otpRepo.findByUser(user)
                .orElseThrow(()-> new NotFoundException("User with Email:"+user.getEmail()+" not found"));
    }
}
