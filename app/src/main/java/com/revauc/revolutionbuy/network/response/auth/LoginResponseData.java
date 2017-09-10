package com.revauc.revolutionbuy.network.response.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponseData {


    private String accessToken;
    private UserDto userDto;
    private int inviteToJoin;
    private int badgeCount;

    public String getAccessToken() {
        return accessToken;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public int getInviteToJoin() {
        return inviteToJoin;
    }

    public int getBadgeCount() {
        return badgeCount;
    }
}
