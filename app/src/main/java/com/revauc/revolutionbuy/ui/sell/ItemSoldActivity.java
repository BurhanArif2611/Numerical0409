package com.revauc.revolutionbuy.ui.sell;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
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
                Uri uri = Uri.parse("market://details?id=" + "com.revauc.revolutionbuy.dev");
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + "com.revauc.revolutionbuy.dev")));
                }
                break;
        }

    }
}

