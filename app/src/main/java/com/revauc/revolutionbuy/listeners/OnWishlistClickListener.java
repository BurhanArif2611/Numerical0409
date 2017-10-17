package com.revauc.revolutionbuy.listeners;

import android.view.View;

import com.revauc.revolutionbuy.network.response.buyer.BuyerProductDto;

/**
 * Created by nishant on 02/10/17.
 */

public interface OnWishlistClickListener {

    public void onWishlistItemClicked(BuyerProductDto buyerProduct);
}
