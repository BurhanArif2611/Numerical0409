package com.revauc.revolutionbuy.ui.auth;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ActivityCountryChooserBinding;
import com.revauc.revolutionbuy.listeners.OnCitySelectListener;
import com.revauc.revolutionbuy.listeners.OnCountrySelectListener;
import com.revauc.revolutionbuy.listeners.OnStateSelectListener;
import com.revauc.revolutionbuy.network.BaseResponse;
import com.revauc.revolutionbuy.network.RequestController;
import com.revauc.revolutionbuy.network.response.profile.CityDto;
import com.revauc.revolutionbuy.network.response.profile.CityResponse;
import com.revauc.revolutionbuy.network.response.profile.CountryDto;
import com.revauc.revolutionbuy.network.response.profile.CountryResponse;
import com.revauc.revolutionbuy.network.response.profile.StateDto;
import com.revauc.revolutionbuy.network.response.profile.StateResponse;
import com.revauc.revolutionbuy.network.retrofit.AuthWebServices;
import com.revauc.revolutionbuy.network.retrofit.DefaultApiObserver;
import com.revauc.revolutionbuy.ui.BaseActivity;
import com.revauc.revolutionbuy.ui.auth.adapters.CityAdapter;
import com.revauc.revolutionbuy.ui.auth.adapters.CountryAdapter;
import com.revauc.revolutionbuy.ui.auth.adapters.StateAdapter;
import com.revauc.revolutionbuy.util.Constants;
import com.revauc.revolutionbuy.util.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 *  24/09/17.
 */

public class CountryChooserActivity extends BaseActivity implements View.OnClickListener, OnCountrySelectListener,OnStateSelectListener,OnCitySelectListener {


