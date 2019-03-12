package com.revauc.revolutionbuy.listeners;

import com.revauc.revolutionbuy.network.response.profile.CountryDto;
import com.revauc.revolutionbuy.network.response.profile.StateDto;



public interface OnStateSelectListener {

    public void onStateSelected(StateDto stateDto);
}
