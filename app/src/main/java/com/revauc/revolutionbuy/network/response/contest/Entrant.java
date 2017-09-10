package com.revauc.revolutionbuy.network.response.contest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Entrant {

    private int userId;
    private String userName;
    private boolean isFollowing;
    private int entryCount;
    private int noOfEntries;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isFollowing() {
        return isFollowing;
    }

    public void setFollowing(boolean following) {
        isFollowing = following;
    }

    public int getEntryCount() {
        return entryCount;
    }

    public void setEntryCount(int entryCount) {
        this.entryCount = entryCount;
    }

    public int getNoOfEntries() {
        return noOfEntries;
    }

    public void setNoOfEntries(int noOfEntries) {
        this.noOfEntries = noOfEntries;
    }
}
