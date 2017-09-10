package com.revauc.revolutionbuy.network.response.notification;

import com.revauc.revolutionbuy.network.BaseResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationResponse extends BaseResponse {

    private NotificationListData response;

    public NotificationListData getResponse() {
        return response;
    }
}
