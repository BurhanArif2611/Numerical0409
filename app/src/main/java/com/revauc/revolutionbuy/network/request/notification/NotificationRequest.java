package com.revauc.revolutionbuy.network.request.notification;


public class NotificationRequest {

    private int currentPage;
    private int currentSize;

    public NotificationRequest(int mCurrentPage, int mCurrentSize) {
        this.currentPage = mCurrentPage;
        this.currentSize = mCurrentSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getCurrentSize() {
        return currentSize;
    }
}
