package com.revauc.revolutionbuy.network.response.buyer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchasedResults {

    private List<PurchasedProductDto> buyerProduct;
    private int totalCount;

    public List<PurchasedProductDto> getBuyerProduct() {
        return buyerProduct;
    }

    public int getTotalCount() {
        return totalCount;
    }
}
