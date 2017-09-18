package com.revauc.revolutionbuy.ui.auth;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ActivityAgeConfirmationBinding;
import com.revauc.revolutionbuy.databinding.ActivityAgeNegationBinding;
import com.revauc.revolutionbuy.ui.BaseActivity;


/**
 * Screen shows Terms of Service.
 */
public class AgeAlertActivity extends BaseActivity implements View.OnClickListener {
    private ActivityAgeNegationBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_age_negation);
        mBinding.imageBack.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
