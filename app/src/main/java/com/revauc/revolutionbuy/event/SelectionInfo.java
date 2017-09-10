package com.revauc.revolutionbuy.event;

/**
 * Created by hemant on 31/05/17.
 */

public class SelectionInfo {

    private int positionFilled ;
    private int totalPosition ;
    private int maxPoints;
    private int icePicksSelected;
    private boolean hideME;

    public boolean isHideME() {
        return hideME;
    }

    public SelectionInfo() {
        hideME = true;
    }

    public SelectionInfo(int positionFilled, int totalPosition, int maxPoints, int icePicksSelected) {
        this.positionFilled = positionFilled;
        this.totalPosition = totalPosition;
        this.maxPoints = maxPoints;
        this.icePicksSelected = icePicksSelected;
        hideME =false;
    }

    public int getPositionFilled() {
        return positionFilled;
    }

    public void setPositionFilled(int positionFilled) {
        this.positionFilled = positionFilled;
    }

    public int getTotalPosition() {
        return totalPosition;
    }

    public void setTotalPosition(int totalPosition) {
        this.totalPosition = totalPosition;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    public int getIcePicksSelected() {
        return icePicksSelected;
    }

    public void setIcePicksSelected(int icePicksSelected) {
        this.icePicksSelected = icePicksSelected;
    }
}
