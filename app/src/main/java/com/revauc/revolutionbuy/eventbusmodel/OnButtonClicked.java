package com.revauc.revolutionbuy.eventbusmodel;

/**
 * Created by nishant on 26/09/17.
 */

public class OnButtonClicked {

    int requestCode;

    public OnButtonClicked(int requestCode) {
        this.requestCode = requestCode;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }
}
