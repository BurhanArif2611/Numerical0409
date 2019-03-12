package com.revauc.revolutionbuy.network.response.buyer;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.revauc.revolutionbuy.util.StringUtils;

import java.util.List;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BuyerProductCategoryDto implements Parcelable {

    private int id;

    private CategoryDto category;

    public int getId() {
        return id;
    }

    public CategoryDto getCategory() {
        return category;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeParcelable(this.category, flags);
    }

    public BuyerProductCategoryDto() {
    }

    protected BuyerProductCategoryDto(Parcel in) {
        this.id = in.readInt();
        this.category = in.readParcelable(CategoryDto.class.getClassLoader());
    }

    public static final Parcelable.Creator<BuyerProductCategoryDto> CREATOR = new Parcelable.Creator<BuyerProductCategoryDto>() {
        @Override
        public BuyerProductCategoryDto createFromParcel(Parcel source) {
            return new BuyerProductCategoryDto(source);
        }

        @Override
        public BuyerProductCategoryDto[] newArray(int size) {
            return new BuyerProductCategoryDto[size];
        }
    };
}
