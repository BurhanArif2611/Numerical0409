package com.revauc.revolutionbuy.network.response.contest;

import com.revauc.revolutionbuy.network.BaseResponse;

/**
 * Created by hemant on 28/04/17.
 */

public class ContestHistoryResponse extends BaseResponse{

    private ContestHistoryListData response;

    public ContestHistoryListData getResponse() {
        return response;
    }

    public void setResponse(ContestHistoryListData response) {
        this.response = response;
    }
}
