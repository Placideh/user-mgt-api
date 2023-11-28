package com.placideh.usermgtapi.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Constants {
    @Value("${jwt-secret}")
    public  static  String API_SECRET_KEY="secret";

    @Value("${token-validity}")
    public  static long TOKEN_VALIDITY=20*60*60*100;


}
