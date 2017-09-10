package com.revauc.revolutionbuy.network.request.auth;

/**
 * Created by hemant on 05/05/17.
 */

public class EntrantsRequest {

    private int contestId;
    private int isFollowing;
    private int currentPage;
    private int currentSize;

    public EntrantsRequest(int contestId, int isFollowing, int currentPage, int currentSize) {
        this.contestId = contestId;
        this.isFollowing = isFollowing;
        this.currentPage = currentPage;
        this.currentSize = currentSize;
    }

    public int getContestId() {
        return contestId;
    }

    public void setContestId(int contestId) {
        this.contestId = contestId;
    }

    public int getIsFollowing() {
        return isFollowing;
    }

    public void setIsFollowing(int isFollowing) {
        this.isFollowing = isFollowing;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }
}
