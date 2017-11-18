package com.revauc.revolutionbuy.ui.auth;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.analytics.AnalyticsManager;
import com.revauc.revolutionbuy.databinding.ActivityMobilePinVerificationBinding;
import com.revauc.revolutionbuy.databinding.ActivityMobileVerificationBinding;
import com.revauc.revolutionbuy.network.BaseResponse;
import com.revauc.revolutionbuy.network.RequestController;
import com.revauc.revolutionbuy.network.request.auth.ForgotPasswordRequest;
import com.revauc.revolutionbuy.network.request.auth.MobilePinRequest;
import com.revauc.revolutionbuy.network.request.auth.MobileVerifyRequest;
import com.revauc.revolutionbuy.network.response.LoginResponse;
import com.revauc.revolutionbuy.network.response.UserDto;
import com.revauc.revolutionbuy.network.retrofit.AuthWebServices;
import com.revauc.revolutionbuy.network.retrofit.DefaultApiObserver;
import com.revauc.revolutionbuy.ui.BaseActivity;
import com.revauc.revolutionbuy.ui.auth.ForgotPasswordConfirmActivity;
import com.revauc.revolutionbuy.ui.dashboard.DashboardActivity;
import com.revauc.revolutionbuy.util.Constants;
import com.revauc.revolutionbuy.util.PreferenceUtil;
import com.revauc.revolutionbuy.util.StringUtils;

