package com.revauc.revolutionbuy.network.response.profile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StateDto {

    private int id;
    private String countryCode;
    private String name;
    private String stateCode;
    private int countryId;

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

    public int getCountryId() {
        return countryId;
    }

    @Override
    public String toString() {
        return name;
    }
}
