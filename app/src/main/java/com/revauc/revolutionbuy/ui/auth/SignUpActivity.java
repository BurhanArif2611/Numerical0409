package com.revauc.revolutionbuy.ui.auth;

import android.content.Intent;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ClickableSpan;
import android.view.View;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ActivitySignUpBinding;
import com.revauc.revolutionbuy.network.BaseResponse;
import com.revauc.revolutionbuy.network.RequestController;
import com.revauc.revolutionbuy.network.request.auth.SignUpRequest;
import com.revauc.revolutionbuy.network.response.LoginResponse;
import com.revauc.revolutionbuy.network.retrofit.AuthWebServices;
import com.revauc.revolutionbuy.network.retrofit.DefaultApiObserver;
import com.revauc.revolutionbuy.ui.BaseActivity;
import com.revauc.revolutionbuy.ui.dashboard.DashboardActivity;
import com.revauc.revolutionbuy.util.Constants;
import com.revauc.revolutionbuy.util.Utils;

import java.util.UUID;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class SignUpActivity extends BaseActivity implements View.OnClickListener {


    private ActivitySignUpBinding activitySignUpBinding;
    private boolean isFromSettings;
    private TypedArray imgsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);

        activitySignUpBinding.imageBack.setOnClickListener(this);
        activitySignUpBinding.textSignUp.setOnClickListener(this);

        setSpanString();

        activitySignUpBinding.editEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(s.toString().trim())){
                    activitySignUpBinding.containerEmail.setBackgroundResource(R.drawable.ic_button_red_border);
                }else{
                    activitySignUpBinding.containerEmail.setBackgroundResource(R.drawable.ic_button_blue_border);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        activitySignUpBinding.editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(s.toString().trim())){
                    activitySignUpBinding.containerPassword.setBackgroundResource(R.drawable.ic_button_red_border);
                }else{
                    activitySignUpBinding.containerPassword.setBackgroundResource(R.drawable.ic_button_blue_border);
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
            case R.id.text_sign_up:
                validateDetails();
                break;
        }
    }

    /**
     * Checks the data for any empty or false values and marks the labels as red in case of any
     * errors in it.
     */
    private void validateDetails() {
        String email = activitySignUpBinding.editEmail.getText().toString().trim();
        String password = activitySignUpBinding.editPassword.getText().toString().trim();

        if(!Utils.isEmailValid(email)){
            showSnakBarFromTop(getString(R.string.error_valid_email), true);
            activitySignUpBinding.containerEmail.setBackgroundResource(R.drawable.ic_button_red_border);

        }else if(password.length() < getResources().getInteger(R.integer.password_min_length)){
            showSnakBarFromTop(getString(R.string.team_info), true);
            activitySignUpBinding.containerPassword.setBackgroundResource(R.drawable.ic_button_red_border);

        }else if(password.length() > getResources().getInteger(R.integer.password_max_length)){
            showSnakBarFromTop(getString(R.string.error_pass_max_fail), true);
            activitySignUpBinding.containerPassword.setBackgroundResource(R.drawable.ic_button_red_border);

        }else{
            attemptSignup(email,password);
        }
    }

    /**
     * This methods attempts sign up by calling signup API and handles the response.
     *
     * @param email
     * @param password
     */
    private void attemptSignup(String email,String password) {
        showProgressBar();
        AuthWebServices apiService = RequestController.createRetrofitRequest(true);
        final SignUpRequest request = new SignUpRequest();
        request.setEmail(email);
        request.setPassword(password);
        request.setDeviceToken("234325252dmcmskc");
        request.setDeviceId("234325252242151");
        request.setDeviceType("2");

        apiService.registerUser(request).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<LoginResponse>(this) {

            @Override
            public void onResponse(LoginResponse response) {
                hideProgressBar();
                if (response.isSuccess()) {
                    Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                    finish();
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

    /**
     * Makes a String for Login Spannable
     */
    private void setSpanString() {
        SpannableString spanString = new SpannableString(
                getString(R.string.agree_to_terms_and_privacy));

        ClickableSpan terms = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(new Intent(SignUpActivity.this, TermsConditionsActivity.class));
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                finish();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };

        ClickableSpan privacy = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(new Intent(SignUpActivity.this, PrivacyActivity.class));
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                finish();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };

        int termsStart = getString(R.string.agree_to_terms_and_privacy).indexOf("Terms of Use");
        int termsEnd = getString(R.string.agree_to_terms_and_privacy).indexOf(" and");

        int privacyStart = getString(R.string.agree_to_terms_and_privacy).indexOf("Privacy Policy");
        int privacyEnd = getString(R.string.agree_to_terms_and_privacy).indexOf(".");

        Typeface font = Typeface.createFromAsset(getAssets(), getString(R.string.font_avenir_bold));
        Utils.setSpanFont(spanString, termsStart, termsEnd, font);
        Utils.setSpannClickEvent(spanString, termsStart, termsEnd, terms);
        Utils.setSpannColor(spanString, termsStart, termsEnd, ContextCompat.getColor(this, R.color.textColorDark));

        Utils.setSpanFont(spanString, privacyStart, privacyEnd, font);
        Utils.setSpannClickEvent(spanString, privacyStart, privacyEnd, privacy);
        Utils.setSpannColor(spanString, privacyStart, privacyEnd, ContextCompat.getColor(this, R.color.textColorDark));


        Utils.setSpannCommanProperty(activitySignUpBinding.textTerms, spanString);

    }
}
