package com.revauc.revolutionbuy.ui.buy.adapter;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ActivityAddPhotosBinding;
import com.revauc.revolutionbuy.databinding.ActivityItemListedBinding;
import com.revauc.revolutionbuy.ui.BaseActivity;


public class ItemListedActivity extends BaseActivity implements View.OnClickListener {


    public static final String BROAD_BUY_LISTED_COMPLETE = "com.revauc.revolutionbuy.ui.buy.complete";
    private ActivityItemListedBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_item_listed);
        mBinding.buttonDone.setOnClickListener(this);
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(BROAD_BUY_LISTED_COMPLETE));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_done:
                finish();
                break;
        }

    }
}

