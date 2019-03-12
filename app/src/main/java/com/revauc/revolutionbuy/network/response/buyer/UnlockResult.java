package com.revauc.revolutionbuy.network.response.buyer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UnlockResult {


    private int unlockPayment;

    public int getUnlockPayment() {
        return unlockPayment;
    }
}
