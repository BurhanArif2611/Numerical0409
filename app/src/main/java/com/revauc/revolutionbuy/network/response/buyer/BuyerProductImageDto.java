package com.revauc.revolutionbuy.network.response.buyer;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BuyerProductImageDto implements Parcelable {

    private int id;
    private String imageName;
    private int primaryImage;

    public int getId() {
        return id;
    }

    public String getImageName() {
        return imageName;
    }

    public int getPrimaryImage() {
        return primaryImage;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.imageName);
        dest.writeInt(this.primaryImage);
    }

    public BuyerProductImageDto() {
    }

    protected BuyerProductImageDto(Parcel in) {
        this.id = in.readInt();
        this.imageName = in.readString();
        this.primaryImage = in.readInt();
    }

    public static final Parcelable.Creator<BuyerProductImageDto> CREATOR = new Parcelable.Creator<BuyerProductImageDto>() {
        @Override
        public BuyerProductImageDto createFromParcel(Parcel source) {
            return new BuyerProductImageDto(source);
        }

        @Override
        public BuyerProductImageDto[] newArray(int size) {
            return new BuyerProductImageDto[size];
        }
    };
}
