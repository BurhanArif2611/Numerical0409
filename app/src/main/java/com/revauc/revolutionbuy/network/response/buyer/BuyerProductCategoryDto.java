package com.revauc.revolutionbuy.network.response.buyer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.revauc.revolutionbuy.util.StringUtils;

import java.util.List;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BuyerProductCategoryDto {

    private int id;

    private CategoryDto category;

    public int getId() {
        return id;
    }

    public CategoryDto getCategory() {
        return category;
    }
}
