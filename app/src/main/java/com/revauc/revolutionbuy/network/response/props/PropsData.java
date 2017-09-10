package com.revauc.revolutionbuy.network.response.props;

import com.revauc.revolutionbuy.network.response.auth.Pagination;

import java.util.List;

/**
 * Created by hemant on 07/05/17.
 */

public class PropsData {

    private List<Props> data = null;
    private Pagination pagination;

    public List<Props> getData() {
        return data;
    }

    public void setData(List<Props> data) {
        this.data = data;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
