package com.revauc.revolutionbuy.network.request.auth;


public class MobileVerifyRequest {

    private String mobile;
    private String pin;

    public MobileVerifyRequest(String mobile, String pin) {
        this.mobile = mobile;
        this.pin = pin;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
