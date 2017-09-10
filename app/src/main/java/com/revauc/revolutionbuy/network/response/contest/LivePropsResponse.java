package com.revauc.revolutionbuy.network.response.contest;

import com.revauc.revolutionbuy.network.BaseResponse;

import java.util.List;

/**
 * Created by hemant on 22/06/17.
 */

public class LivePropsResponse extends BaseResponse {

    public List<LiveProps> getResponse() {
        return response;
    }

    public void setResponse(List<LiveProps> response) {
        this.response = response;
    }

    private List<LiveProps> response;

}
