package com.revauc.revolutionbuy.network.response.contest;

import com.revauc.revolutionbuy.network.response.auth.Pagination;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by hemant on 28/04/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LiveEntrantListData {

    private List<LiveEntrant> data = null;
    private List<LiveEntrant> userData = null;
    private Pagination pagination;

    public List<LiveEntrant> getData() {
        return data;
    }

    public void setData(List<LiveEntrant> data) {
        this.data = data;
    }

    public List<LiveEntrant> getUserData() {
        return userData;
    }

    public void setUserData(List<LiveEntrant> userData) {
        this.userData = userData;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
