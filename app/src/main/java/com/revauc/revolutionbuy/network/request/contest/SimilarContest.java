package com.revauc.revolutionbuy.network.request.contest;

/**
 * Created by hemant on 13/06/17.
 */

public class SimilarContest {
    private String id;
    private String userContestId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserContestId() {
        return userContestId;
    }

    public void setUserContestId(String userContestId) {
        this.userContestId = userContestId;
    }

    public SimilarContest(String id, String userContestId) {
        this.id = id;
        this.userContestId = userContestId;
    }
}