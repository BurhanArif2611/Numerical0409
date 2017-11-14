package com.revauc.revolutionbuy.ui.auth;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ActivityMobileVerificationBinding;
import com.revauc.revolutionbuy.network.BaseResponse;
import com.revauc.revolutionbuy.network.RequestController;
import com.revauc.revolutionbuy.network.request.auth.MobilePinRequest;
import com.revauc.revolutionbuy.network.retrofit.AuthWebServices;
import com.revauc.revolutionbuy.network.retrofit.DefaultApiObserver;
import com.revauc.revolutionbuy.ui.BaseActivity;
import com.revauc.revolutionbuy.util.Constants;
import com.revauc.revolutionbuy.util.PreferenceUtil;
import com.revauc.revolutionbuy.util.StringUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MobileVerificationActivity extends BaseActivity implements View.OnClickListener {


    private ActivityMobileVerificationBinding mBinding;
    private String name;
    private int age;
    private int cityId;
    private String mFilePath;
    private boolean isFromSettings;
    private String mPhoneCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_mobile_verification);

        if(getIntent()!=null)
        {
            isFromSettings = getIntent().getBooleanExtra(Constants.EXTRA_FROM_SETTINGS,false);
            name = getIntent().getStringExtra(Constants.EXTRA_USER_NAME);
            age = getIntent().getIntExtra(Constants.EXTRA_AGE,0);
            cityId = getIntent().getIntExtra(Constants.EXTRA_CITY_ID,0);
            mFilePath = getIntent().getStringExtra(Constants.EXTRA_PROFILE_IMAGE);
            mPhoneCode = getIntent().getStringExtra(Constants.EXTRA_PHONE_CODE);
            if(mPhoneCode!=null)
            {
                mBinding.editMobile.setText(mPhoneCode);
            }
        }

        if(isFromSettings)
        {
            mBinding.editMobile.setText(PreferenceUtil.getUserProfile().getMobile());
        }

        mBinding.toolbarProfile.tvToolbarGeneralLeft.setText(R.string.back);
        mBinding.toolbarProfile.tvToolbarGeneralLeft.setVisibility(View.VISIBLE);
        mBinding.toolbarProfile.tvToolbarGeneralLeft.setOnClickListener(this);
        mBinding.toolbarProfile.txvToolbarGeneralCenter.setText(isFromSettings?R.string.change_mobile_number:R.string.mobile_verification);
        mBinding.toolbarProfile.tvToolbarGeneralLeft.setOnClickListener(this);
        mBinding.textGeneratePin.setOnClickListener(this);

        mBinding.editMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(s.toString().trim())){
                    mBinding.containerNumber.setBackgroundResource(R.drawable.ic_button_red_border);
                }else{
                    mBinding.containerNumber.setBackgroundResource(R.drawable.ic_button_blue_border);
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
        switch (view.getId())
        {
            case R.id.tv_toolbar_general_left:
                onBackPressed();

                break;
            case R.id.text_generate_pin:
                validateDetails();
                break;
        }

    }

    /**
     * Checks the data for any empty or false values and marks the labels as red in case of any
     * errors in it.
     */
    private void validateDetails() {
        String mobile = mBinding.editMobile.getText().toString().trim();

        if(StringUtils.isNullOrEmpty(mobile)){
            showSnackBarFromBottom(getString(R.string.text_please_enter,getString(R.string.mobile_number)),mBinding.mainContainer, true);
            mBinding.containerNumber.setBackgroundResource(R.drawable.ic_button_red_border);

        }else{
            generatePin(mobile);
        }
    }

    private void generatePin(final String mobile) {
        showProgressBar();
        AuthWebServices apiService = RequestController.createRetrofitRequest(false);

        apiService.generateMobilePin(new MobilePinRequest(mobile)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<BaseResponse>(this) {

            @Override
            public void onResponse(BaseResponse response) {
                hideProgressBar();
                if (response.isSuccess()) {
                    Intent intent = new Intent(MobileVerificationActivity.this,MobilePinVerificationActivity.class);
                    intent.putExtra(Constants.EXTRA_USER_NAME,name);
                    intent.putExtra(Constants.EXTRA_MOBILE,mobile);
                    intent.putExtra(Constants.EXTRA_AGE,age);
                    intent.putExtra(Constants.EXTRA_CITY_ID,cityId);
                    intent.putExtra(Constants.EXTRA_PROFILE_IMAGE,mFilePath);
                    intent.putExtra(Constants.EXTRA_FROM_SETTINGS,isFromSettings);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                    if(isFromSettings)
                    {
                        finish();
                    }
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

}
