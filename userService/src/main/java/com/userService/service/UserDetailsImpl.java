package com.userService.service;

import com.userService.entity.User;
import com.userService.repository.TokenRepository;
import com.userService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserDetailsImpl implements UserDetailsService {
   @Autowired
   private UserRepository userRepository;

   @Autowired
   private TokenRepository tokenRepository;
   @Autowired
    private PasswordEncoder passwordEncoder;

   @Autowired
   private JwtService jwtService;


    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }

    @Transactional
    public void deleteUser(String email,String password) {

        User user = userRepository.findByEmail(email);

        if (user!= null && passwordEncoder.matches(password, user.getPassword())) {
            tokenRepository.deleteTokenByUserId(user.getId());
            userRepository.deleteByEmail(email); // Delete based on email
        } else {
            throw new IllegalArgumentException("Invalid email or password");
        }
    }

    @Transactional
    public User updateUser(String newFirstName,String newLastName, String email) {
        User existingUser = userRepository.findByEmail(email);

        if (existingUser == null) {
            throw new IllegalArgumentException("User not found with email: " + email);
        }

        existingUser.setFirstName(newFirstName);
        existingUser.setLastName(newLastName);


        return userRepository.save(existingUser);
    }

    @Transactional
    public List<User> getAll(){
        List<User> users = userRepository.findAll();
        return users;
    }



}
