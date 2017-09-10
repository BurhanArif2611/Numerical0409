package com.revauc.revolutionbuy.network.response.notification;

import com.revauc.revolutionbuy.network.response.auth.Pagination;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationListData {

    private List<P2FNotification> data = null;
    private Pagination pagination;

    public List<P2FNotification> getData() {
        return data;
    }

    public void setData(List<P2FNotification> data) {
        this.data = data;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
