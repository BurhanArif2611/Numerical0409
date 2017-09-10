package com.revauc.revolutionbuy.network.response.contest;

import com.revauc.revolutionbuy.network.BaseResponse;

import java.util.List;

/**
 * Created by hemant on 22/06/17.
 */

public class HeadToHeadResponse extends BaseResponse {

    public HeadToHeadPropsResponse getResponse() {
        return response;
    }

    public void setResponse(HeadToHeadPropsResponse response) {
        this.response = response;
    }

    private HeadToHeadPropsResponse response;

}
