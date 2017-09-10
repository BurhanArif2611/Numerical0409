package com.revauc.revolutionbuy.network.response.contest;

/**
 * Created by hemant on 22/06/17.
 */

public class HeadToHeadProp {

    private LiveProps myPropPick;
    private LiveProps opponentsPropPick;

    public HeadToHeadProp(LiveProps myPropPick, LiveProps opponentsPropPick) {
        this.myPropPick = myPropPick;
        this.opponentsPropPick = opponentsPropPick;
    }

    public LiveProps getMyPropPick() {
        return myPropPick;
    }

    public void setMyPropPick(LiveProps myPropPick) {
        this.myPropPick = myPropPick;
    }

    public LiveProps getOpponentsPropPick() {
        return opponentsPropPick;
    }

    public void setOpponentsPropPick(LiveProps opponentsPropPick) {
        this.opponentsPropPick = opponentsPropPick;
    }
}
