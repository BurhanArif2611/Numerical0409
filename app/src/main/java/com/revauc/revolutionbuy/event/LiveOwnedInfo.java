package com.revauc.revolutionbuy.event;

/**
 * Created by hemant on 31/05/17.
 */

public class LiveOwnedInfo {

    private int OwnedPoints;
    private int OwnedPoints2;
    private boolean hideME;

    public boolean isHideME() {
        return hideME;
    }

    public LiveOwnedInfo() {
        hideME = true;
    }

    public LiveOwnedInfo(int OwnedPoints, int OwnedPoints2) {
        this.OwnedPoints = OwnedPoints;
        this.OwnedPoints2 = OwnedPoints2;
        hideME = false;
    }

    public int getOwnedPoints() {
        return OwnedPoints;
    }

    public void setOwnedPoints(int ownedPoints) {
        OwnedPoints = ownedPoints;
    }

    public int getOwnedPoints2() {
        return OwnedPoints2;
    }

    public void setOwnedPoints2(int ownedPoints2) {
        OwnedPoints2 = ownedPoints2;
    }
}
