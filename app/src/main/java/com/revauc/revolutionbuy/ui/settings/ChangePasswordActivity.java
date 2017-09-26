package com.revauc.revolutionbuy.ui.settings;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ActivityChangePasswordBinding;
import com.revauc.revolutionbuy.databinding.ActivityCreateProfileBinding;
import com.revauc.revolutionbuy.network.BaseResponse;
import com.revauc.revolutionbuy.network.RequestController;
import com.revauc.revolutionbuy.network.request.auth.ChangePasswordRequest;
import com.revauc.revolutionbuy.network.request.auth.SignUpRequest;
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
import com.revauc.revolutionbuy.ui.auth.CreateProfileActivity;
import com.revauc.revolutionbuy.ui.auth.SignInActivity;
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
public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {
    private ActivityChangePasswordBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_change_password);


        mBinding.toolbarProfile.txvToolbarGeneralCenter.setText(R.string.nav_item_change_password);
        mBinding.toolbarProfile.tvToolbarGeneralLeft.setText(R.string.cancel);
        mBinding.toolbarProfile.tvToolbarGeneralLeft.setVisibility(View.VISIBLE);
        mBinding.toolbarProfile.tvToolbarGeneralLeft.setOnClickListener(this);
        mBinding.toolbarProfile.tvToolbarGeneralRight.setText(R.string.save);
        mBinding.toolbarProfile.tvToolbarGeneralRight.setVisibility(View.VISIBLE);
        mBinding.toolbarProfile.tvToolbarGeneralRight.setOnClickListener(this);


        mBinding.editCurrentPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString().trim())) {
                    mBinding.containerCurrentPassword.setBackgroundResource(R.drawable.ic_button_red_border);
                } else {
                    mBinding.containerCurrentPassword.setBackgroundResource(R.drawable.ic_button_blue_border);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mBinding.editNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString().trim())) {
                    mBinding.containerNewPassword.setBackgroundResource(R.drawable.ic_button_red_border);
                } else {
                    mBinding.containerNewPassword.setBackgroundResource(R.drawable.ic_button_blue_border);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mBinding.editConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString().trim())) {
                    mBinding.containerConfirmPassword.setBackgroundResource(R.drawable.ic_button_red_border);
                } else {
                    mBinding.containerConfirmPassword.setBackgroundResource(R.drawable.ic_button_blue_border);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
            default:
                break;
        }
    }

    private void validateDetailsEntered() {
        String currentPassword = mBinding.editCurrentPassword.getText().toString();
        String newPassword = mBinding.editNewPassword.getText().toString();
        String confirmPassword = mBinding.editConfirmPassword.getText().toString();
        if (StringUtils.isNullOrEmpty(currentPassword)) {
            showSnackBarFromBottom(getString(R.string.text_please_enter, getString(R.string.current_password)), mBinding.mainContainer, true);
            mBinding.containerCurrentPassword.setBackgroundResource(R.drawable.ic_button_red_border);
        } else if (currentPassword.length() < getResources().getInteger(R.integer.password_min_length)) {
            showSnackBarFromBottom(getString(R.string.error_pass_min_fail), mBinding.mainContainer, true);
            mBinding.containerCurrentPassword.setBackgroundResource(R.drawable.ic_button_red_border);

        } else if (currentPassword.length() > getResources().getInteger(R.integer.password_max_length)) {
            showSnackBarFromBottom(getString(R.string.error_pass_max_fail), mBinding.mainContainer, true);
            mBinding.containerCurrentPassword.setBackgroundResource(R.drawable.ic_button_red_border);

        } else if (StringUtils.isNullOrEmpty(newPassword)) {
            showSnackBarFromBottom(getString(R.string.text_please_enter, getString(R.string.new_password)), mBinding.mainContainer, true);
            mBinding.containerNewPassword.setBackgroundResource(R.drawable.ic_button_red_border);
        } else if (newPassword.length() < getResources().getInteger(R.integer.password_min_length)) {
            showSnackBarFromBottom(getString(R.string.error_pass_min_fail), mBinding.mainContainer, true);
            mBinding.containerNewPassword.setBackgroundResource(R.drawable.ic_button_red_border);

        } else if (newPassword.length() > getResources().getInteger(R.integer.password_max_length)) {
            showSnackBarFromBottom(getString(R.string.error_pass_max_fail), mBinding.mainContainer, true);
            mBinding.containerNewPassword.setBackgroundResource(R.drawable.ic_button_red_border);

        } else if (StringUtils.isNullOrEmpty(confirmPassword)) {
            showSnackBarFromBottom(getString(R.string.text_please_enter, getString(R.string.confirm_password)), mBinding.mainContainer, true);
            mBinding.containerConfirmPassword.setBackgroundResource(R.drawable.ic_button_red_border);
        } else if (confirmPassword.length() < getResources().getInteger(R.integer.password_min_length)) {
            showSnackBarFromBottom(getString(R.string.error_pass_min_fail), mBinding.mainContainer, true);
            mBinding.containerConfirmPassword.setBackgroundResource(R.drawable.ic_button_red_border);

        } else if (confirmPassword.length() > getResources().getInteger(R.integer.password_max_length)) {
            showSnackBarFromBottom(getString(R.string.error_pass_max_fail), mBinding.mainContainer, true);
            mBinding.containerConfirmPassword.setBackgroundResource(R.drawable.ic_button_red_border);

        } else if (!confirmPassword.equals(newPassword)) {
            showSnackBarFromBottom(getString(R.string.password_should_match), mBinding.mainContainer, true);
            mBinding.containerConfirmPassword.setBackgroundResource(R.drawable.ic_button_red_border);
        } else {
            changePassword(currentPassword, newPassword, confirmPassword);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    private void changePassword(String oldPassword, String newPassword, String confirmPassword) {
        showProgressBar();
        AuthWebServices apiService = RequestController.createRetrofitRequest(true);
        apiService.changePassword(new ChangePasswordRequest(oldPassword,newPassword,confirmPassword)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<BaseResponse>(this) {

            @Override
            public void onResponse(BaseResponse response) {
                hideProgressBar();
                if (response.isSuccess()) {
                    showToast(response.getMessage());
                    onBackPressed();
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
