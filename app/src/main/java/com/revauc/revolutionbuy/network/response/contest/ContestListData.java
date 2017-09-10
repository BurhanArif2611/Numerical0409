package com.revauc.revolutionbuy.network.response.contest;

import com.revauc.revolutionbuy.network.response.auth.Pagination;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Created by hemant on 28/04/17.
 */


@JsonIgnoreProperties(ignoreUnknown = true)
public class ContestListData {


    private ArrayList<Contest> data = null;
    private Pagination pagination;

    public ArrayList<Contest> getData() {
        return data;
    }

    public void setData(ArrayList<Contest> data) {
        this.data = data;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

}
