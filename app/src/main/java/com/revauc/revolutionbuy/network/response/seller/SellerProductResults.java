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
public class SellerProductResults {

    private List<BuyerProductDto> product;
    private int totalCount;

    public List<BuyerProductDto> getProduct() {
        return product;
    }

    public int getTotalCount() {
        return totalCount;
    }
}
