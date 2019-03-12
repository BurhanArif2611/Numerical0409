package com.revauc.revolutionbuy.network.response.profile;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountryLocationDto implements Parcelable {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
    }

    public CountryLocationDto() {
    }

    protected CountryLocationDto(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<CountryLocationDto> CREATOR = new Parcelable.Creator<CountryLocationDto>() {
        @Override
        public CountryLocationDto createFromParcel(Parcel source) {
            return new CountryLocationDto(source);
        }

        @Override
        public CountryLocationDto[] newArray(int size) {
            return new CountryLocationDto[size];
        }
    };
}
