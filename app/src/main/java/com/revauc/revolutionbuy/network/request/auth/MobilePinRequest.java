package com.revauc.revolutionbuy.network.request.auth;



public class MobilePinRequest {

    private String mobile;

    public MobilePinRequest(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
