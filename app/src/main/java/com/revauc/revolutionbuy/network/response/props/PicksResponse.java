package com.revauc.revolutionbuy.network.response.props;

import com.revauc.revolutionbuy.network.response.auth.Pagination;

import java.util.ArrayList;

/**
 * Created by hemant on 07/05/17.
 */

public class PicksResponse {

    private ArrayList<Picks> data = null;
    private Pagination pagination;

    public ArrayList<Picks> getData() {
        return data;
    }

    public void setData(ArrayList<Picks> data) {
        this.data = data;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
