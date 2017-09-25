package com.revauc.revolutionbuy.ui.auth;

import android.content.Intent;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ActivityEnterAppBinding;
import com.revauc.revolutionbuy.eventbusmodel.OnButtonClicked;
import com.revauc.revolutionbuy.ui.BaseActivity;
import com.revauc.revolutionbuy.ui.dashboard.DashboardActivity;
import com.revauc.revolutionbuy.util.Constants;
import com.revauc.revolutionbuy.widget.BottomSheetAlertInverse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


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

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
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
                BottomSheetAlertInverse.getInstance(EnterAppActivity.this,getString(R.string.sure_to_continue_as_guest),getString(R.string.continue_as_guest),getString(R.string.cancel)).show();
                break;
        }
    }

    @Subscribe
    public void onSkip(OnButtonClicked onPositiveClicked)
    {
        Intent intent = new Intent(EnterAppActivity.this, DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        finish();
    }



}
