package com.revauc.revolutionbuy.notification;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by hemant on 28/12/16.
 */

public class NotificationPayload implements Parcelable {

    private String type;
    private String message;
    private String notificationType;

    public String getContestID() {
        return contestID;
    }

    public void setContestID(String contestID) {
        this.contestID = contestID;
    }

    private String contestID;

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public NotificationPayload(String type, String message, String notificationType) {

        this.type = type;
        this.message = message;
        this.notificationType = notificationType;
    }


    public NotificationPayload() {
    }


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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeString(this.message);
        dest.writeString(this.notificationType);
        dest.writeString(this.contestID);
    }

    protected NotificationPayload(Parcel in) {
        this.type = in.readString();
        this.message = in.readString();
        this.notificationType = in.readString();
        this.contestID = in.readString();
    }

    public static final Creator<NotificationPayload> CREATOR = new Creator<NotificationPayload>() {
        @Override
        public NotificationPayload createFromParcel(Parcel source) {
            return new NotificationPayload(source);
        }

        @Override
        public NotificationPayload[] newArray(int size) {
            return new NotificationPayload[size];
        }
    };
}
