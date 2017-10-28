package com.revauc.revolutionbuy.network.response.profile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationResults {

    private List<NotificationDto> notification;
    private int totalCount;
    private int totalUnreadCount;

    public List<NotificationDto> getNotification() {
        return notification;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getTotalUnreadCount() {
        return totalUnreadCount;
    }
}
