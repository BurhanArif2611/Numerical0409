/*
 * Copyright © 2017 Thrive fantasy. All rights reserved.
 * Developed by Appster.
 */

package com.revauc.revolutionbuy.notification;


import android.os.Parcel;
import android.os.Parcelable;

public class NotificationPayload implements Parcelable {

    private int id;
    private int type;
    private String badge;
    private String message;
    private String description;
    private String buyerProductId;
    private String sellerProductId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBuyerProductId() {
        return buyerProductId;
    }

    public void setBuyerProductId(String buyerProductId) {
        this.buyerProductId = buyerProductId;
    }

    public String getSellerProductId() {
        return sellerProductId;
    }

    public void setSellerProductId(String sellerProductId) {
        this.sellerProductId = sellerProductId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.type);
        dest.writeString(this.badge);
        dest.writeString(this.message);
        dest.writeString(this.description);
        dest.writeString(this.buyerProductId);
        dest.writeString(this.sellerProductId);
    }

    public NotificationPayload() {
    }

    protected NotificationPayload(Parcel in) {
        this.id = in.readInt();
        this.type = in.readInt();
        this.badge = in.readString();
        this.message = in.readString();
        this.description = in.readString();
        this.buyerProductId = in.readString();
        this.sellerProductId = in.readString();
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
