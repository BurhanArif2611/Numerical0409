package com.revauc.revolutionbuy.network.request.contest;

import java.util.ArrayList;

/**
 * Created by nishant on 28/04/17.
 */

public class ApplySilmilarRequest {

    public String adminContestsId;
    public String userContestId;
    public ArrayList<SimilarContest> similarContest;

    public ApplySilmilarRequest() {
    }

    public ApplySilmilarRequest(String adminContestsId,String userContestId, ArrayList<SimilarContest> similarContest) {
        this.adminContestsId = adminContestsId;
        this.userContestId = userContestId;
        this.similarContest = similarContest;
    }

}
