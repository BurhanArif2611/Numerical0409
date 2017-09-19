package com.revauc.revolutionbuy.ui.auth;

import android.content.Intent;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ActivitySignInBinding;
import com.revauc.revolutionbuy.network.BaseResponse;
import com.revauc.revolutionbuy.network.RequestController;
import com.revauc.revolutionbuy.network.request.auth.SignUpRequest;
import com.revauc.revolutionbuy.network.response.LoginResponse;
import com.revauc.revolutionbuy.network.retrofit.AuthWebServices;
import com.revauc.revolutionbuy.network.retrofit.DefaultApiObserver;
import com.revauc.revolutionbuy.ui.BaseActivity;
import com.revauc.revolutionbuy.ui.dashboard.DashboardActivity;
import com.revauc.revolutionbuy.util.Utils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class SignInActivity extends BaseActivity implements View.OnClickListener {


    private ActivitySignInBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);

        mBinding.imageBack.setOnClickListener(this);
        mBinding.textSignIn.setOnClickListener(this);

        mBinding.editEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(s.toString().trim())){
                    mBinding.containerEmail.setBackgroundResource(R.drawable.ic_button_red_border);
                }else{
                    mBinding.containerEmail.setBackgroundResource(R.drawable.ic_button_blue_border);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mBinding.editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(s.toString().trim())){
                    mBinding.containerPassword.setBackgroundResource(R.drawable.ic_button_red_border);
                }else{
                    mBinding.containerPassword.setBackgroundResource(R.drawable.ic_button_blue_border);
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
            case R.id.image_back:
                onBackPressed();

                break;
            case R.id.text_sign_in:
                validateDetails();
                break;
        }

    }

    /**
     * Checks the data for any empty or false values and marks the labels as red in case of any
     * errors in it.
     */
    private void validateDetails() {
        String email = mBinding.editEmail.getText().toString().trim();
        String password = mBinding.editPassword.getText().toString().trim();

        if(!Utils.isEmailValid(email)){
            showSnakBarFromTop(getString(R.string.error_valid_email), true);
            mBinding.containerEmail.setBackgroundResource(R.drawable.ic_button_red_border);

        }else if(password.length() < getResources().getInteger(R.integer.password_min_length)){
            showSnakBarFromTop(getString(R.string.team_info), true);
            mBinding.containerPassword.setBackgroundResource(R.drawable.ic_button_red_border);

        }else if(password.length() > getResources().getInteger(R.integer.password_max_length)){
            showSnakBarFromTop(getString(R.string.error_pass_max_fail), true);
            mBinding.containerPassword.setBackgroundResource(R.drawable.ic_button_red_border);

        }else{
            attemptSignIn(email,password);
        }
    }

    /**
     * This methods attempts sign up by calling signup API and handles the response.
     *
     * @param email
     * @param password
     */
    private void attemptSignIn(String email,String password) {
        showProgressBar();
        AuthWebServices apiService = RequestController.createRetrofitRequest(true);
        final SignUpRequest request = new SignUpRequest();
        request.setEmail(email);
        request.setPassword(password);
        request.setDeviceToken("234325252dmcmskc");
        request.setDeviceId("234325252242151");
        request.setDeviceType("2");

        apiService.loginUser(request).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<LoginResponse>(this) {

            @Override
            public void onResponse(LoginResponse response) {
                hideProgressBar();
                if (response.isSuccess()) {
//                    Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
//                    startActivity(intent);
//                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
//                    finish();
                    showToast(response.getMessage());
                }
                else
                {
                    showSnakBarFromTop(response.getMessage(), true);
                }

            }

            @Override
            public void onError(Throwable call, BaseResponse baseResponse) {
                hideProgressBar();
                if (baseResponse != null) {
                    String errorMessage = baseResponse.getMessage();
                    showSnakBarFromTop(errorMessage, true);
                }
            }
        });
    }

}