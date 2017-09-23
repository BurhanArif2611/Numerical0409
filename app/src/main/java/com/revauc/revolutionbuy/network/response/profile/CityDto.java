package com.revauc.revolutionbuy.network.response.profile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CityDto {

    private int id;
    private String countryCode;
    private String name;
    private String stateCode;
    private int stateId;

    public int getId() {
        return id;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getName() {
        return name;
    }

    public String getStateCode() {
        return stateCode;
    }

    public int getStateId() {
        return stateId;
    }

    @Override
    public String toString() {
        return name;
    }
}
