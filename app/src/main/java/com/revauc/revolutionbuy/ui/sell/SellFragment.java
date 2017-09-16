/*
 * Copyright © 2017 Thrive fantasy. All rights reserved.
 * Developed by Appster.
 */

package com.revauc.revolutionbuy.ui.sell;


import android.content.Intent;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;


import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.FragmentSellBinding;
import com.revauc.revolutionbuy.ui.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;



public class SellFragment extends BaseFragment{
    private static final String TAG = "SellFragment";
    private FragmentSellBinding mBinder;


    public SellFragment() {
        // Required empty public constructor
    }

    public static SellFragment newInstance() {
        return new SellFragment();
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
        mBinder = DataBindingUtil.inflate(inflater, R.layout.fragment_sell, container, false);

        return mBinder.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        setupOptionsGrid();
    }

    private void setupOptionsGrid() {
        mBinder.toolbarSell.txvToolbarGeneralCenter.setText("Sell");
        mBinder.toolbarSell.ivToolBarLeft.setImageResource(R.drawable.ic_search);
        mBinder.toolbarSell.ivToolBarRight.setImageResource(R.drawable.ic_cart);
        String[] titles = getResources().getStringArray(R.array.categories_labels);
        TypedArray imgsArray = getResources().obtainTypedArray(R.array.categories_imgs);
        mBinder.gridOptions.setAdapter(new SellOptionsGridAdapter(getActivity(),titles,imgsArray));
    }

}
