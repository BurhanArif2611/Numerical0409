package com.revauc.revolutionbuy.network.response.contest;

import com.revauc.revolutionbuy.network.BaseResponse;

/**
 * Created by hemant on 28/04/17.
 */

public class ContestImportResponse extends BaseResponse{

    private ContestListImportData response;

    public ContestListImportData getResponse() {
        return response;
    }

    public void setResponse(ContestListImportData response) {
        this.response = response;
    }
}
