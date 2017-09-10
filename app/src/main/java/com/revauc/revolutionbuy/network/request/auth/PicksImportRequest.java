package com.revauc.revolutionbuy.network.request.auth;

/**
 * Created by hemant on 05/05/17.
 */

public class PicksImportRequest {

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    public void setContestId(int contestId) {

        this.contestId = contestId;
    }

    private int contestId;
    private int userContestId;


    public PicksImportRequest(int contestId,int userContestId, int userImportedContest, int currentPage, int currentSize) {
        this.contestId = contestId;
        this.userContestId = userContestId;
        this.userImportedContest = userImportedContest;
        this.currentPage = currentPage;
        this.currentSize = currentSize;
    }

    private int userImportedContest;
    private int currentPage;
    private int currentSize;

    public int getUserImportedContest() {
        return userImportedContest;
    }

    public void setUserImportedContest(int userImportedContest) {
        this.userImportedContest = userImportedContest;
    }

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
