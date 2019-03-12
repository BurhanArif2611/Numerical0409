package com.revauc.revolutionbuy.network.response.profile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.revauc.revolutionbuy.network.response.buyer.BuyerProductDto;
import com.revauc.revolutionbuy.network.response.buyer.PurchasedProductDto;
import com.revauc.revolutionbuy.network.response.seller.SellerOfferDto;

import java.util.List;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationDetailResult {

//    private Object buyerProduct;
    private SellerOfferDto sellerProduct;
//
//    public Object getBuyerProduct() {
//        return buyerProduct;
//    }

    public SellerOfferDto getSellerProduct() {
        return sellerProduct;
    }
}
