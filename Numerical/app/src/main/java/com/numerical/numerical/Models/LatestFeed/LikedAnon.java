package com.numerical.numerical.Models.LatestFeed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LikedAnon implements Serializable {
    @SerializedName("userIP")
    @Expose
    private String userIP;

    public String getUserIP() {
        return userIP;
    }

    public void setUserIP(String userIP) {
        this.userIP = userIP;
    }
}
