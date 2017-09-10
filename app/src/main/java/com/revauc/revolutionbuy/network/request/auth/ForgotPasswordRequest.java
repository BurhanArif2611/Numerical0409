package com.revauc.revolutionbuy.network.request.auth;


public class ForgotPasswordRequest {


    public String email;

    public ForgotPasswordRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
