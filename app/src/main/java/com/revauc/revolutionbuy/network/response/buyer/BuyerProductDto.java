package com.revauc.revolutionbuy.network.response.buyer;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.revauc.revolutionbuy.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BuyerProductDto implements Parcelable {

    private int id;
    private String title;
    private String description;
    private List<BuyerProductCategoryDto> buyerProductCategories;
    private List<BuyerProductImageDto> buyerProductImages;

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

    public List<BuyerProductImageDto> getBuyerProductImages() {
        return buyerProductImages;
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
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeList(this.buyerProductCategories);
        dest.writeList(this.buyerProductImages);
    }

    public BuyerProductDto() {
    }

    protected BuyerProductDto(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.buyerProductCategories = new ArrayList<BuyerProductCategoryDto>();
        in.readList(this.buyerProductCategories, BuyerProductCategoryDto.class.getClassLoader());
        this.buyerProductImages = new ArrayList<BuyerProductImageDto>();
        in.readList(this.buyerProductImages, BuyerProductImageDto.class.getClassLoader());
    }

    public static final Parcelable.Creator<BuyerProductDto> CREATOR = new Parcelable.Creator<BuyerProductDto>() {
        @Override
        public BuyerProductDto createFromParcel(Parcel source) {
            return new BuyerProductDto(source);
        }

        @Override
        public BuyerProductDto[] newArray(int size) {
            return new BuyerProductDto[size];
        }
    };
}
