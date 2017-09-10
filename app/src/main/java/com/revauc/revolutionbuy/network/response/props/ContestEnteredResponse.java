package com.revauc.revolutionbuy.network.response.props;

import com.revauc.revolutionbuy.network.BaseResponse;

/**
 * Created by hemant on 24/05/17.
 */

public class ContestEnteredResponse extends BaseResponse {

    private  ContestPropsResponse response;

    public ContestPropsResponse getResponse() {
        return response;
    }

    public void setResponse(ContestPropsResponse response) {
        this.response = response;
    }
}
