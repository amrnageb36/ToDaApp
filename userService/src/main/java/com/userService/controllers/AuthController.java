package com.userService.controllers;


import com.userService.entity.Otp;
import com.userService.entity.User;
import com.userService.model.request.AuthenticationResponse;
import com.userService.model.request.ChangePasswordRequest;
import com.userService.model.request.LoginRequest;
import com.userService.model.request.RegisterRequest;
import com.userService.repository.OtpRepo;
import com.userService.repository.UserRepository;
import com.userService.service.AuthService;
import com.userService.service.PasswordResetService;
import com.userService.service.JwtService;
import com.userService.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    UserDetailsImpl userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordResetService emailService;

    @Autowired
    private OtpRepo otpRepo;


    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping(value = "/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {

        return ResponseEntity.ok(authService.login(loginRequest));
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest)
    {

        UserDetails userCheck =userService.loadUserByUsername(registerRequest.getEmail());
        if(userCheck!=null){
            throw new RuntimeException(String.format("user with email = %s already exists", registerRequest.getEmail()));
        }


        return ResponseEntity.ok(authService.register(registerRequest));

    }


    @PostMapping("/activate-account")
    public ResponseEntity activate(@RequestHeader String otp,@RequestParam String email) {
       return ResponseEntity.ok(authService.activateNewUser(otp,email));
    }

    @GetMapping("/validate-token")
    public ResponseEntity<Boolean>  checkToken(@RequestHeader("Authorization") String authHeader,@RequestParam("email") String username){
        String pureToken=authHeader.replace("Bearer ", "");
        UserDetails user = userService.loadUserByUsername(username);
        if (user == null) {
            return ResponseEntity.ok(false);
        }
        Boolean isValid=jwtService.isTokenValid(pureToken,user);
        return ResponseEntity.ok(isValid);
    }


    @PostMapping("/password/forget")
    public ResponseEntity<String> forgetPassword(@RequestParam String email) {
        passwordResetService.sendOtp(email);
        return ResponseEntity.ok("OTP has been sent to your email.");
    }

    @PostMapping("/password/change")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request) {
        return ResponseEntity.ok(passwordResetService.changePassword(request.getEmail(), request.getOtp(), request.getNewPassword()));
    }

    @PostMapping("/otp/resend")
    public String regenrateOtp(@RequestParam String email){
        ResponseEntity.ok(authService.generateOtp(email));
        return "the new Otp sent to your email.";
    }

}