import java.io.File;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class MobilePinVerificationActivity extends BaseActivity implements View.OnClickListener {


    private ActivityMobilePinVerificationBinding mBinding;
    private String name, mobile;
    private int age;
    private int cityId;
    private String mFilePath;
    private boolean isFromSettings;
    private MixpanelAPI mixpanelAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_mobile_pin_verification);
        mixpanelAPI = MixpanelAPI.getInstance(this, getString(R.string.mixpanel_token));

        if (getIntent() != null) {
            isFromSettings = getIntent().getBooleanExtra(Constants.EXTRA_FROM_SETTINGS, false);
            name = getIntent().getStringExtra(Constants.EXTRA_USER_NAME);
            mobile = getIntent().getStringExtra(Constants.EXTRA_MOBILE);
            age = getIntent().getIntExtra(Constants.EXTRA_AGE, 0);
            cityId = getIntent().getIntExtra(Constants.EXTRA_CITY_ID, 0);
            mFilePath = getIntent().getStringExtra(Constants.EXTRA_PROFILE_IMAGE);
        }

        mBinding.toolbarProfile.tvToolbarGeneralLeft.setText(R.string.back);
        mBinding.toolbarProfile.tvToolbarGeneralLeft.setVisibility(View.VISIBLE);
        mBinding.toolbarProfile.tvToolbarGeneralLeft.setOnClickListener(this);
        mBinding.toolbarProfile.txvToolbarGeneralCenter.setText(R.string.mobile_verification);
        mBinding.textVerifyPin.setOnClickListener(this);
        mBinding.textResend.setOnClickListener(this);

        mBinding.editPin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString().trim())) {
                    mBinding.editPin.setLineColor(ContextCompat.getColor(MobilePinVerificationActivity.this, R.color.color_red_border));
                } else {
                    mBinding.editPin.setLineColor(ContextCompat.getColor(MobilePinVerificationActivity.this, R.color.colorPrimary));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_toolbar_general_left:
                onBackPressed();

                break;
            case R.id.text_verify_pin:
                validateDetails();
                break;

            case R.id.text_resend:
                generatePin(mobile);
                break;
        }

    }

    @Override
    protected void onDestroy() {
        if(mixpanelAPI!=null)
        {
            mixpanelAPI.flush();
        }
        super.onDestroy();
    }

    /**
     * Checks the data for any empty or false values and marks the labels as red in case of any
     * errors in it.
     */
    private void validateDetails() {
        String pin = mBinding.editPin.getText().toString().trim();

        if (StringUtils.isNullOrEmpty(pin)) {
            showSnackBarFromBottom(getString(R.string.text_please_enter, "PIN"), mBinding.mainContainer, true);
            mBinding.editPin.setLineColor(ContextCompat.getColor(MobilePinVerificationActivity.this, R.color.color_red_border));

        } else {
            if (isFromSettings) {
                changeMobileNumber(pin);
            } else {
                addProfileWithPin(pin);
            }

        }
    }

    private void changeMobileNumber(String pin) {
        showProgressBar();
        AuthWebServices apiService = RequestController.createRetrofitRequest(false);

        apiService.verifyMobileChangePin(new MobileVerifyRequest(mobile, pin)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<BaseResponse>(this) {

            @Override
            public void onResponse(BaseResponse response) {
                hideProgressBar();
                if (response.isSuccess()) {
                    UserDto userDto = PreferenceUtil.getUserProfile();
                    userDto.setMobile(mobile);
                    PreferenceUtil.setUserProfile(userDto);
                    onBackPressed();
                    showToast(response.getMessage());
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

    private void generatePin(final String mobile) {
        showProgressBar();
        AuthWebServices apiService = RequestController.createRetrofitRequest(false);

        apiService.generateMobilePin(new MobilePinRequest(mobile)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<BaseResponse>(this) {

            @Override
            public void onResponse(BaseResponse response) {
                hideProgressBar();
                if (response.isSuccess()) {
                   showSnackBarFromBottom(response.getMessage(),mBinding.mainContainer,false);
                }
                else
                {
                    showSnackBarFromBottom(response.getMessage(),mBinding.mainContainer, true);
                }

            }

            @Override
            public void onError(Throwable call, BaseResponse baseResponse) {
                hideProgressBar();
                if (baseResponse != null) {
                    String errorMessage = baseResponse.getMessage();
                    showSnackBarFromBottom(errorMessage,mBinding.mainContainer, true);
                }
            }
        });
    }

    private void addProfileWithPin(String pin) {
        showProgressBar();

        //File creating from selected URL
        File file = null;
        try
        {
            file = new File(mFilePath);
        }
        catch (Exception e)
        {

        }


        //Creating Profile Details
        RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody ageBody = RequestBody.create(MediaType.parse("text/plain"), "" + age);
        RequestBody mobileBody = RequestBody.create(MediaType.parse("text/plain"), mobile);
        RequestBody cityBody = RequestBody.create(MediaType.parse("text/plain"), "" + cityId);
        RequestBody pinBody = RequestBody.create(MediaType.parse("text/plain"), pin);

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("name", nameBody);
        map.put("age", ageBody);
        map.put("cityId", cityBody);
        map.put("mobile", mobileBody);
        map.put("pin", pinBody);
        // create RequestBody instance from file

        AuthWebServices apiService = RequestController.createRetrofitRequest(false);
        Observable<LoginResponse> uploadForm;
        if(file!=null)
        {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            // MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("image", file.getName(), requestFile);
            uploadForm =  apiService.uploadFormData(map, body);
        }
        else
        {
            uploadForm =  apiService.uploadFormData(map);
        }




        uploadForm.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DefaultApiObserver<LoginResponse>(this) {

                    @Override
                    public void onResponse(LoginResponse response) {
                        hideProgressBar();
                        if (response.isSuccess()) {
                            if(response.getResult()!=null)
                            {
                                if(response.getResult().getUser()!=null)
                                {
                                    PreferenceUtil.setUserProfile(response.getResult().getUser());
                                    PreferenceUtil.setLoggedIn(true);
                                    AnalyticsManager.setDistictUserSuperProperties(mixpanelAPI,response.getResult().getUser().getFbId()!=null?Constants.LOGIN_FACEBOOK:Constants.LOGIN_EMAIL,Constants.TYPE_SIGNUP,response.getResult().getUser());
                                    AnalyticsManager.trackMixpanelEvent(mixpanelAPI,getString(R.string.sign_up_event));
                                    Intent intent = new Intent(MobilePinVerificationActivity.this, DashboardActivity.class);
                                    intent.putExtra(Constants.EXTRA_IS_FROM_PROFILE, true);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                                    finish();
                                }
                            }

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

}
