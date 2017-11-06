package com.revauc.revolutionbuy.network.response.profile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationDto {

    private int id;
    private int type;
    private int buyerProductId;
    private int sellerProductId;
    private int isRead;
    private String description;
    private long timestamp;

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public int getBuyerProductId() {
        return buyerProductId;
    }

    public int getSellerProductId() {
        return sellerProductId;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public String getDescription() {
        return description;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
