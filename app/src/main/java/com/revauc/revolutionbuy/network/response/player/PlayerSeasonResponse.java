package com.revauc.revolutionbuy.network.response.player;

import com.revauc.revolutionbuy.network.BaseResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerSeasonResponse extends BaseResponse {

    private PlayerSeasonData response;

    public PlayerSeasonData getResponse() {
        return response;
    }
}
