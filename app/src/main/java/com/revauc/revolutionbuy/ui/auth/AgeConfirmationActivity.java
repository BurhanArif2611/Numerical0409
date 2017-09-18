package com.revauc.revolutionbuy.ui.auth;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

import com.revauc.revolutionbuy.BuildConfig;
import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ActivityAgeConfirmationBinding;
import com.revauc.revolutionbuy.databinding.ActivityTermsBinding;
import com.revauc.revolutionbuy.ui.BaseActivity;


/**
 * Screen shows Terms of Service.
 */
public class AgeConfirmationActivity extends BaseActivity implements View.OnClickListener {
    private ActivityAgeConfirmationBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_age_confirmation);
        mBinding.textYes.setOnClickListener(this);
        mBinding.textNo.setOnClickListener(this);
        mBinding.imageBack.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_yes:
                startActivity(new Intent(AgeConfirmationActivity.this,SignUpActivity.class));
                finish();
                break;
            case R.id.text_no:
                Intent intent = new Intent(AgeConfirmationActivity.this, AgeAlertActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                finish();
                break;
            case R.id.image_back:
                onBackPressed();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
