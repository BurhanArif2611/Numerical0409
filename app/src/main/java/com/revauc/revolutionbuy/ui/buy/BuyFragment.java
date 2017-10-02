/*
 * Copyright © 2017 Thrive fantasy. All rights reserved.
 * Developed by Appster.
 */

package com.revauc.revolutionbuy.ui.buy;


import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.FragmentBuyBinding;
import com.revauc.revolutionbuy.databinding.FragmentSellBinding;
import com.revauc.revolutionbuy.ui.BaseFragment;
import com.revauc.revolutionbuy.ui.sell.SellOptionsGridAdapter;
import com.revauc.revolutionbuy.widget.typeface.CustomTextView;


public class BuyFragment extends BaseFragment implements TabLayout.OnTabSelectedListener {
    private static final String TAG = "SellFragment";
    private FragmentBuyBinding mBinder;


    public BuyFragment() {
        // Required empty public constructor
    }

    public static BuyFragment newInstance() {
        return new BuyFragment();
    }

    @Override
    public String getFragmentName() {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinder = DataBindingUtil.inflate(inflater, R.layout.fragment_buy, container, false);

        return mBinder.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBinder.toolbarBuy.txvToolbarGeneralCenter.setText(R.string.buy);
        BuySectionAdapter notificationsSectionAdapter = new BuySectionAdapter(getActivity().getSupportFragmentManager(),getActivity());
        mBinder.viewpagerBuy.setAdapter(notificationsSectionAdapter);
        mBinder.toolbarBuy.buyTabs.setupWithViewPager(mBinder.viewpagerBuy);
        for (int i = 0; i < mBinder.toolbarBuy.buyTabs.getTabCount(); i++) {
            CustomTextView tv=(CustomTextView) LayoutInflater.from(getActivity()).inflate(R.layout.layout_tab_item,null);
            mBinder.toolbarBuy.buyTabs.getTabAt(i).setCustomView(tv);
        }
        mBinder.toolbarBuy.buyTabs.addOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if(tab.getPosition()==0)
        {
            mBinder.floatingActionButton.setVisibility(View.VISIBLE);
        }
        else
        {
            mBinder.floatingActionButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
