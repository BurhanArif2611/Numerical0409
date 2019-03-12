package com.revauc.revolutionbuy.listeners;

import com.revauc.revolutionbuy.network.response.buyer.BuyerProductDto;
import com.revauc.revolutionbuy.network.response.profile.NotificationDto;

/**
 *  02/10/17.
 */

public interface OnNotificationClickListener {

    public void onNotificationClicked(NotificationDto notificationDto,int position);
}
