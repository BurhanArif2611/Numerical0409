package com.revauc.revolutionbuy.network.response.buyer;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.revauc.revolutionbuy.network.response.UserDto;
import com.revauc.revolutionbuy.util.StringUtils;

import java.util.List;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchasedProductDto implements Parcelable {

    private int id;
    private int sellerProductId;
    private String title;
    private List<BuyerProductCategoryDto> buyerProductCategories;
    private List<SellerProductDto> sellerProducts;
    private String productTypeText;

    public int getId() {
        return id;
    }

    public int getSellerProductId() {
        return sellerProductId;
    }

    public String getTitle() {
        return title;
    }

    public List<BuyerProductCategoryDto> getBuyerProductCategories() {
        return buyerProductCategories;
    }

    public List<SellerProductDto> getSellerProducts() {
        return sellerProducts;
    }

    public String getProductTypeText() {
        return productTypeText;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.sellerProductId);
        dest.writeString(this.title);
        dest.writeTypedList(this.buyerProductCategories);
        dest.writeTypedList(this.sellerProducts);
        dest.writeString(this.productTypeText);
    }

    public PurchasedProductDto() {
    }

    protected PurchasedProductDto(Parcel in) {
        this.id = in.readInt();
        this.sellerProductId = in.readInt();
        this.title = in.readString();
        this.buyerProductCategories = in.createTypedArrayList(BuyerProductCategoryDto.CREATOR);
        this.sellerProducts = in.createTypedArrayList(SellerProductDto.CREATOR);
        this.productTypeText = in.readString();
    }

    public static final Creator<PurchasedProductDto> CREATOR = new Creator<PurchasedProductDto>() {
        @Override
        public PurchasedProductDto createFromParcel(Parcel source) {
            return new PurchasedProductDto(source);
        }

        @Override
        public PurchasedProductDto[] newArray(int size) {
            return new PurchasedProductDto[size];
        }
    };
}
