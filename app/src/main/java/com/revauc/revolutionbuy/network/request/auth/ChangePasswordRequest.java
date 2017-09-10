package com.revauc.revolutionbuy.network.request.auth;


public class ChangePasswordRequest {


    public String newPassword;
    public String oldPassword;
    public int isAdmin;

    public ChangePasswordRequest(String newPassword, String oldPassword, int isAdmin) {
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
        this.isAdmin = isAdmin;
    }
}
