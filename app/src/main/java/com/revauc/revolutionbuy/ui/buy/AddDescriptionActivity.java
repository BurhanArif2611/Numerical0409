package com.revauc.revolutionbuy.ui.buy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ActivityAddDescriptionBinding;
import com.revauc.revolutionbuy.databinding.ActivityAddTitleBinding;
import com.revauc.revolutionbuy.network.response.buyer.BuyerProductDto;
import com.revauc.revolutionbuy.ui.BaseActivity;
import com.revauc.revolutionbuy.ui.buy.adapter.ItemListedActivity;
import com.revauc.revolutionbuy.util.Constants;
import com.revauc.revolutionbuy.util.StringUtils;


public class AddDescriptionActivity extends BaseActivity implements View.OnClickListener {


    private ActivityAddDescriptionBinding mBinding;
    private final BroadcastReceiver mReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };
    private String mTitle;
    private String mCategory;
    private BuyerProductDto mProductDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_description);
        mProductDetail = getIntent().getParcelableExtra(Constants.EXTRA_PRODUCT_DETAIL);
        mBinding.toolbarAddDescription.tvToolbarGeneralLeft.setText(R.string.cancel);
        mBinding.toolbarAddDescription.tvToolbarGeneralLeft.setVisibility(View.VISIBLE);
        mBinding.toolbarAddDescription.tvToolbarGeneralLeft.setOnClickListener(this);
        mBinding.toolbarAddDescription.txvToolbarGeneralCenter.setText(R.string.add_description);
        mBinding.toolbarAddDescription.tvToolbarGeneralRight.setText(R.string.skip_next_text);
        mBinding.toolbarAddDescription.tvToolbarGeneralRight.setVisibility(View.VISIBLE);
        mBinding.toolbarAddDescription.tvToolbarGeneralRight.setOnClickListener(this);
        mBinding.layoutBuyerFooter.textStepThree.setEnabled(true);

        if(mProductDetail!=null)
        {
            mBinding.editTitle.setText(mProductDetail.getDescription());
        }

        mBinding.editTitle.addTextChangedListener(new TextWatcher() {
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

        mTitle = getIntent().getStringExtra(Constants.EXTRA_TITLE);
        mCategory = getIntent().getStringExtra(Constants.EXTRA_CATEGORY);

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
        String description = mBinding.editTitle.getText().toString();
        if(StringUtils.isNullOrEmpty(description)){
            showSnackBarFromBottom(getString(R.string.text_please_enter,getString(R.string.description)),mBinding.mainContainer, true);
            mBinding.containerDesc.setBackgroundResource(R.drawable.ic_button_red_border);
        }else{
            Intent intent = new Intent(AddDescriptionActivity.this, AddPhotosActivity.class);
            intent.putExtra(Constants.EXTRA_TITLE,mTitle);
            intent.putExtra(Constants.EXTRA_CATEGORY,mCategory);
            intent.putExtra(Constants.EXTRA_DESCRIPTION,description);
            intent.putExtra(Constants.EXTRA_PRODUCT_DETAIL,mProductDetail);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }
    }
}

