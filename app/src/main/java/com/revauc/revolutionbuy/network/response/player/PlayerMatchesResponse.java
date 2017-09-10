package com.revauc.revolutionbuy.network.response.player;

import com.revauc.revolutionbuy.network.BaseResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerMatchesResponse extends BaseResponse {

    private PlayerMatchesData response;

    public PlayerMatchesData getResponse() {
        return response;
    }
}
