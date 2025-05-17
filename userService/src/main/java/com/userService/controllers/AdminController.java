package com.userService.controllers;

import com.userService.entity.User;
import com.userService.model.request.LoginRequest;
import com.userService.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {
  @Autowired
    private UserDetailsImpl userDetailsService;

    @GetMapping
    public List<User> getAll(){
        return userDetailsService.getAll();
    }
  @DeleteMapping("/delete")
  public void deleteUser(@RequestBody LoginRequest loginRequest){
    String email = loginRequest.getEmail();
    String password = loginRequest.getPassword();
    userDetailsService.deleteUser(email,password);
  }

}
