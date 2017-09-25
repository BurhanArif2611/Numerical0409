package com.revauc.revolutionbuy.eventbusmodel;

/**
 * Created by nishant on 26/09/17.
 */

public class OnButtonClicked {

    boolean isPositive;

    public OnButtonClicked(boolean isPositive) {
        this.isPositive = isPositive;
    }

    public boolean isPositive() {
        return isPositive;
    }

    public void setPositive(boolean positive) {
        isPositive = positive;
    }
}
