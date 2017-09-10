package com.revauc.revolutionbuy.network.request.contest;

/**
 * Created by hemant on 12/05/17.
 */

public class PropsSelected {

    private int adminContestPropsId;
    private boolean ice;
    private boolean over;
    private int playerId;

    public boolean isIcePrimary() {
        return icePrimary;
    }

    public void setIcePrimary(boolean icePrimary) {
        this.icePrimary = icePrimary;
    }

    private boolean icePrimary;

    public PropsSelected(int playerId,int adminContestPropsId, boolean ice, boolean over) {
        this.playerId = playerId;
        this.adminContestPropsId = adminContestPropsId;
        this.ice = ice;
        this.over = over;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getAdminContestPropsId() {
        return adminContestPropsId;
    }

    public void setAdminContestPropsId(int adminContestPropsId) {
        this.adminContestPropsId = adminContestPropsId;
    }

    public boolean isIce() {
        return ice;
    }

    public void setIce(boolean ice) {
        this.ice = ice;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }
}
