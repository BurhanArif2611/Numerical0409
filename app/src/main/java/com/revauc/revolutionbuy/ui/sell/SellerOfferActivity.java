/*
 * Copyright © 2017 Thrive fantasy. All rights reserved.
 * Developed by Appster.
 */

package com.revauc.revolutionbuy.ui.sell;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ActivitySellerOwnOfferBinding;
import com.revauc.revolutionbuy.ui.BaseActivity;
import com.revauc.revolutionbuy.widget.typeface.CustomTextView;


public class SellerOfferActivity extends BaseActivity implements View.OnClickListener {
    private ActivitySellerOwnOfferBinding mBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = DataBindingUtil.setContentView(this, R.layout.activity_seller_own_offer);
        mBinder.toolbarSellerOffer.txvToolbarGeneralCenter.setText(R.string.items_you_re_selling);
        mBinder.toolbarSellerOffer.ivToolBarLeft.setImageResource(R.drawable.ic_back_blue);
        mBinder.toolbarSellerOffer.ivToolBarLeft.setOnClickListener(this);
        YourOfferSectionAdapter notificationsSectionAdapter = new YourOfferSectionAdapter(getSupportFragmentManager(),this);
        mBinder.viewpagerSellerOffer.setAdapter(notificationsSectionAdapter);
        mBinder.toolbarSellerOffer.buyTabs.setupWithViewPager(mBinder.viewpagerSellerOffer);
        for (int i = 0; i < mBinder.toolbarSellerOffer.buyTabs.getTabCount(); i++) {
            CustomTextView tv=(CustomTextView) LayoutInflater.from(this).inflate(R.layout.layout_tab_item,null);
            mBinder.toolbarSellerOffer.buyTabs.getTabAt(i).setCustomView(tv);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.iv_tool_bar_left:
                onBackPressed();
                break;
        }
    }
}
