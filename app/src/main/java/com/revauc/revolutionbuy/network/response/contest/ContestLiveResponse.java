package com.revauc.revolutionbuy.network.response.contest;

import com.revauc.revolutionbuy.network.BaseResponse;

/**
 * Created by hemant on 28/04/17.
 */

public class ContestLiveResponse extends BaseResponse{

    private ContestLiveListData response;

    public ContestLiveListData getResponse() {
        return response;
    }

    public void setResponse(ContestLiveListData response) {
        this.response = response;
    }
}
