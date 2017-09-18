package com.revauc.revolutionbuy.network.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponseData {


    private String token;
    private UserDto user;


    public String getToken() {
        return token;
    }

    public UserDto getUser() {
        return user;
    }
}
