package com.revauc.revolutionbuy.network.response.contest;

import com.revauc.revolutionbuy.network.response.auth.Pagination;

import java.util.List;

/**
 * Created by hemant on 28/04/17.
 */

public class ContestHistoryListData {


    private List<ContestHistory> data = null;
    private Pagination pagination;

    public List<ContestHistory> getData() {
        return data;
    }

    public void setData(List<ContestHistory> data) {
        this.data = data;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

}
