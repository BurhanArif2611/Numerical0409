package com.revauc.revolutionbuy.network.response.profile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.revauc.revolutionbuy.network.response.buyer.BuyerProductDto;
import com.revauc.revolutionbuy.network.response.buyer.PurchasedProductDto;
import com.revauc.revolutionbuy.network.response.seller.SellerOfferDto;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationDetailPurchaseResult {

    private BuyerProductDto buyerProduct;
    private SellerOfferDto sellerProduct;
    private PurchasedProductDto purchasedProduct;

    public BuyerProductDto getBuyerProduct() {
        return buyerProduct;
    }

    public SellerOfferDto getSellerProduct() {
        return sellerProduct;
    }

    public PurchasedProductDto getPurchasedProduct() {
        return purchasedProduct;
    }
}
