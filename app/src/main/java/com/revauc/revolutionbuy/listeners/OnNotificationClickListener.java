package com.revauc.revolutionbuy.listeners;


import com.revauc.revolutionbuy.network.response.notification.P2FNotification;

public interface OnNotificationClickListener {

    void onNotificationClicked(int position, P2FNotification p2FNotification);

}
