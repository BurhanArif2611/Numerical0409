package com.revauc.revolutionbuy.network.response.contest;

import com.revauc.revolutionbuy.network.BaseResponse;

import java.util.List;

/**
 * Created by hemant on 22/06/17.
 */

public class HeadToHeadPropsResponse extends BaseResponse {


    private List<LiveProps> myPropPicks;
    private List<LiveProps> opponentsPropPicks;

    public List<LiveProps> getMyPropPicks() {
        return myPropPicks;
    }

    public void setMyPropPicks(List<LiveProps> myPropPicks) {
        this.myPropPicks = myPropPicks;
    }

    public List<LiveProps> getOpponentsPropPicks() {
        return opponentsPropPicks;
    }

    public void setOpponentsPropPicks(List<LiveProps> opponentsPropPicks) {
        this.opponentsPropPicks = opponentsPropPicks;
    }
}
