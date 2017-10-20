package com.revauc.revolutionbuy.ui.auth;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.AdapterView;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ActivityCreateProfileBinding;
import com.revauc.revolutionbuy.network.BaseResponse;
import com.revauc.revolutionbuy.network.RequestController;
import com.revauc.revolutionbuy.network.response.LoginResponse;
import com.revauc.revolutionbuy.network.response.UserDto;
import com.revauc.revolutionbuy.network.response.profile.CityDto;
import com.revauc.revolutionbuy.network.response.profile.CityResponse;
import com.revauc.revolutionbuy.network.response.profile.CountryDto;
import com.revauc.revolutionbuy.network.response.profile.CountryResponse;
import com.revauc.revolutionbuy.network.response.profile.StateDto;
import com.revauc.revolutionbuy.network.response.profile.StateResponse;
import com.revauc.revolutionbuy.network.retrofit.AuthWebServices;
import com.revauc.revolutionbuy.network.retrofit.DefaultApiObserver;
import com.revauc.revolutionbuy.ui.BaseActivity;
import com.revauc.revolutionbuy.ui.auth.adapters.AutoCompleteCityAdapter;
import com.revauc.revolutionbuy.ui.auth.adapters.AutoCompleteCountryAdapter;
import com.revauc.revolutionbuy.ui.auth.adapters.AutoCompleteStateAdapter;
import com.revauc.revolutionbuy.ui.dashboard.DashboardActivity;
import com.revauc.revolutionbuy.util.Constants;
import com.revauc.revolutionbuy.util.ImagePickerUtils;
import com.revauc.revolutionbuy.util.PreferenceUtil;
import com.revauc.revolutionbuy.util.StringUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * Screen shows Terms of Service.
 */
public class CreateProfileActivity extends BaseActivity implements View.OnClickListener {
    private ActivityCreateProfileBinding mBinding;
    private List<CountryDto> mCountries;
    private List<StateDto> mStates;
    private List<CityDto> mCities;
    private AutoCompleteCountryAdapter mCountriesAdapter;
    private AutoCompleteStateAdapter mStatesAdapter;
    private AutoCompleteCityAdapter mCitiesAdapter;
    private int selectedCountryId;
    private int selectedStateId;
    private int selectedCityId;
    private String mFilePath;
    private boolean isImageRemoved, isPhotoPresentFromServer;
    private boolean isFromSettings;

