package com.revauc.revolutionbuy.ui.auth;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ActivityContactAdminBinding;
import com.revauc.revolutionbuy.databinding.ActivityMobileVerificationBinding;
import com.revauc.revolutionbuy.network.BaseResponse;
import com.revauc.revolutionbuy.network.RequestController;
import com.revauc.revolutionbuy.network.request.auth.MobilePinRequest;
import com.revauc.revolutionbuy.network.retrofit.AuthWebServices;
import com.revauc.revolutionbuy.network.retrofit.DefaultApiObserver;
import com.revauc.revolutionbuy.ui.BaseActivity;
import com.revauc.revolutionbuy.ui.auth.MobilePinVerificationActivity;
import com.revauc.revolutionbuy.util.Constants;
import com.revauc.revolutionbuy.util.StringUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class ContactAdminActivity extends BaseActivity implements View.OnClickListener {


    private ActivityContactAdminBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_contact_admin);

        mBinding.toolbarAdmin.ivToolBarLeft.setImageResource(R.drawable.ic_back_blue);
        mBinding.toolbarAdmin.ivToolBarLeft.setVisibility(View.VISIBLE);
        mBinding.toolbarAdmin.ivToolBarLeft.setOnClickListener(this);
        mBinding.toolbarAdmin.txvToolbarGeneralCenter.setText(R.string.contact_admin);
        mBinding.textEmail.setOnClickListener(this);
        mBinding.textMobile.setOnClickListener(this);
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
            case R.id.iv_tool_bar_left:
                onBackPressed();

                break;
            case R.id.text_email:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto::" + getString(R.string.admin_email_id)));
                try {
                    startActivity(emailIntent);
                } catch (ActivityNotFoundException e) {
                    //TODO: Handle case where no email app is available
                }
                break;
            case R.id.text_mobile:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + getString(R.string.admin_mobile_number)));
                startActivity(intent);
                break;
        }

    }



}
