package com.revauc.revolutionbuy.ui.sell;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ActivityAddDescriptionBinding;
import com.revauc.revolutionbuy.databinding.ActivityReportItemBinding;
import com.revauc.revolutionbuy.network.BaseResponse;
import com.revauc.revolutionbuy.network.RequestController;
import com.revauc.revolutionbuy.network.request.auth.ForgotPasswordRequest;
import com.revauc.revolutionbuy.network.request.auth.ReportItemRequest;
import com.revauc.revolutionbuy.network.request.auth.SellerReportItemRequest;
import com.revauc.revolutionbuy.network.retrofit.AuthWebServices;
import com.revauc.revolutionbuy.network.retrofit.DefaultApiObserver;
import com.revauc.revolutionbuy.ui.BaseActivity;
import com.revauc.revolutionbuy.ui.auth.ForgotPasswordActivity;
import com.revauc.revolutionbuy.ui.auth.ForgotPasswordConfirmActivity;
import com.revauc.revolutionbuy.ui.buy.AddPhotosActivity;
import com.revauc.revolutionbuy.ui.buy.adapter.ItemListedActivity;
import com.revauc.revolutionbuy.util.Constants;
import com.revauc.revolutionbuy.util.StringUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class ReportItemActivity extends BaseActivity implements View.OnClickListener {


    private ActivityReportItemBinding mBinding;
    private final BroadcastReceiver mReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };
    private int mProductId;
    private boolean isBuyer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_report_item);
        mBinding.toolbarAddDescription.tvToolbarGeneralLeft.setText(R.string.cancel);
        mBinding.toolbarAddDescription.tvToolbarGeneralLeft.setVisibility(View.VISIBLE);
        mBinding.toolbarAddDescription.tvToolbarGeneralLeft.setOnClickListener(this);
        mBinding.toolbarAddDescription.txvToolbarGeneralCenter.setText(R.string.report_item);
        mBinding.textSubmit.setOnClickListener(this);

        mBinding.editDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(s.toString().trim())){
                    mBinding.containerDesc.setBackgroundResource(R.drawable.ic_button_red_border);
                }else{
                    mBinding.containerDesc.setBackgroundResource(R.drawable.et_edittext_border);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(mReciever, new IntentFilter(ItemListedActivity.BROAD_BUY_LISTED_COMPLETE));

        mProductId = getIntent().getIntExtra(Constants.EXTRA_PRODUCT_ID,0);
        isBuyer = getIntent().getBooleanExtra(Constants.EXTRA_IS_BUYER,false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReciever);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_toolbar_general_left:
                onBackPressed();

                break;
            case R.id.text_submit:
                validateDetails();
                break;
        }

    }

    private void validateDetails() {
        String description = mBinding.editDesc.getText().toString();
        if(StringUtils.isNullOrEmpty(description)){
            showSnackBarFromBottom(getString(R.string.text_please_enter,getString(R.string.reason_for_reporting)),mBinding.mainContainer, true);
            mBinding.containerDesc.setBackgroundResource(R.drawable.ic_button_red_border);
        }else{
            if(isBuyer)
            {
                reportThisSellerItem(description);
            }
            else
            {
                reportThisItem(description);
            }

        }
    }

    private void reportThisItem(String description) {
        showProgressBar(getString(R.string.reporting_item));
        AuthWebServices apiService = RequestController.createRetrofitRequest(false);

        apiService.reportBuyerItem(new ReportItemRequest(description,mProductId)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<BaseResponse>(this) {

            @Override
            public void onResponse(BaseResponse response) {
                hideProgressBar();
                if (response.isSuccess()) {
                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_OK, returnIntent);
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

    private void reportThisSellerItem(String description) {
        showProgressBar(getString(R.string.reporting_item));
        AuthWebServices apiService = RequestController.createRetrofitRequest(false);

        apiService.reportSellerItem(new SellerReportItemRequest(description,mProductId)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<BaseResponse>(this) {

            @Override
            public void onResponse(BaseResponse response) {
                hideProgressBar();
                if (response.isSuccess()) {
                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_OK, returnIntent);
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

