package com.revauc.revolutionbuy.network.response.contest;

import com.revauc.revolutionbuy.network.BaseResponse;

/**
 * Created by hemant on 28/04/17.
 */

public class ContestResponse extends BaseResponse{

    private ContestListData response;

    public ContestListData getResponse() {
        return response;
    }

    public void setResponse(ContestListData response) {
        this.response = response;
    }
}
