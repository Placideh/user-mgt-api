package com.placideh.usermgtapi.controller;


import com.placideh.usermgtapi.Dto.LoginDto;
import com.placideh.usermgtapi.Dto.UserDto;
import com.placideh.usermgtapi.entity.PasswordReset;
import com.placideh.usermgtapi.entity.User;
import com.placideh.usermgtapi.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/auth/user")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping( value = "/signup")
    public ResponseEntity<Map<String,User>> registerUser(@Valid @RequestBody UserDto user ) {
        Map<String ,User> message=new HashMap<>();
        User newUser;
        try {
            newUser = authService.createUserAccount(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        message.put("success",newUser);

        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PostMapping("/verify/{otp}")
    public ResponseEntity<Map<String,Boolean>> verifyUserEmail(@PathVariable String otp){
        Map<String ,Boolean> message=new HashMap<>();
        Boolean result=authService.verifyEmail(otp);
        message.put("success",result);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping(value="/login")
    public ResponseEntity<Map<String,String>> login(@Valid @RequestBody LoginDto loginCredentials){

        Map<String,String>message=authService.login(loginCredentials.getEmail(),loginCredentials.getPassword());

        return new ResponseEntity<>(message, HttpStatus.OK);

    }



    @PostMapping("/user/reset/{email}")
    public ResponseEntity<Map<String,String>> resetPasswordLink(@PathVariable String email){
        Map<String ,String> message=new HashMap<>();
        authService.resetPasswordLink(email);
        message.put("message","success");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @PostMapping(value = "/user/reset/password")
    public ResponseEntity<Map<String,String>> resetUserPassword(@Valid  @RequestBody PasswordReset reset){
        Map<String ,String> message=new HashMap<>();
        authService.resetPassword(reset);
        message.put("message","success");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
