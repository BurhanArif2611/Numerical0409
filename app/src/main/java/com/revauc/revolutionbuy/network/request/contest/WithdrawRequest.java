package com.revauc.revolutionbuy.network.request.contest;

/**
 * Created by hemant on 28/04/17.
 */

public class WithdrawRequest {

    private int contestId;
    private int userContestId;

    public WithdrawRequest(int contestId, int userContestId) {
        this.contestId = contestId;
        this.userContestId = userContestId;
    }

    public int getContestId() {
        return contestId;
    }

    public void setContestId(int contestId) {
        this.contestId = contestId;
    }

    public int getUserContestId() {
        return userContestId;
    }

    public void setUserContestId(int userContestId) {
        this.userContestId = userContestId;
    }
}
