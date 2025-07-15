package com.ABG.model;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;
    private String password;

    // You already have @Data, but here are explicit getters/setters just in case Lombok isn't working
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
