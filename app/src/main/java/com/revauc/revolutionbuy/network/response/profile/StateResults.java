package com.revauc.revolutionbuy.network.response.profile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StateResults {

    private List<StateDto> state;

    public List<StateDto> getState() {
        return state;
    }
}
