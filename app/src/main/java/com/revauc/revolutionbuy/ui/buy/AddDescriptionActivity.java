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
import com.revauc.revolutionbuy.databinding.ActivityAddTitleBinding;
import com.revauc.revolutionbuy.ui.BaseActivity;
import com.revauc.revolutionbuy.util.StringUtils;


public class AddDescriptionActivity extends BaseActivity implements View.OnClickListener {


    private ActivityAddDescriptionBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_description);
        mBinding.toolbarAddDescription.tvToolbarGeneralLeft.setText(R.string.cancel);
        mBinding.toolbarAddDescription.tvToolbarGeneralLeft.setVisibility(View.VISIBLE);
        mBinding.toolbarAddDescription.tvToolbarGeneralLeft.setOnClickListener(this);
        mBinding.toolbarAddDescription.txvToolbarGeneralCenter.setText(R.string.add_description);
        mBinding.toolbarAddDescription.tvToolbarGeneralRight.setText(R.string.skip_next_text);
        mBinding.toolbarAddDescription.tvToolbarGeneralRight.setVisibility(View.VISIBLE);
        mBinding.toolbarAddDescription.tvToolbarGeneralRight.setOnClickListener(this);
        mBinding.layoutBuyerFooter.textStepThree.setEnabled(true);

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
            showSnackBarFromBottom(getString(R.string.text_please_enter,getString(R.string.description)),mBinding.mainContainer, true);
            mBinding.containerDesc.setBackgroundResource(R.drawable.ic_button_red_border);
        }else{
            Intent intent = new Intent(AddDescriptionActivity.this, AddPhotosActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }
    }
}

