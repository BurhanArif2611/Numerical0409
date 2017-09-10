package com.revauc.revolutionbuy.event;

/**
 * Created by hemant on 19/05/17.
 */

public class FabSelection {

   private boolean isClicked ;
   private String fragmentId ;

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public String getFragmentId() {
        return fragmentId;
    }

    public void setFragmentId(String fragmentId) {
        this.fragmentId = fragmentId;
    }

    public FabSelection(boolean isClicked, String fragmentId) {
        this.isClicked = isClicked;
        this.fragmentId = fragmentId;

    }
}
