package com.userService.controllers;

import com.userService.entity.User;
import com.userService.model.request.LoginRequest;
import com.userService.service.JwtService;
import com.userService.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/users")
public class UserController
{
    @Autowired
    UserDetailsImpl userDetailsService;

    @Autowired
    private JwtService jwtService;

    @DeleteMapping("/delete")
    public void deleteUser( @RequestHeader("Authorization") String authHeader,@RequestBody LoginRequest loginRequest){
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        String pureToken=authHeader.replace("Bearer ", "");
        String checkEmail= jwtService.extractSubject(pureToken);
        if(!checkEmail.equalsIgnoreCase(email)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied: email mismatch");
        }
        userDetailsService.deleteUser(email,password);
    }
    @PutMapping
    public User update(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam String newFirstname,
            @RequestParam String newLastname,
            @RequestParam String email) {
        String pureToken=authHeader.replace("Bearer ", "");
        String checkEmail= jwtService.extractSubject(pureToken);
        if(!checkEmail.equalsIgnoreCase(email)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied: email mismatch");
        }
        return userDetailsService.updateUser(newFirstname,newLastname,email);
    }


    @GetMapping("/id")
    public int getUserId(@RequestParam String email){
        User user = userDetailsService.loadUserByUsername(email);
        return user.getId();
    }

}
