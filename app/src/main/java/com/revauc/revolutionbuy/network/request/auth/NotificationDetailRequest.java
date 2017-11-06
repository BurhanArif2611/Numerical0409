package com.revauc.revolutionbuy.network.request.auth;


public class NotificationDetailRequest {

    private Integer notificationId;

    public NotificationDetailRequest(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }
}
