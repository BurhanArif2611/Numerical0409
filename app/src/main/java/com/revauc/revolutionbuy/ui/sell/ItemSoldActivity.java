package com.revauc.revolutionbuy.ui.sell;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ActivityItemSoldBinding;
import com.revauc.revolutionbuy.databinding.ActivityOfferSentBinding;
import com.revauc.revolutionbuy.ui.BaseActivity;


public class ItemSoldActivity extends BaseActivity implements View.OnClickListener {


    public static final String BROAD_OFFER_SOLD = "com.revauc.revolutionbuy.ui.sell.sold";
    private ActivityItemSoldBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_item_sold);
        mBinding.buttonMaybeLater.setOnClickListener(this);
        mBinding.buttonRateRevBuy.setOnClickListener(this);
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(BROAD_OFFER_SOLD));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_maybe_later:
                finish();
                break;
            case R.id.button_rate_rev_buy:
                finish();
                break;
        }

    }
}

