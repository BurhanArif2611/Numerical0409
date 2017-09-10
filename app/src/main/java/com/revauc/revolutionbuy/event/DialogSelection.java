package com.revauc.revolutionbuy.event;

/**
 * Created by hemant on 19/05/17.
 */

public class DialogSelection {


    private boolean isFeatured;
    private int selectedSort;

    public DialogSelection(boolean isFeatured, int selectedSort) {
        this.isFeatured = isFeatured;
        this.selectedSort = selectedSort;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean featured) {
        isFeatured = featured;
    }

    public int getSelectedSort() {
        return selectedSort;
    }

    public void setSelectedSort(int selectedSort) {
        this.selectedSort = selectedSort;
    }
}
