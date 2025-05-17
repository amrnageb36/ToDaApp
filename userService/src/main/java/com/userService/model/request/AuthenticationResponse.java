package com.userService.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class AuthenticationResponse {
    private String accessToken;
    private String email;


    public AuthenticationResponse() {
    }

    public AuthenticationResponse(String accessToken, String email) {
        this.accessToken = accessToken;
        this.email = email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

