package com.revauc.revolutionbuy.network.request.notification;


public class NotificationSettingRequest {

    private boolean notificationFlagStartOfContest;
    private boolean notificationFlagPropPicked;

    public NotificationSettingRequest(boolean notificationFlagStartOfContest, boolean notificationFlagPropPicked) {
        this.notificationFlagStartOfContest = notificationFlagStartOfContest;
        this.notificationFlagPropPicked = notificationFlagPropPicked;
    }

    public boolean isNotificationFlagStartOfContest() {
        return notificationFlagStartOfContest;
    }

    public void setNotificationFlagStartOfContest(boolean notificationFlagStartOfContest) {
        this.notificationFlagStartOfContest = notificationFlagStartOfContest;
    }

    public boolean isNotificationFlagPropPicked() {
        return notificationFlagPropPicked;
    }

    public void setNotificationFlagPropPicked(boolean notificationFlagPropPicked) {
        this.notificationFlagPropPicked = notificationFlagPropPicked;
    }
}
