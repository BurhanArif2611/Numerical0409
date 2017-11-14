package com.revauc.revolutionbuy.listeners;

import com.revauc.revolutionbuy.network.response.profile.CountryDto;

/**
 * Created by nishant on 02/10/17.
 */

public interface OnCountrySelectListener {

    public void onCountrySelected(CountryDto countryDto);
}
