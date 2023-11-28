package com.placideh.usermgtapi.service.impl;

import com.placideh.usermgtapi.Dto.UserDto;
import com.placideh.usermgtapi.entity.*;
import com.placideh.usermgtapi.exception.AlreadyExistException;
import com.placideh.usermgtapi.exception.CustomInputException;
import com.placideh.usermgtapi.exception.NotFoundException;
import com.placideh.usermgtapi.mails.EmailSenderService;
import com.placideh.usermgtapi.repository.UserRepo;
import com.placideh.usermgtapi.service.AuthService;
import com.placideh.usermgtapi.service.OneTimePasswordService;
import com.placideh.usermgtapi.service.RoleService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Component
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepo userRepo;

    private final RoleService roleService;

    private final EmailSenderService emailSenderService;


    private final OneTimePasswordService oneTimePasswordService;

    private static final String USER_ROLE="user";

    public AuthServiceImpl(UserRepo userRepo, RoleService roleService, EmailSenderService emailSenderService, OneTimePasswordService oneTimePasswordService) {
        this.userRepo = userRepo;
        this.roleService = roleService;
        this.emailSenderService = emailSenderService;
        this.oneTimePasswordService = oneTimePasswordService;
    }

    @Override
    public User createUserAccount(UserDto user) throws Exception {
        log.info("User Account Creation");
        String message="your Verification OTP number";
        log.info(user.getPassword());
        boolean isPasswordValid= isPasswordValid(user.getPassword()).trim().isEmpty();
        if (!isPasswordValid) {
            throw new CustomInputException(isPasswordValid(user.getPassword()));
        }
        boolean isUserExist=userRepo.findByEmail(user.getEmail()).isPresent();

        if (isUserExist) throw new AlreadyExistException("email: "+user.getEmail()+" is already taken");

        Role existingRole=roleService.getRoleByName(USER_ROLE);
        String hashedPassword= BCrypt.hashpw(user.getPassword(),BCrypt.gensalt(10));
        String otp=generateOTPCode();

        User newUser= User.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(hashedPassword)
                .role(existingRole)
                .otp(otp)
                .status(Status.UNVERIFIED)
                .build();
        User createdUser=userRepo.save(newUser);

        try {
            triggerTheEmailMsg(user.getEmail(),user.getFirstName(),otp,message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        OneTimePassword newOtp= OneTimePassword.builder()
                .otp(otp)
                .status(Status.UNVERIFIED)
                .user(createdUser)
                .issuedDateAndTime(LocalDateTime.now())
                .build();
        oneTimePasswordService.createOtp(newOtp);



        return createdUser;
    }

    @Override
    public boolean verifyEmail(String otp) {

        log.info("Account Verification");
        OneTimePassword existingOtp=oneTimePasswordService.getOtp(otp);
        User existingUser=userRepo.findByEmail(existingOtp.getUser().getEmail())
                .orElseThrow(()-> new NotFoundException("user:  not found"));

        if (existingUser.getStatus().equals(Status.VERIFIED) ) throw new AlreadyExistException("User already Verified");

        if (!existingUser.getOtp().equals(otp.trim()) && existingUser.getStatus() != Status.UNVERIFIED) throw new NotFoundException("OTP: "+otp+" not exist");

        existingUser.setStatus(Status.VERIFIED);
        userRepo.save(existingUser);
        oneTimePasswordService.deleteOtp(otp);

        return true;
    }



    @Override
    public Map<String,String> login(String email, String password) {
        log.info("user login");

        User existingUser=userRepo.findByEmail(email.trim())
                .orElseThrow(()-> new CustomInputException("Invalid email/password"));

        if (!existingUser.getStatus().equals(Status.VERIFIED)) throw new CustomInputException("User Status  not verified");

        if(!BCrypt.checkpw(password,existingUser.getPassword())) throw new CustomInputException("Invalid email/password");
        return generateJWTToken(existingUser);
    }
    @Override
    public boolean resetPassword(PasswordReset reset) {
        log.info("Password Reset");

        if ( !reset.getPassword().equals(reset.getConfirmPassword())) throw new CustomInputException("password must match");

        OneTimePassword otp=oneTimePasswordService.getOtp(reset.getOtp());

        User existingUser=userRepo.findByEmail(otp.getUser().getEmail())
                .orElseThrow(()-> new NotFoundException("user  not found "));

//        if (isOtpExpired(otp.getIssuedDateAndTime())) throw new CustomInputException("OTP expired");
        if (!otp.getStatus().equals(Status.RESET)|| !otp.getUser().getStatus().equals(Status.VERIFIED)) throw new CustomInputException("User Status  not correct");
        String hashedPassword = BCrypt.hashpw(reset.getPassword(), BCrypt.gensalt(10));
        existingUser.setPassword(hashedPassword);
        userRepo.save(existingUser);
        oneTimePasswordService.deleteOtp(otp.getOtp());
        return true;
    }

    @Override
    public boolean resetPasswordLink(String email) {
        log.info("Password  Reset Link");
        String message="your Reset Password OTP number";
        User existingUser=userRepo.findByEmail(email)
                .orElseThrow(()-> new NotFoundException("email :"+email+" not found "));

        if (existingUser.getStatus().equals(Status.UNVERIFIED)) throw new CustomInputException("User is not verified");
        String otp=generateOTPCode();

        try {
            OneTimePassword newOtp=OneTimePassword.builder()
                    .otp(otp)
                    .user(existingUser)
                    .status(Status.RESET)
                    .issuedDateAndTime(LocalDateTime.now())
                    .build();
            triggerTheEmailMsg(existingUser.getEmail(),existingUser.getFirstName(),otp,message);
            oneTimePasswordService.createOtp(newOtp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    private Map<String ,String> generateJWTToken(User user){
        long timestamp=System.currentTimeMillis();
        String token= Jwts.builder().signWith(SignatureAlgorithm.HS256,Constants.API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp+ Constants.TOKEN_VALIDITY))
                .claim("id",user.getUserId())
                .claim("email",user.getEmail())
                .claim("firstName",user.getFirstName())
                .claim("email",user.getEmail())
                .compact();
        Map<String ,String> map=new HashMap<>();
        map.put("token", token);
        return map;

    }
    private void triggerTheEmailMsg(String email,String user,String otp,String message) throws Exception {
        emailSenderService.sendCommunicationEmail(
                email,
                "Hello "+user+" Welcome to user Management  \n \n "+message+":"+otp+"\n \n \n QT-challenge ",
                "User management"
        );
    }
    private  String isPasswordValid(String password) {

        Pattern specialCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
        Pattern lowerCasePatten = Pattern.compile("[a-z ]");
        Pattern digitCasePatten = Pattern.compile("[0-9 ]");


        if (password.length() < 8) {
            return "Password length must have atleast 8 character ";
        }
        if (!specialCharPatten.matcher(password).find()) {
            return "Password must have atleast one special character ";
        }
        if (!UpperCasePatten.matcher(password).find()) {
            return "Password must have atleast one uppercase character ";
        }
        if (!lowerCasePatten.matcher(password).find()) {
            return "Password must have atleast one lowercase character ";
        }
        if (!digitCasePatten.matcher(password).find()) {
            return "Password must have atleast one digit character ";
        }

        return "";

    }
    private  String generateOTPCode() {

        String characters = "0123456789";
        String str = "";
        char[] mynewCharacters = characters.toCharArray();
        Integer generatedCodeLength =6;
        for (int i = 0; i < generatedCodeLength; i++) {
            int index = (int) (Math.random() *10);
            String newString = characters.substring(index, characters.length() - 1);
            str += mynewCharacters[newString.length()];
        }
        return str;
    }
}

