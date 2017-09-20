package com.revauc.revolutionbuy.network.request.auth;

/*
Copyright © 2017 Block Partee. All rights reserved.
Developed by Appster.
*/

public class ForgotPasswordRequest {

    private String email;

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
