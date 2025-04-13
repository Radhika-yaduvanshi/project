package com.blogwebsite.user.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class ForgotController {

    @RequestMapping("/forgot")
    public String OpenEmailForm(){
        return "forgot_email_form";
    }

    @PostMapping("/send-otp")
    public String sendOtp(@RequestParam("email") String email){
        System.out.println("Email id is : "+email);
//        generate otp of rendom number
        Random random =new Random(1000);
       int otp= random.nextInt(9999999);
        System.out.println("OTP is : "+otp);
        return "varify_otp";
    }
}
