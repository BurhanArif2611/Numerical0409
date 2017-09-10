package com.revauc.revolutionbuy.network.response.props;

import com.revauc.revolutionbuy.network.BaseResponse;

/**
 * Created by hemant on 07/05/17.
 */

public class PicksData extends BaseResponse {

    private PicksResponse response;

    public PicksResponse getResponse() {
        return response;
    }

    public void setResponse(PicksResponse response) {
        this.response = response;
    }
}
