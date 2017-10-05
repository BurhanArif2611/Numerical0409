package com.revauc.revolutionbuy.ui.buy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ActivityAddTitleBinding;
import com.revauc.revolutionbuy.databinding.ActivitySelectCategoriesBinding;
import com.revauc.revolutionbuy.listeners.OnCategorySelectListener;
import com.revauc.revolutionbuy.network.BaseResponse;
import com.revauc.revolutionbuy.network.RequestController;
import com.revauc.revolutionbuy.network.response.buyer.CategoriesResponse;
import com.revauc.revolutionbuy.network.response.buyer.CategoryDto;
import com.revauc.revolutionbuy.network.retrofit.AuthWebServices;
import com.revauc.revolutionbuy.network.retrofit.DefaultApiObserver;
import com.revauc.revolutionbuy.ui.BaseActivity;
import com.revauc.revolutionbuy.ui.auth.CreateProfileActivity;
import com.revauc.revolutionbuy.ui.buy.adapter.ItemListedActivity;
import com.revauc.revolutionbuy.util.Constants;
import com.revauc.revolutionbuy.util.StringUtils;
import com.revauc.revolutionbuy.util.Utils;
import com.revauc.revolutionbuy.widget.SingleActionBottomSheetAlert;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;



public class AddTitleActivity extends BaseActivity implements View.OnClickListener {


    private ActivityAddTitleBinding mBinding;
    private final BroadcastReceiver mReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_title);
        mBinding.toolbarAddTitle.tvToolbarGeneralLeft.setText(R.string.cancel);
        mBinding.toolbarAddTitle.tvToolbarGeneralLeft.setVisibility(View.VISIBLE);
        mBinding.toolbarAddTitle.tvToolbarGeneralLeft.setOnClickListener(this);
        mBinding.toolbarAddTitle.txvToolbarGeneralCenter.setText(R.string.add_title);
        mBinding.toolbarAddTitle.tvToolbarGeneralRight.setText(R.string.next);
        mBinding.toolbarAddTitle.tvToolbarGeneralRight.setVisibility(View.VISIBLE);
        mBinding.toolbarAddTitle.tvToolbarGeneralRight.setOnClickListener(this);
        mBinding.layoutBuyerFooter.textStepTwo.setEnabled(true);

        mBinding.editTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(s.toString().trim())){
                    mBinding.containerNumber.setBackgroundResource(R.drawable.ic_button_red_border);
                }else{
                    mBinding.containerNumber.setBackgroundResource(R.drawable.et_edittext_border);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        LocalBroadcastManager.getInstance(this).registerReceiver(mReciever, new IntentFilter(ItemListedActivity.BROAD_BUY_LISTED_COMPLETE));

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
            case R.id.tv_toolbar_general_right:
                validateDetails();
                break;
        }

    }

    private void validateDetails() {
        String title = mBinding.editTitle.getText().toString();
        if(StringUtils.isNullOrEmpty(title)){
            showSnackBarFromBottom(getString(R.string.text_please_enter,getString(R.string.title)),mBinding.mainContainer, true);
            mBinding.containerNumber.setBackgroundResource(R.drawable.ic_button_red_border);

        }else{
            startActivity(new Intent(AddTitleActivity.this,AddDescriptionActivity.class));
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }
    }
}

