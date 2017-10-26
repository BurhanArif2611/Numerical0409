/*
 * Copyright © 2017 Thrive fantasy. All rights reserved.
 * Developed by Appster.
 */

package com.revauc.revolutionbuy.ui.sell;


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
import com.revauc.revolutionbuy.databinding.FragmentWishlistBinding;
import com.revauc.revolutionbuy.listeners.OnWishlistClickListener;
import com.revauc.revolutionbuy.network.BaseResponse;
import com.revauc.revolutionbuy.network.RequestController;
import com.revauc.revolutionbuy.network.response.buyer.BuyerProductDto;
import com.revauc.revolutionbuy.network.response.buyer.WishlistResponse;
import com.revauc.revolutionbuy.network.response.seller.SellerOfferDto;
import com.revauc.revolutionbuy.network.response.seller.SellerOffersResponse;
import com.revauc.revolutionbuy.network.retrofit.AuthWebServices;
import com.revauc.revolutionbuy.network.retrofit.DefaultApiObserver;
import com.revauc.revolutionbuy.ui.BaseFragment;
import com.revauc.revolutionbuy.ui.buy.BuyerProductDetailActivity;
import com.revauc.revolutionbuy.ui.buy.adapter.WishListAdapter;
import com.revauc.revolutionbuy.ui.sell.adapter.OffersAdapter;
import com.revauc.revolutionbuy.util.Constants;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class CurrentOffersFragment extends BaseFragment implements OnWishlistClickListener, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "CurrentOffersFragment";
    private FragmentWishlistBinding mBinder;
    private int page=1;
    private int limit=10;
    private OffersAdapter mAdapter;
    private List<SellerOfferDto> mBuyerProducts = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    private int mTotalCount;
    private boolean isFetching = false;


    public CurrentOffersFragment() {
        // Required empty public constructor
    }

    public static CurrentOffersFragment newInstance() {
        return new CurrentOffersFragment();
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
                        if (mTotalCount > page*limit) {
                            page = page+1;
                            fetchCurrentOffers(page,limit,false);
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

        mBinder.textNoData.setText(R.string.havent_made_any_offers_yet);
        mBinder.textNoData.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_no_offer_data,0,0);
        mAdapter = new OffersAdapter(getActivity(),mBuyerProducts,this);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mBinder.recyclerViewWishlist.setLayoutManager(mLayoutManager);
        mBinder.recyclerViewWishlist.setLayoutManager(mLayoutManager);
        mBinder.recyclerViewWishlist.setAdapter(mAdapter);
        mBinder.recyclerViewWishlist.addOnScrollListener(mRecyclerListner);
        mBinder.swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        page = 1;
        fetchCurrentOffers(page,limit,true);
    }

    private void fetchCurrentOffers(final int page, int limit, boolean showLoading) {

        if (isFetching) {
            return;
        }

        if(showLoading)
        {
            mBinder.swipeRefreshLayout.setRefreshing(true);
        }

        isFetching = true;
        AuthWebServices apiService = RequestController.createRetrofitRequest(false);

        apiService.getSellerOffers(page,1,limit).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<SellerOffersResponse>(getActivity()) {

            @Override
            public void onResponse(SellerOffersResponse response) {
                hideProgressBar();
                if (response != null && response.isSuccess()) {
                    if(response.getResult()!=null && response.getResult().getSellerProduct()!=null)
                    {
                        if(page==1)
                        {
                            mBuyerProducts.clear();
                            mTotalCount = response.getResult().getTotalCount();
                        }
                        mBuyerProducts.addAll(response.getResult().getSellerProduct());
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
    public void onWishlistItemClicked(BuyerProductDto buyerProduct) {
        Intent intent = new Intent(getActivity(),BuyerProductDetailActivity.class);
        intent.putExtra(Constants.EXTRA_PRODUCT_DETAIL,buyerProduct);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        if(!isFetching) {
            page = 1;
            mBinder.swipeRefreshLayout.setRefreshing(false);
            fetchCurrentOffers(page,limit,true);
        }
    }
}
