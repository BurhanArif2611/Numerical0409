package com.revauc.revolutionbuy.network.response.contest;

import com.revauc.revolutionbuy.network.BaseResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LiveEntrantsResponse extends BaseResponse {

    private LiveEntrantListData response;

    public LiveEntrantListData getResponse() {
        return response;
    }
}
