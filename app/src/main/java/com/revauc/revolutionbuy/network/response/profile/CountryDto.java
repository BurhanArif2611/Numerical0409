package com.revauc.revolutionbuy.network.response.profile;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountryDto {

    private int id;
    private String countryCode;
    private String name;
    private String phoneCode;

    public int getId() {
        return id;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getName() {
        return name;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    @Override
    public String toString() {
        return name;
    }
}
