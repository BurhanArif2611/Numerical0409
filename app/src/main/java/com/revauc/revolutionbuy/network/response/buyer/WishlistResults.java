package com.revauc.revolutionbuy.network.response.buyer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WishlistResults {

    private List<BuyerProductDto> buyerProduct;
    private int totalCount;

    public List<BuyerProductDto> getBuyerProduct() {
        return buyerProduct;
    }

    public int getTotalCount() {
        return totalCount;
    }
}
