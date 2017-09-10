package com.revauc.revolutionbuy.network.response.props;

import com.revauc.revolutionbuy.network.BaseResponse;

/**
 * Created by hemant on 07/05/17.
 */

public class PropsResponse extends BaseResponse {

    private PropsData response;

    public PropsData getResponse() {
        return response;
    }

    public void setResponse(PropsData response) {
        this.response = response;
    }
}
