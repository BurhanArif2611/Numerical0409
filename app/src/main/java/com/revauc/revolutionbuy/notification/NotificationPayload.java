/*
 * Copyright © 2017 Thrive fantasy. All rights reserved.
 * Developed by Appster.
 */

package com.revauc.revolutionbuy.notification;


public class NotificationPayload {

    private String type;
    private String message;
    private int notificationId;
    private int notificationtypeId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getNotificationtypeId() {
        return notificationtypeId;
    }

    public void setNotificationtypeId(int notificationtypeId) {
        this.notificationtypeId = notificationtypeId;
    }
}
