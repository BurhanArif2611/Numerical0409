package com.revauc.revolutionbuy.listeners;

import com.revauc.revolutionbuy.network.response.buyer.BuyerProductDto;
import com.revauc.revolutionbuy.network.response.buyer.PurchasedProductDto;

/**
 * Created by nishant on 02/10/17.
 */

public interface OnPurchasedClickListener {

    public void onPurchaseClicked(PurchasedProductDto purchasedProductDto);
}
