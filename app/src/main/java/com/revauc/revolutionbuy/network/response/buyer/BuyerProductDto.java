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
public class BuyerProductDto {

    private int id;
    private String title;
    private String description;
    private List<BuyerProductCategoryDto> buyerProductCategories;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<BuyerProductCategoryDto> getBuyerProductCategories() {
        return buyerProductCategories;
    }

    public String getBuyerProductCategoriesString()
    {
        String buyerCategories = "";
        for(BuyerProductCategoryDto buyerProductCategoryDto:buyerProductCategories)
        {
            buyerCategories = buyerCategories+buyerProductCategoryDto.getCategory().getName()+", ";
        }
        if(!StringUtils.isNullOrEmpty(buyerCategories))
            buyerCategories = buyerCategories.substring(0,buyerCategories.length()-2);


        return buyerCategories;
    }

}
