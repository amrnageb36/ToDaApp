package com.userService.service;

import com.userService.entity.Otp;
import com.userService.entity.User;
import com.userService.repository.OtpRepo;
import com.userService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Random;


@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpRepo otpRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;



    public void sendOtp(String email){
        User user = userRepository.findByEmail(email);

        if(user!=null){
            String otp = String.format("%06d",new Random().nextInt(999999));
            LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(5);

            Otp resetOtp = new Otp(user,otp,expiryDate);
            otpRepository.save(resetOtp);

            sendEmail(email,otp);
        }
    }

    private void sendEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("password reset request");
        message.setText("Your OTP code is: " + otp + "\nThis code will expire in 5 minutes.");

        mailSender.send(message);

    }

    public String changePassword(String email, String otp, String newPassword) {
        User user = userRepository.findByEmail(email);
        Otp otpRecord = otpRepository.findByUser(user);
        if (user == null) {
            otpRepository.delete(otpRecord);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with provided email.");
        }

        if (otpRecord == null) {
            otpRepository.delete(otpRecord);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No OTP record found for this user.");
        }

        if (otpRecord.getExpiryDate().isBefore(LocalDateTime.now())) {
            otpRepository.delete(otpRecord);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OTP has expired.");
        }

        if (!otp.equalsIgnoreCase(otpRecord.getOtp())) {
            otpRepository.delete(otpRecord);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect OTP.");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        otpRepository.delete(otpRecord);

        return "Password changed successfully.";
    }



}
