package com.revauc.revolutionbuy.network.request.auth;

/**
 * Created by hemant on 05/05/17.
 */

public class PicksRequest {

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public PicksRequest(int contestId, int userContestId,int currentPage, int currentSize) {
        this.contestId = contestId;
        this.userContestId = userContestId;
        this.currentPage = currentPage;
        this.currentSize = currentSize;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    public void setContestId(int contestId) {

        this.contestId = contestId;
    }

    private int contestId;
    private int currentPage;
    private int userContestId;
    private int currentSize;

    public int getContestId() {
        return contestId;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public int getUserContestId() {
        return userContestId;
    }

    public void setUserContestId(int userContestId) {
        this.userContestId = userContestId;
    }
}
