package com.revauc.revolutionbuy.network.response.seller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.revauc.revolutionbuy.network.response.buyer.BuyerProductDto;

import java.util.List;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SellerOffersResults {

    private List<SellerOfferDto> sellerProduct;
    private int totalCount;

    public List<SellerOfferDto> getSellerProduct() {
        return sellerProduct;
    }

    public int getTotalCount() {
        return totalCount;
    }
}
