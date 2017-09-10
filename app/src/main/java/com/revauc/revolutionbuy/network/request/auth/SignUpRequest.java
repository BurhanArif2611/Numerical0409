package com.revauc.revolutionbuy.network.request.auth;

/**
 * Created by nishant on 12/05/17.
 */

public class SignUpRequest extends FBLoginRequest {

    public String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
