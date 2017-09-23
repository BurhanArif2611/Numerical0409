package com.revauc.revolutionbuy.ui.auth;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ActivityForgotPasswordBinding;
import com.revauc.revolutionbuy.network.BaseResponse;
import com.revauc.revolutionbuy.network.RequestController;
import com.revauc.revolutionbuy.network.request.auth.ForgotPasswordRequest;
import com.revauc.revolutionbuy.network.request.auth.SignUpRequest;
import com.revauc.revolutionbuy.network.response.LoginResponse;
import com.revauc.revolutionbuy.network.retrofit.AuthWebServices;
import com.revauc.revolutionbuy.network.retrofit.DefaultApiObserver;
import com.revauc.revolutionbuy.ui.BaseActivity;
import com.revauc.revolutionbuy.util.Utils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class ForgotPasswordActivity extends BaseActivity implements View.OnClickListener {


    private ActivityForgotPasswordBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password);

        mBinding.imageBack.setOnClickListener(this);
        mBinding.textSubmit.setOnClickListener(this);

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
            case R.id.text_submit:
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

        if(!Utils.isEmailValid(email)){
            showSnackBarFromBottom(getString(R.string.error_valid_email),mBinding.mainContainer, true);
            mBinding.containerEmail.setBackgroundResource(R.drawable.ic_button_red_border);

        }else{
            sendResetPasswordLink(email);
        }
    }

    /**
     * This methods attempts sign up by calling signup API and handles the response.
     *
     * @param email
     */
    private void sendResetPasswordLink(String email) {
        showProgressBar();
        AuthWebServices apiService = RequestController.createRetrofitRequest(true);

        apiService.forgotPassword(new ForgotPasswordRequest(email)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<BaseResponse>(this) {

            @Override
            public void onResponse(BaseResponse response) {
                hideProgressBar();
                if (response.isSuccess()) {
                    Intent intent = new Intent(ForgotPasswordActivity.this, ForgotPasswordConfirmActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                    finish();
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
