/*
 * Copyright © 2017 Thrive fantasy. All rights reserved.
 * Developed by Appster.
 */

package com.revauc.revolutionbuy.ui.buy;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.FragmentPurchasedBinding;
import com.revauc.revolutionbuy.databinding.FragmentWishlistBinding;
import com.revauc.revolutionbuy.listeners.OnPurchasedClickListener;
import com.revauc.revolutionbuy.listeners.OnWishlistClickListener;
import com.revauc.revolutionbuy.network.BaseResponse;
import com.revauc.revolutionbuy.network.RequestController;
import com.revauc.revolutionbuy.network.response.buyer.BuyerProductDto;
import com.revauc.revolutionbuy.network.response.buyer.PurchasedProductDto;
import com.revauc.revolutionbuy.network.response.buyer.PurchasedResponse;
import com.revauc.revolutionbuy.network.response.buyer.WishlistResponse;
import com.revauc.revolutionbuy.network.retrofit.AuthWebServices;
import com.revauc.revolutionbuy.network.retrofit.DefaultApiObserver;
import com.revauc.revolutionbuy.ui.BaseFragment;
import com.revauc.revolutionbuy.ui.buy.adapter.PurchasedAdapter;
import com.revauc.revolutionbuy.ui.buy.adapter.WishListAdapter;
import com.revauc.revolutionbuy.util.Constants;
import com.revauc.revolutionbuy.util.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class PurchasedFragment extends BaseFragment implements OnPurchasedClickListener, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "WishListFragment";
    private static final String PARAM_TITLE = "ParamTitle";
    private FragmentWishlistBinding mBinder;
    private int offset=0;
    private int limit=10;
    private PurchasedAdapter mAdapter;
    private List<PurchasedProductDto> mBuyerProducts = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    private int mTotalCount;
    private boolean isFetching = false;


    public PurchasedFragment() {
        // Required empty public constructor
    }

    public static PurchasedFragment newInstance() {
        return new PurchasedFragment();
    }

    private RecyclerView.OnScrollListener mRecyclerListner = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int total = mLayoutManager.getItemCount();
            int lastVisibleItemCount = mLayoutManager.findLastVisibleItemPosition();

            //to avoid multiple calls to loadMore() method
            //maintain a boolean value (isLoading). if loadMore() task started set to true and completes set to false
            if (!isFetching) {
                if (total > 0)
                    if ((total - 1) == lastVisibleItemCount) {
                        if (mTotalCount > offset) {
                            offset = offset+10;
                            fetchBuyerWishlist(offset,limit,false);
                            mBinder.progressbarLoading.setVisibility(View.VISIBLE);
                        }


                    } else
                        mBinder.progressbarLoading.setVisibility(View.GONE);
            }
        }
    };

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

        mAdapter = new PurchasedAdapter(getActivity(),mBuyerProducts,this);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mBinder.textNoData.setText(R.string.purchased_no_data_text);
        mBinder.recyclerViewWishlist.setLayoutManager(mLayoutManager);
        mBinder.recyclerViewWishlist.setLayoutManager(mLayoutManager);
        mBinder.recyclerViewWishlist.setAdapter(mAdapter);
        mBinder.recyclerViewWishlist.addOnScrollListener(mRecyclerListner);
        mBinder.swipeRefreshLayout.setOnRefreshListener(this);
        if(!PreferenceUtil.isLoggedIn())
        {
            mBinder.swipeRefreshLayout.setEnabled(false);
            mBinder.textNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(PreferenceUtil.isLoggedIn())
        {
            offset=0;
            fetchBuyerWishlist(offset,limit,true);
        }

    }

    private void fetchBuyerWishlist(final int offset, int limit,boolean showLoading) {

        if (isFetching) {
            return;
        }

        if(showLoading)
        {
            mBinder.swipeRefreshLayout.setRefreshing(true);
        }

        isFetching = true;
        AuthWebServices apiService = RequestController.createRetrofitRequest(false);

        apiService.getBuyerPurchasedList(offset, limit).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<PurchasedResponse>(getActivity()) {

            @Override
            public void onResponse(PurchasedResponse response) {
                hideProgressBar();
                if (response != null && response.isSuccess()) {
                    if(response.getResult()!=null && response.getResult().getBuyerProduct()!=null)
                    {
                        if(offset==0)
                        {
                            mBuyerProducts.clear();
                            mTotalCount = response.getResult().getTotalCount();
                        }
                        mBuyerProducts.addAll(response.getResult().getBuyerProduct());
                        mAdapter.notifyDataSetChanged();
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
                doPostLoadingTask();
            }
        });
    }

    private void doPostLoadingTask() {
        mBinder.swipeRefreshLayout.setRefreshing(false);
        mBinder.progressbarLoading.setVisibility(View.GONE);
        isFetching = false;
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
    public void onRefresh() {
        if(!isFetching) {
            offset = 0;
            mBinder.swipeRefreshLayout.setRefreshing(false);
            fetchBuyerWishlist(offset,limit,true);
        }
    }

    @Override
    public void onPurchaseClicked(PurchasedProductDto purchasedProductDto) {
        Intent intent = new Intent(getActivity(),PurchasedItemDetailActivity.class);
        intent.putExtra(Constants.EXTRA_PRODUCT_DETAIL,purchasedProductDto);
        startActivity(intent);
    }
}
