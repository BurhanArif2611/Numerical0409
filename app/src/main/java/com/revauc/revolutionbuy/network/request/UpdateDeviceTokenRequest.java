package com.revauc.revolutionbuy.network.request;

import com.revauc.revolutionbuy.network.request.contest.SimilarContest;

import java.util.ArrayList;

/**
 * Created by nishant on 28/04/17.
 */

public class UpdateDeviceTokenRequest {

    public String deviceToken;

    public UpdateDeviceTokenRequest(String deviceToken) {
        this.deviceToken = deviceToken;
    }


}
