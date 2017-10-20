/*
 * Copyright © 2017 Thrive fantasy. All rights reserved.
 * Developed by Appster.
 */

package com.revauc.revolutionbuy.ui.buy;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.FragmentWishlistBinding;
import com.revauc.revolutionbuy.listeners.OnWishlistClickListener;
import com.revauc.revolutionbuy.network.BaseResponse;
import com.revauc.revolutionbuy.network.RequestController;
import com.revauc.revolutionbuy.network.response.buyer.BuyerProductDto;
import com.revauc.revolutionbuy.network.response.buyer.WishlistResponse;
import com.revauc.revolutionbuy.network.retrofit.AuthWebServices;
import com.revauc.revolutionbuy.network.retrofit.DefaultApiObserver;
import com.revauc.revolutionbuy.ui.BaseFragment;
import com.revauc.revolutionbuy.ui.buy.adapter.WishListAdapter;
import com.revauc.revolutionbuy.util.Constants;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class WishListFragment extends BaseFragment implements OnWishlistClickListener{
    private static final String TAG = "WishListFragment";
    private static final String PARAM_TITLE = "ParamTitle";
    private FragmentWishlistBinding mBinder;
    private int offset=0;
    private int limit=50;
    private WishListAdapter mAdapter;
    private List<BuyerProductDto> mBuyerProducts = new ArrayList<>();


    public WishListFragment() {
        // Required empty public constructor
    }

    public static WishListFragment newInstance() {
        return new WishListFragment();
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
        mBinder = DataBindingUtil.inflate(inflater, R.layout.fragment_wishlist, container, false);
        return mBinder.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new WishListAdapter(getActivity(),mBuyerProducts,this);
        RecyclerView.LayoutManager lay = new LinearLayoutManager(getActivity());
        mBinder.recyclerViewWishlist.setLayoutManager(lay);
        mBinder.recyclerViewWishlist.setAdapter(mAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        fetchBuyerWishlist(offset,limit);
    }

    private void fetchBuyerWishlist(final int offset, int limit) {
        showProgressBar();
        AuthWebServices apiService = RequestController.createRetrofitRequest(false);

        apiService.getBuyerWishlist(offset, limit).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<WishlistResponse>(getActivity()) {

            @Override
            public void onResponse(WishlistResponse response) {
                hideProgressBar();
                if (response != null && response.isSuccess()) {
                    if(response.getResult()!=null && response.getResult().getBuyerProduct()!=null)
                    {
                        if(offset==0)
                        {
                            mBuyerProducts.clear();
                            mBuyerProducts.addAll(response.getResult().getBuyerProduct());
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    showToast(response.getMessage());
//                    showSnackBarFromBottom(response.getMessage(), mBinding.mainContainer, true);
                }
                    doPostLoadingTask();
            }

            @Override
            public void onError(Throwable call, BaseResponse baseResponse) {
                hideProgressBar();
                if (baseResponse != null) {
                    String errorMessage = baseResponse.getMessage();
                    showToast(errorMessage);
//                    Utils.showSnackbar(errorMessage, mBinder.mainContainer, true);
                }
            }
        });
    }

    private void doPostLoadingTask() {
        if(mBuyerProducts!=null && !mBuyerProducts.isEmpty())
        {
         mBinder.textNoData.setVisibility(View.GONE);

        }
        else
        {
            mBinder.textNoData.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onWishlistItemClicked(BuyerProductDto buyerProduct) {
        Intent intent = new Intent(getActivity(),BuyerProductDetailActivity.class);
        intent.putExtra(Constants.EXTRA_PRODUCT_DETAIL,buyerProduct);
        startActivity(intent);
    }
}
