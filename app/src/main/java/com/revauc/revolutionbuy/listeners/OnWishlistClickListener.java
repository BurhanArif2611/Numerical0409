package com.revauc.revolutionbuy.listeners;

import android.view.View;

import com.revauc.revolutionbuy.network.response.buyer.BuyerProductDto;



public interface OnWishlistClickListener {

    public void onWishlistItemClicked(BuyerProductDto buyerProduct);
}
