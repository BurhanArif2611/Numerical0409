package com.revauc.revolutionbuy.network.response.seller;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.revauc.revolutionbuy.network.response.UserDto;
import com.revauc.revolutionbuy.network.response.buyer.BuyerProductCategoryDto;
import com.revauc.revolutionbuy.network.response.buyer.BuyerProductDto;
import com.revauc.revolutionbuy.network.response.buyer.BuyerProductImageDto;
import com.revauc.revolutionbuy.util.StringUtils;

import java.util.List;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SellerOfferDto implements Parcelable {

    private int id;
    private int userId;
    private int buyerProductId;
    private int price;
    private int productType;
    private int state;
    private String description;
    private BuyerProductDto buyerProduct;
    private UserDto user;
    private List<BuyerProductImageDto> sellerProductImages;

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getBuyerProductId() {
        return buyerProductId;
    }

    public int getPrice() {
        return price;
    }

    public int getProductType() {
        return productType;
    }

    public int getState() {
        return state;
    }

    public String getDescription() {
        return description;
    }

    public UserDto getUser() {
        return user;
    }

    public BuyerProductDto getBuyerProduct() {
        return buyerProduct;
    }

    public List<BuyerProductImageDto> getSellerProductImages() {
        return sellerProductImages;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.userId);
        dest.writeInt(this.buyerProductId);
        dest.writeInt(this.price);
        dest.writeInt(this.productType);
        dest.writeInt(this.state);
        dest.writeString(this.description);
        dest.writeParcelable(this.buyerProduct, flags);
        dest.writeParcelable(this.user, flags);
        dest.writeTypedList(this.sellerProductImages);
    }

    public SellerOfferDto() {
    }

    protected SellerOfferDto(Parcel in) {
        this.id = in.readInt();
        this.userId = in.readInt();
        this.buyerProductId = in.readInt();
        this.price = in.readInt();
        this.productType = in.readInt();
        this.state = in.readInt();
        this.description = in.readString();
        this.buyerProduct = in.readParcelable(BuyerProductDto.class.getClassLoader());
        this.user = in.readParcelable(UserDto.class.getClassLoader());
        this.sellerProductImages = in.createTypedArrayList(BuyerProductImageDto.CREATOR);
    }

    public static final Creator<SellerOfferDto> CREATOR = new Creator<SellerOfferDto>() {
        @Override
        public SellerOfferDto createFromParcel(Parcel source) {
            return new SellerOfferDto(source);
        }

        @Override
        public SellerOfferDto[] newArray(int size) {
            return new SellerOfferDto[size];
        }
    };
}
