package com.revauc.revolutionbuy.network.request.contest;

/**
 * Created by hemant on 28/04/17.
 */

public class ContestRequest {

//     "leagueType":"MLB", (all, "MLB","NBA","NFL")
    private String leagueType;
    // "contestType":null,  (all, "TRADITIONAL","VERSUS")
    private String contestType;
    private int featured;
    private int currentPage ;
    private int currentSize ;

    public ContestRequest(String leagueType, String contestType, int featured, int mCurrentPage, int mCurrentSize) {
        this.leagueType = leagueType;
        this.contestType = contestType;
        this.featured = featured;
        this.currentPage = mCurrentPage;
        this.currentSize = mCurrentSize;
    }

    public String getLeagueType() {
        return leagueType;
    }

    public String getContestType() {
        return contestType;
    }

    public int getFeatured() {
        return featured;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getCurrentSize() {
        return currentSize;
    }
}
