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
public class CategoryDto implements Parcelable {

    private int id;
    private String name;
    private boolean selected;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
    }

    public CategoryDto() {
    }

    protected CategoryDto(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.selected = in.readByte() != 0;
    }

    public static final Parcelable.Creator<CategoryDto> CREATOR = new Parcelable.Creator<CategoryDto>() {
        @Override
        public CategoryDto createFromParcel(Parcel source) {
            return new CategoryDto(source);
        }

        @Override
        public CategoryDto[] newArray(int size) {
            return new CategoryDto[size];
        }
    };
}
