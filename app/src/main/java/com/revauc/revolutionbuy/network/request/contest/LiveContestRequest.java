package com.revauc.revolutionbuy.network.request.contest;

/**
 * Created by hemant on 28/04/17.
 */

public class LiveContestRequest extends ContestRequest{

    private int sortType ;

    public LiveContestRequest(String leagueType, String contestType, int featured, int mCurrentPage, int mCurrentSize,int sortType) {
       super(leagueType, contestType, featured, mCurrentPage, mCurrentSize);
        this.sortType = sortType;
    }

    public int getSortType() {
        return sortType;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }
}
