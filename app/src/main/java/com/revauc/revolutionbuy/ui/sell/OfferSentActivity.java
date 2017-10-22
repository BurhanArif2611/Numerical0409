package com.revauc.revolutionbuy.ui.sell;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ActivityItemListedBinding;
import com.revauc.revolutionbuy.databinding.ActivityOfferSentBinding;
import com.revauc.revolutionbuy.ui.BaseActivity;


public class OfferSentActivity extends BaseActivity implements View.OnClickListener {


    public static final String BROAD_OFFER_SENT_COMPLETE = "com.revauc.revolutionbuy.ui.sell.complete";
    private ActivityOfferSentBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_offer_sent);
        mBinding.buttonBackToCategories.setOnClickListener(this);
        mBinding.buttonViewItems.setOnClickListener(this);
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(BROAD_OFFER_SENT_COMPLETE));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_view_items:
                finish();
                break;
            case R.id.button_back_to_categories:
                finish();
                break;
        }

    }
}

