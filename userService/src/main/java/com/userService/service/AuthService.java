package com.userService.service;

import com.userService.entity.Otp;
import com.userService.entity.Token;
import com.userService.entity.TokenType;
import com.userService.entity.User;
import com.userService.model.request.AuthenticationResponse;
import com.userService.model.request.LoginRequest;
import com.userService.model.request.RegisterRequest;
import com.userService.repository.OtpRepo;
import com.userService.repository.TokenRepository;
import com.userService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class AuthService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private OtpRepo otpRepo;

    @Autowired
    private JavaMailSender mailSender;

    public AuthenticationResponse login(LoginRequest request)
    {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail());
        Map<String , Object> extraClaims = new HashMap<>();
        String jwtToken = jwtService.createToken(user , extraClaims);
        saveUserToken(user, jwtToken);
        return new AuthenticationResponse(jwtToken , request.getEmail());
    }

    public Otp generateOtp(String email){
        String otp = String.format("%06d",new Random().nextInt(999999));
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(20);
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new RuntimeException("the user = null");
        }
        Otp newOtp = new Otp(user, otp ,expiryDate);
        sendEmail(email, newOtp);
        return otpRepo.save(newOtp);

    }
    public String register(RegisterRequest request)
    {
        User user = new User(
                request.getFirstname(),
                request.getLastname(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getRole(),
                false
        );

        userRepository.save(user);

        Otp otp=generateOtp(request.getEmail());



        sendEmail(request.getEmail(),otp);

        return "User registered successfully. Please check your email for the OTP to activate your account.";

}

private void sendEmail(String email, Otp otp) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(email);
    message.setSubject("activation request");
    message.setText("Your OTP code is: " + otp.getOtp() + "\nThis code will expire in 20 minutes.");

    mailSender.send(message);
}

public String activateNewUser(String otp, String email) {
        User user = userRepository.findByEmail(email);
        Otp otpRecord = otpRepo.findByUser(user);
    if (user == null) {
        otpRepo.delete(otpRecord);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }

        if (otpRecord == null) {
            otpRepo.delete(otpRecord);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid OTP or email.");
        }

        if (otpRecord.getExpiryDate().isBefore(LocalDateTime.now())) {
            otpRepo.delete(otpRecord);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OTP has expired.");
        }

        if (!otp.equalsIgnoreCase(otpRecord.getOtp())) {
            otpRepo.delete(otpRecord);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect OTP.");
        }

        user.setEnabled(true);
        userRepository.save(user);
        otpRepo.delete(otpRecord);

        return "The user has been registered successfully. Login to start your journey.";
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = new Token(
                jwtToken,
                TokenType.BEARER,
                false,
                false,
                user
        );
        tokenRepository.save(token);
    }

}
