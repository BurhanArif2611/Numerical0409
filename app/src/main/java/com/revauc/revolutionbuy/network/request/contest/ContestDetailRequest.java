package com.revauc.revolutionbuy.network.request.contest;

/**
 * Created by nishant on 28/04/17.
 */

public class ContestDetailRequest {


    private String contestId;

    private String userContestId;

    public String getContestId() {
        return contestId;
    }

    public void setContestId(String contestId) {
        this.contestId = contestId;
    }

    public String getUserContestId() {
        return userContestId;
    }

    public void setUserContestId(String userContestId) {
        this.userContestId = userContestId;
    }

    public ContestDetailRequest(String contestId, String userContestId) {
        this.contestId = contestId;
        this.userContestId = userContestId;
    }

//    public ContestDetailRequest(String contestId) {
//        this.contestId = contestId;
//        this.userContestId = "0";
//    }
}
