package com.revauc.revolutionbuy.network.response.contest;

import com.revauc.revolutionbuy.network.response.auth.Pagination;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by hemant on 28/04/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EntrantListData {

    private List<Entrant> data = null;
    private Pagination pagination;

    public List<Entrant> getData() {
        return data;
    }

    public void setData(List<Entrant> data) {
        this.data = data;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
