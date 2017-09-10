package com.revauc.revolutionbuy.network.request.auth;

/**
 *
 */
public class FBLoginRequest {

    public String email;
    public String username;
    public String deviceID;
    public String deviceToken;
    public String latLongStr;
    public int devicetype;
    public String accessToken;

    public String getLatLongStr() {
        return latLongStr;
    }

    public void setLatLongStr(String latLongStr) {
        this.latLongStr = latLongStr;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public int getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(int devicetype) {
        this.devicetype = devicetype;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
