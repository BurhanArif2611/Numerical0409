package com.revauc.revolutionbuy.ui.buy;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ActivityAddDescriptionBinding;
import com.revauc.revolutionbuy.databinding.ActivityAddPhotosBinding;
import com.revauc.revolutionbuy.ui.BaseActivity;
import com.revauc.revolutionbuy.ui.buy.adapter.ItemListedActivity;
import com.revauc.revolutionbuy.util.StringUtils;


public class AddPhotosActivity extends BaseActivity implements View.OnClickListener {


    private ActivityAddPhotosBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_photos);
        mBinding.toolbarAddPhoto.tvToolbarGeneralLeft.setText(R.string.cancel);
        mBinding.toolbarAddPhoto.tvToolbarGeneralLeft.setVisibility(View.VISIBLE);
        mBinding.toolbarAddPhoto.tvToolbarGeneralLeft.setOnClickListener(this);
        mBinding.toolbarAddPhoto.txvToolbarGeneralCenter.setText(R.string.upload_photos);
        mBinding.toolbarAddPhoto.tvToolbarGeneralRight.setText(R.string.skip_done_text);
        mBinding.toolbarAddPhoto.tvToolbarGeneralRight.setVisibility(View.VISIBLE);
        mBinding.toolbarAddPhoto.tvToolbarGeneralRight.setOnClickListener(this);
        mBinding.layoutBuyerFooter.textStepFour.setEnabled(true);
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
        Intent intent = new Intent(AddPhotosActivity.this, ItemListedActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }
}