    private ActivityCountryChooserBinding mBinding;
    private String[] mCurrencyCodes;
    private CountryAdapter mCountryAdapter;
    private int chooserType;
    private int stateId;
    private int countryId;
    private List<CountryDto> mCountriesList;
    private List<StateDto> mStateList;
    private StateAdapter mStateAdapter;
    private List<CityDto> mCityList;
    private CityAdapter mCityAdapter;
    private boolean isFetching;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_country_chooser);
        mBinding.toolbarChooser.ivToolBarLeft.setImageResource(R.drawable.ic_back_blue);
        mBinding.toolbarChooser.ivToolBarLeft.setVisibility(View.VISIBLE);
        mBinding.toolbarChooser.ivToolBarLeft.setOnClickListener(this);


        chooserType = getIntent().getIntExtra(Constants.EXTRA_CHOOSE_TYPE,0);
        countryId = getIntent().getIntExtra(Constants.EXTRA_COUNTRY_ID,0);
        stateId = getIntent().getIntExtra(Constants.EXTRA_STATE_ID,0);
        if(chooserType==Constants.CHOOSE_TYPE_COUNTRY)
        {
            mBinding.toolbarChooser.txvToolbarGeneralCenter.setText(R.string.select_country);
            mBinding.editCountry.setHint("Search by country");
            mBinding.imageCountry.setImageResource(R.drawable.ic_country);

        }
        if(chooserType==Constants.CHOOSE_TYPE_STATE)
        {
            mBinding.toolbarChooser.txvToolbarGeneralCenter.setText(R.string.select_state);
            mBinding.editCountry.setHint("Search by state");
            mBinding.imageCountry.setImageResource(R.drawable.ic_state);
        }
        if(chooserType==Constants.CHOOSE_TYPE_CITY)
        {
            mBinding.toolbarChooser.txvToolbarGeneralCenter.setText(R.string.select_city);
            mBinding.editCountry.setHint("Search by city");
            mBinding.imageCountry.setImageResource(R.drawable.ic_city);
        }

        mBinding.editCountry.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(isFetching)
                {
                    return false;
                }
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String keyword = mBinding.editCountry.getText().toString();
                    if (!StringUtils.isNullOrEmpty(keyword)) {
                        hideKeyboard();
                        attemptSearch(keyword);
                    } else {
                        showSnackBarFromBottom("Please enter something to search", mBinding.mainContainer, true);
                    }
                    return true;
                }
                return false;
            }
        });

    }

    private void attemptSearch(String keyword) {
        if(chooserType==Constants.CHOOSE_TYPE_COUNTRY)
        {
            fetchCountries(keyword);
        }
        if(chooserType==Constants.CHOOSE_TYPE_STATE)
        {
            fetchStates(countryId,keyword);

        }
        if(chooserType==Constants.CHOOSE_TYPE_CITY)
        {
            fetchCities(countryId,stateId,keyword);
        }
    }

    private void fetchCountries(String searchKey) {
        showProgressBar();
        AuthWebServices apiService = RequestController.createRetrofitRequest(false);

        apiService.getCountries(searchKey).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<CountryResponse>(this) {

            @Override
            public void onResponse(CountryResponse response) {
                hideProgressBar();
                if (response != null && response.isSuccess()) {
                    setupCountries(response.getResult().getCountry());
                } else {
                    showSnackBarFromBottom(response.getMessage(), mBinding.mainContainer, true);
                }

            }

            @Override
            public void onError(Throwable call, BaseResponse baseResponse) {
                hideProgressBar();
                if (baseResponse != null) {
                    String errorMessage = baseResponse.getMessage();
                    showSnackBarFromBottom(errorMessage, mBinding.mainContainer, true);
                }
            }
        });
    }

    private void fetchStates(int countryId,String searchKey) {
        showProgressBar();
        AuthWebServices apiService = RequestController.createRetrofitRequest(false);

        apiService.getStates(countryId,searchKey).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<StateResponse>(this) {

            @Override
            public void onResponse(StateResponse response) {
                hideProgressBar();
                if (response != null && response.isSuccess()) {
                    setupStates(response.getResult().getState());
                } else {
                    showSnackBarFromBottom(response.getMessage(), mBinding.mainContainer, true);
                }

            }

            @Override
            public void onError(Throwable call, BaseResponse baseResponse) {
                hideProgressBar();
                if (baseResponse != null) {
                    String errorMessage = baseResponse.getMessage();
                    showSnackBarFromBottom(errorMessage, mBinding.mainContainer, true);
                }
            }
        });
    }

    private void fetchCities(int countryId, int stateId,String searchKey) {
        showProgressBar();
        AuthWebServices apiService = RequestController.createRetrofitRequest(false);

        apiService.getCities(countryId, stateId,searchKey).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<CityResponse>(this) {

            @Override
            public void onResponse(CityResponse response) {
                hideProgressBar();
                if (response != null && response.isSuccess()) {
                    setupCities(response.getResult().getCity());
                } else {
                    showSnackBarFromBottom(response.getMessage(), mBinding.mainContainer, true);
                }

            }

            @Override
            public void onError(Throwable call, BaseResponse baseResponse) {
                hideProgressBar();
                if (baseResponse != null) {
                    String errorMessage = baseResponse.getMessage();
                    showSnackBarFromBottom(errorMessage, mBinding.mainContainer, true);
                }
            }
        });
    }

    private void setupCountries(List<CountryDto> country)
    {
        mCountriesList = country;
        RecyclerView.LayoutManager lay = new LinearLayoutManager(CountryChooserActivity.this);
        mCountryAdapter = new CountryAdapter(CountryChooserActivity.this,mCountriesList,this);
        mBinding.recyclerView.setLayoutManager(lay);
        mBinding.recyclerView.setAdapter(mCountryAdapter);
    }

    private void setupStates(List<StateDto> states)
    {
        mStateList = states;
        RecyclerView.LayoutManager lay = new LinearLayoutManager(CountryChooserActivity.this);
        mStateAdapter = new StateAdapter(CountryChooserActivity.this,mStateList,this);
        mBinding.recyclerView.setLayoutManager(lay);
        mBinding.recyclerView.setAdapter(mStateAdapter);
    }

    private void setupCities(List<CityDto> cities)
    {
        mCityList = cities;
        RecyclerView.LayoutManager lay = new LinearLayoutManager(CountryChooserActivity.this);
        mCityAdapter = new CityAdapter(CountryChooserActivity.this,mCityList,this);
        mBinding.recyclerView.setLayoutManager(lay);
        mBinding.recyclerView.setAdapter(mCityAdapter);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_tool_bar_left:
                onBackPressed();
                break;
        }

    }

    @Override
    public void onCitySelected(CityDto cityDto) {
        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_CITY_ID,cityDto.getId());
        intent.putExtra(Constants.EXTRA_CHOOSE_TYPE,Constants.CHOOSE_TYPE_CITY);
        intent.putExtra(Constants.EXTRA_LOCATION_VALUE,cityDto.getName());
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    @Override
    public void onCountrySelected(CountryDto countryDto) {
        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_COUNTRY_ID,countryDto.getId());
        intent.putExtra(Constants.EXTRA_PHONE_CODE,countryDto.getPhoneCode());
        intent.putExtra(Constants.EXTRA_CHOOSE_TYPE,Constants.CHOOSE_TYPE_COUNTRY);
        intent.putExtra(Constants.EXTRA_LOCATION_VALUE,countryDto.getName());
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    @Override
    public void onStateSelected(StateDto stateDto) {
        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_STATE_ID,stateDto.getId());
        intent.putExtra(Constants.EXTRA_CHOOSE_TYPE,Constants.CHOOSE_TYPE_STATE);
        intent.putExtra(Constants.EXTRA_LOCATION_VALUE,stateDto.getName());
        setResult(Activity.RESULT_OK,intent);
        finish();
    }
}

