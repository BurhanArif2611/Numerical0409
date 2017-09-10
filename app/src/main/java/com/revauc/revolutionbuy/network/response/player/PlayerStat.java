package com.revauc.revolutionbuy.network.response.player;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by nishant on 02/06/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerStat {

    private String paramKey = null;
    private Double paramValue = null;

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public Double getParamValue() {
        return paramValue;
    }

    public void setParamValue(Double paramValue) {
        this.paramValue = paramValue;
    }
}
