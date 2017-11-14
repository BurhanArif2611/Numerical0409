package com.revauc.revolutionbuy.listeners;

import com.revauc.revolutionbuy.network.response.profile.CountryDto;
import com.revauc.revolutionbuy.network.response.profile.StateDto;

/**
 * Created by nishant on 02/10/17.
 */

public interface OnStateSelectListener {

    public void onStateSelected(StateDto stateDto);
}
