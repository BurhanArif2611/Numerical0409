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
public class StateLocationDto implements Parcelable {

    private int id;
    private String name;
    private int countryId;
    private CountryLocationDto country;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCountryId() {
        return countryId;
    }

    public CountryLocationDto getCountry() {
        return country;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.countryId);
        dest.writeParcelable(this.country, flags);
    }

    public StateLocationDto() {
    }

    protected StateLocationDto(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.countryId = in.readInt();
        this.country = in.readParcelable(CountryLocationDto.class.getClassLoader());
    }

    public static final Parcelable.Creator<StateLocationDto> CREATOR = new Parcelable.Creator<StateLocationDto>() {
        @Override
        public StateLocationDto createFromParcel(Parcel source) {
            return new StateLocationDto(source);
        }

        @Override
        public StateLocationDto[] newArray(int size) {
            return new StateLocationDto[size];
        }
    };
}
