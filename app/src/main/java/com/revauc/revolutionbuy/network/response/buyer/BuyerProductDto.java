package com.revauc.revolutionbuy.network.response.buyer;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.revauc.revolutionbuy.network.response.UserDto;
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
    private int userId;
    private int offerGet;
    private String title;
    private String description;
    private List<BuyerProductCategoryDto> buyerProductCategories;
    private List<BuyerProductImageDto> buyerProductImages;
    private UserDto user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOfferGet() {
        return offerGet;
    }

    public void setOfferGet(int offerGet) {
        this.offerGet = offerGet;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<BuyerProductCategoryDto> getBuyerProductCategories() {
        return buyerProductCategories;
    }

    public void setBuyerProductCategories(List<BuyerProductCategoryDto> buyerProductCategories) {
        this.buyerProductCategories = buyerProductCategories;
    }

    public List<BuyerProductImageDto> getBuyerProductImages() {
        return buyerProductImages;
    }

    public void setBuyerProductImages(List<BuyerProductImageDto> buyerProductImages) {
        this.buyerProductImages = buyerProductImages;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
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
        dest.writeInt(this.userId);
        dest.writeInt(this.offerGet);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeTypedList(this.buyerProductCategories);
        dest.writeTypedList(this.buyerProductImages);
        dest.writeParcelable(this.user, flags);
    }

    public BuyerProductDto() {
    }

    protected BuyerProductDto(Parcel in) {
        this.id = in.readInt();
        this.userId = in.readInt();
        this.offerGet = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.buyerProductCategories = in.createTypedArrayList(BuyerProductCategoryDto.CREATOR);
        this.buyerProductImages = in.createTypedArrayList(BuyerProductImageDto.CREATOR);
        this.user = in.readParcelable(UserDto.class.getClassLoader());
    }

    public static final Creator<BuyerProductDto> CREATOR = new Creator<BuyerProductDto>() {
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
