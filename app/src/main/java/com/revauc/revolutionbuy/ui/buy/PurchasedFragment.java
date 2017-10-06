/*
 * Copyright © 2017 Thrive fantasy. All rights reserved.
 * Developed by Appster.
 */

package com.revauc.revolutionbuy.ui.buy;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.FragmentPurchasedBinding;
import com.revauc.revolutionbuy.ui.BaseFragment;
import com.revauc.revolutionbuy.ui.buy.adapter.PurchasedAdapter;
import com.revauc.revolutionbuy.ui.buy.adapter.WishListAdapter;


public class PurchasedFragment extends BaseFragment{
    private static final String TAG = "PurchasedFragment";
    private FragmentPurchasedBinding mBinder;
    private PurchasedAdapter mAdapter;


    public PurchasedFragment() {
        // Required empty public constructor
    }

    public static PurchasedFragment newInstance() {
        return new PurchasedFragment();
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
        mBinder = DataBindingUtil.inflate(inflater, R.layout.fragment_purchased, container, false);
        return mBinder.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new PurchasedAdapter(getActivity());
        RecyclerView.LayoutManager lay = new LinearLayoutManager(getActivity());
        mBinder.recyclerViewPurchased.setLayoutManager(lay);
        mBinder.recyclerViewPurchased.setAdapter(mAdapter);
    }



}
