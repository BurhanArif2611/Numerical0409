package com.revauc.revolutionbuy.network.response.contest;

import com.revauc.revolutionbuy.network.BaseResponse;

import java.util.List;


public class PropsDeltaResponse extends BaseResponse {

    public List<PropsDelta> getResponse() {
        return response;
    }

    public void setResponse(List<PropsDelta> response) {
        this.response = response;
    }

    private List<PropsDelta> response;

}