    private ImagePickerUtils.OnImagePickerListener imageListener = new ImagePickerUtils.OnImagePickerListener() {
        @Override
        public void success(String name, String path) {
            mFilePath = path;
            isImageRemoved = false;
            mBinding.imageProfile.setImageBitmap(BitmapFactory.decodeFile(path));
            mBinding.textAddPhoto.setText(R.string.change_photo);
        }

        @Override
        public void fail(String message) {
            showSnackBarFromBottom(message, mBinding.mainContainer, true);
            mFilePath = "";
            isImageRemoved = false;
        }

        @Override
        public void onImageRemove() {
            mFilePath = "";
            isImageRemoved = true;
            mBinding.imageProfile.setImageDrawable(ContextCompat.getDrawable(CreateProfileActivity.this,
                    R.drawable.ic_avatar_profile));
            mBinding.textAddPhoto.setText(R.string.add_photo);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_profile);

        isFromSettings = getIntent().getBooleanExtra(Constants.EXTRA_FROM_SETTINGS, false);


        if (isFromSettings) {
            UserDto userDto = PreferenceUtil.getUserProfile();
            mBinding.toolbarProfile.txvToolbarGeneralCenter.setText(R.string.edit_profile);
            mBinding.toolbarProfile.tvToolbarGeneralLeft.setText(R.string.cancel);
            mBinding.toolbarProfile.tvToolbarGeneralLeft.setVisibility(View.VISIBLE);
            mBinding.toolbarProfile.tvToolbarGeneralLeft.setOnClickListener(this);
            mBinding.toolbarProfile.tvToolbarGeneralRight.setText(R.string.done);
            //setting values
            mBinding.editName.setText(userDto.getName());
            mBinding.editAge.setText(userDto.getAge() + "");
            mBinding.editCountry.setText(userDto.getCity().getState().getCountry().getName());
            mBinding.editState.setText(userDto.getCity().getState().getName());
            mBinding.editCity.setText(userDto.getCity().getName());
            selectedCountryId = userDto.getCity().getState().getCountry().getId();
            selectedStateId = userDto.getCity().getState().getId();
            selectedCityId = userDto.getCity().getId();
            if (!StringUtils.isNullOrEmpty(userDto.getImageName())) {
                showProgressBar();
                Picasso.with(CreateProfileActivity.this).load(userDto.getImageName()).into(mBinding.imageProfile,
                        new Callback() {
                            @Override
                            public void onSuccess() {
                                hideProgressBar();
                                isPhotoPresentFromServer = true;
                            }

                            @Override
                            public void onError() {
                                hideProgressBar();
                                isPhotoPresentFromServer = false;
                            }
                        });

                mBinding.textAddPhoto.setText(R.string.change_photo);
            }
            loadLocations();
        } else {
            mBinding.toolbarProfile.txvToolbarGeneralCenter.setText(R.string.create_profile);
            mBinding.toolbarProfile.tvToolbarGeneralLeft.setText(R.string.cancel);
            mBinding.toolbarProfile.tvToolbarGeneralLeft.setVisibility(View.VISIBLE);
            mBinding.toolbarProfile.tvToolbarGeneralLeft.setOnClickListener(this);
            mBinding.toolbarProfile.tvToolbarGeneralRight.setText(R.string.next);
        }

        mBinding.toolbarProfile.tvToolbarGeneralRight.setVisibility(View.VISIBLE);
        mBinding.toolbarProfile.tvToolbarGeneralRight.setOnClickListener(this);
        mBinding.imageProfile.setOnClickListener(this);
        mBinding.editCountry.setOnClickListener(this);

        mBinding.editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString().trim())) {
                    mBinding.containerName.setBackgroundResource(R.drawable.ic_button_red_border);
                } else {
                    mBinding.containerName.setBackgroundResource(R.drawable.ic_button_blue_border);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mBinding.editAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString().trim())) {
                    mBinding.containerAge.setBackgroundResource(R.drawable.ic_button_red_border);
                } else {
                    mBinding.containerAge.setBackgroundResource(R.drawable.ic_button_blue_border);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mBinding.editCountry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString().trim())) {
                    mBinding.containerCountry.setBackgroundResource(R.drawable.ic_button_red_border);
                } else {
                    mBinding.containerCountry.setBackgroundResource(R.drawable.ic_button_blue_border);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mBinding.editState.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString().trim())) {
                    mBinding.containerState.setBackgroundResource(R.drawable.ic_button_red_border);
                } else {
                    mBinding.containerState.setBackgroundResource(R.drawable.ic_button_blue_border);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mBinding.editCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString().trim())) {
                    mBinding.containerCity.setBackgroundResource(R.drawable.ic_button_red_border);
                } else {
                    mBinding.containerCity.setBackgroundResource(R.drawable.ic_button_blue_border);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        fetchCountries();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_toolbar_general_left:
                onBackPressed();
                break;
            case R.id.tv_toolbar_general_right:
                validateDetailsEntered();
                break;
            case R.id.image_profile:
                if (!TextUtils.isEmpty(mFilePath) || isPhotoPresentFromServer) {
                    ImagePickerUtils.add(getSupportFragmentManager(), imageListener, true);
                } else {
                    ImagePickerUtils.add(getSupportFragmentManager(), imageListener, false);
                }
                break;
            default:
                break;
        }
    }

    private void validateDetailsEntered() {
        String name = mBinding.editName.getText().toString();
        String age = mBinding.editAge.getText().toString();
        if (StringUtils.isNullOrEmpty(name)) {
            showSnackBarFromBottom(getString(R.string.text_please_enter, "Name"), mBinding.mainContainer, true);
            mBinding.containerName.setBackgroundResource(R.drawable.ic_button_red_border);
        } else if (StringUtils.isNullOrEmpty(age)) {
            showSnackBarFromBottom(getString(R.string.text_please_enter, "Age"), mBinding.mainContainer, true);
            mBinding.containerAge.setBackgroundResource(R.drawable.ic_button_red_border);
        } else if (Integer.parseInt(age) < 18) {
            showSnackBarFromBottom(getString(R.string.profile_age_error), mBinding.mainContainer, true);
            mBinding.containerAge.setBackgroundResource(R.drawable.ic_button_red_border);
        } else if (selectedCountryId == 0) {
            showSnackBarFromBottom(getString(R.string.text_please_select, "country"), mBinding.mainContainer, true);
            mBinding.containerCountry.setBackgroundResource(R.drawable.ic_button_red_border);
        } else if (selectedStateId == 0) {
            showSnackBarFromBottom(getString(R.string.text_please_select, "state"), mBinding.mainContainer, true);
            mBinding.containerState.setBackgroundResource(R.drawable.ic_button_red_border);
        } else if (selectedCityId == 0) {
            showSnackBarFromBottom(getString(R.string.text_please_select, "city"), mBinding.mainContainer, true);
            mBinding.containerCity.setBackgroundResource(R.drawable.ic_button_red_border);
        } else {
            if (isFromSettings)
            {
                editProfile(name, age, selectedCityId + "");
            }
            else
            {
                Intent intent = new Intent(CreateProfileActivity.this,MobileVerificationActivity.class);
                intent.putExtra(Constants.EXTRA_USER_NAME,name);
                intent.putExtra(Constants.EXTRA_AGE,Integer.parseInt(age));
                intent.putExtra(Constants.EXTRA_CITY_ID,selectedCityId);
                intent.putExtra(Constants.EXTRA_PROFILE_IMAGE,mFilePath);
                startActivity(intent);
            }


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    private void fetchCountries() {
        showProgressBar();
        AuthWebServices apiService = RequestController.createRetrofitRequest(false);

        apiService.getCountries().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<CountryResponse>(this) {

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

    private void setupCountries(List<CountryDto> countries) {
        mCountries = countries;
        mCountriesAdapter = new AutoCompleteCountryAdapter(CreateProfileActivity.this, mCountries);
        mBinding.editCountry.setAdapter(mCountriesAdapter);
        mBinding.editCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                hideKeyboard();
                selectedCountryId = mCountriesAdapter.getItem(position).getId();
                mBinding.editState.setText("");
                mBinding.editCity.setText("");
                selectedCityId = 0;
                selectedStateId = 0;
                fetchStates(selectedCountryId);
            }
        });
    }

    private void fetchStates(int countryId) {
        AuthWebServices apiService = RequestController.createRetrofitRequest(false);

        apiService.getStates(countryId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<StateResponse>(this) {

            @Override
            public void onResponse(StateResponse response) {
                if (response != null && response.isSuccess()) {
                    setupStates(response.getResult().getState());
                } else {
                    showSnackBarFromBottom(response.getMessage(), mBinding.mainContainer, true);
                }

            }

            @Override
            public void onError(Throwable call, BaseResponse baseResponse) {
                if (baseResponse != null) {
                    String errorMessage = baseResponse.getMessage();
                    showSnackBarFromBottom(errorMessage, mBinding.mainContainer, true);
                }
            }
        });
    }

    private void setupStates(List<StateDto> states) {
        mStates = states;
        mStatesAdapter = new AutoCompleteStateAdapter(CreateProfileActivity.this, mStates);
        mBinding.editState.setAdapter(mStatesAdapter);
        mBinding.editState.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                hideKeyboard();
                selectedStateId = mStatesAdapter.getItem(position).getId();
                mBinding.editCity.setText("");
                selectedCityId = 0;
                fetchCities(selectedStateId, selectedStateId);

            }
        });
    }

    private void fetchCities(int countryId, int stateId) {
        AuthWebServices apiService = RequestController.createRetrofitRequest(false);

        apiService.getCities(countryId, stateId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<CityResponse>(this) {

            @Override
            public void onResponse(CityResponse response) {
                if (response != null && response.isSuccess()) {
                    setupCities(response.getResult().getCity());
                } else {
                    showSnackBarFromBottom(response.getMessage(), mBinding.mainContainer, true);
                }

            }

            @Override
            public void onError(Throwable call, BaseResponse baseResponse) {
                if (baseResponse != null) {
                    String errorMessage = baseResponse.getMessage();
                    showSnackBarFromBottom(errorMessage, mBinding.mainContainer, true);
                }
            }
        });
    }

    private void setupCities(List<CityDto> cities) {
        mCities = cities;
        mCitiesAdapter = new AutoCompleteCityAdapter(CreateProfileActivity.this, mCities);
        mBinding.editCity.setAdapter(mCitiesAdapter);
        mBinding.editCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                hideKeyboard();
                selectedCityId = mCitiesAdapter.getItem(position).getId();
            }
        });
    }

    private void editProfile(String name, String age, String cityId) {
        showProgressBar();

        //File creating from selected URL
        File file = null;
        if (mFilePath != null) {
            file = new File(mFilePath);
        }


        //Creating Profile Details
        RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody ageBody = RequestBody.create(MediaType.parse("text/plain"), "" + age);
        RequestBody cityBody = RequestBody.create(MediaType.parse("text/plain"), "" + cityId);

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("name", nameBody);
        map.put("age", ageBody);
        map.put("cityId", cityBody);

        MultipartBody.Part body = null;
        if (file != null) {
            // create RequestBody instance from file
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

            // MultipartBody.Part is used to send also the actual file name
            body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        }


        AuthWebServices apiService = RequestController.createRetrofitRequest(false);

        apiService.editProfile(map, body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DefaultApiObserver<LoginResponse>(this) {

                    @Override
                    public void onResponse(LoginResponse response) {
                        hideProgressBar();
                        if (response.isSuccess()) {
                            PreferenceUtil.setUserProfile(response.getResult().getUser());
                            if (isFromSettings) {
                                onBackPressed();
                            } else {
                                PreferenceUtil.setLoggedIn(true);
                                Intent intent = new Intent(CreateProfileActivity.this, DashboardActivity.class);
                                intent.putExtra(Constants.EXTRA_IS_FROM_PROFILE, true);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                                finish();
                            }


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

    private void loadLocations() {
        fetchCountries();
        fetchStates(selectedCountryId);
        fetchCities(selectedCountryId, selectedStateId);
    }


}
