package com.revauc.revolutionbuy.ui.auth;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ActivityEnterAppBinding;
import com.revauc.revolutionbuy.databinding.ActivityWalkthroughBinding;
import com.revauc.revolutionbuy.ui.BaseActivity;
import com.revauc.revolutionbuy.ui.dashboard.DashboardActivity;
import com.revauc.revolutionbuy.util.Constants;


public class EnterAppActivity extends BaseActivity implements View.OnClickListener{


    private ActivityEnterAppBinding activityEnterAppBinding;
    private boolean isFromSettings;
    private TypedArray imgsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEnterAppBinding = DataBindingUtil.setContentView(this, R.layout.activity_enter_app);
        if (getIntent() != null) {
            isFromSettings = getIntent().getBooleanExtra(Constants.EXTRA_FROM_SETTINGS, false);
        }

        activityEnterAppBinding.textSignUp.setOnClickListener(this);
        activityEnterAppBinding.textLogin.setOnClickListener(this);
        activityEnterAppBinding.textSkip.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.text_sign_up:
                startActivity(new Intent(this, AgeConfirmationActivity.class));
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                break;
            case R.id.text_login:
                startActivity(new Intent(this, SignInActivity.class));
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                break;
            case R.id.text_skip:
                Intent intent = new Intent(EnterAppActivity.this, DashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                finish();
                break;
        }
    }
}
