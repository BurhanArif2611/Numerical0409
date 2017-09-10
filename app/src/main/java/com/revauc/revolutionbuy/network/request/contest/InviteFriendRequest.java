package com.revauc.revolutionbuy.network.request.contest;

/**
 * Created by hemant on 29/05/17.
 */

public class InviteFriendRequest {
//"inviteeEmail":"cheshta.sikka@appster.in",
//        "contestId": 4


    public String getInviteeEmail() {
        return inviteeEmail;
    }

    public void setInviteeEmail(String inviteeEmail) {
        this.inviteeEmail = inviteeEmail;
    }

    public int getContestId() {
        return contestId;
    }

    public void setContestId(int contestId) {
        this.contestId = contestId;
    }

    private String inviteeEmail;
    private int contestId;

    public InviteFriendRequest(String inviteeEmail, int contestId) {
        this.inviteeEmail = inviteeEmail;
        this.contestId = contestId;
    }
}
