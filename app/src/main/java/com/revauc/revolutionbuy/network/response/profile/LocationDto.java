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
public class LocationDto implements Parcelable {

    private int id;
    private String name;
    private int stateId;
    private StateLocationDto state;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStateId() {
        return stateId;
    }

    public StateLocationDto getState() {
        return state;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.stateId);
        dest.writeParcelable(this.state, flags);
    }

    public LocationDto() {
    }

    protected LocationDto(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.stateId = in.readInt();
        this.state = in.readParcelable(StateLocationDto.class.getClassLoader());
    }

    public static final Parcelable.Creator<LocationDto> CREATOR = new Parcelable.Creator<LocationDto>() {
        @Override
        public LocationDto createFromParcel(Parcel source) {
            return new LocationDto(source);
        }

        @Override
        public LocationDto[] newArray(int size) {
            return new LocationDto[size];
        }
    };
}
