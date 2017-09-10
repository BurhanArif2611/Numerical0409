package com.revauc.revolutionbuy.network.response.contest;

import com.revauc.revolutionbuy.network.BaseResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EntrantsResponse extends BaseResponse {

    private EntrantListData response;

    public EntrantListData getResponse() {
        return response;
    }
}
