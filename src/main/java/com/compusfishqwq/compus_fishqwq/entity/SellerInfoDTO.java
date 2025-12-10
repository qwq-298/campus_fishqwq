package com.compusfishqwq.compus_fishqwq.entity;

public class SellerInfoDTO {
    private String username;
    private String email;

    public SellerInfoDTO(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
}