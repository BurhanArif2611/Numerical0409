package com.revauc.revolutionbuy.network.response.contest;

import com.revauc.revolutionbuy.network.response.auth.Pagination;

import java.util.List;

/**
 * Created by hemant on 28/04/17.
 */

public class ContestLiveListData {


    private List<ContestLive> data = null;
    private Pagination pagination;

    public List<ContestLive> getData() {
        return data;
    }

    public void setData(List<ContestLive> data) {
        this.data = data;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

}
