package com.revauc.revolutionbuy.network.request.notification;


public class ReadNotificationRequest {

    private String notificationId;

    public ReadNotificationRequest(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }
}
