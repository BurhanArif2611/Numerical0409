/*
 * Copyright © 2017 Thrive fantasy. All rights reserved.
 * Developed by Appster.
 */

package com.revauc.revolutionbuy.ui.notification;


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
import com.revauc.revolutionbuy.databinding.FragmentNotificationsBinding;
import com.revauc.revolutionbuy.databinding.FragmentWishlistBinding;
import com.revauc.revolutionbuy.listeners.OnNotificationClickListener;
import com.revauc.revolutionbuy.listeners.OnSellerOfferClickListener;
import com.revauc.revolutionbuy.network.BaseResponse;
import com.revauc.revolutionbuy.network.RequestController;
import com.revauc.revolutionbuy.network.response.buyer.PurchasedProductDto;
import com.revauc.revolutionbuy.network.response.buyer.PurchasedResponse;
import com.revauc.revolutionbuy.network.response.profile.NotificationDto;
import com.revauc.revolutionbuy.network.response.profile.NotificationResponse;
import com.revauc.revolutionbuy.network.response.seller.SellerOfferDto;
import com.revauc.revolutionbuy.network.response.seller.SellerOffersResponse;
import com.revauc.revolutionbuy.network.retrofit.AuthWebServices;
import com.revauc.revolutionbuy.network.retrofit.DefaultApiObserver;
import com.revauc.revolutionbuy.ui.BaseFragment;
import com.revauc.revolutionbuy.ui.buy.PurchasedItemDetailActivity;
import com.revauc.revolutionbuy.ui.buy.SellerOffersActivity;
import com.revauc.revolutionbuy.ui.sell.SellerOwnOfferDetailActivity;
import com.revauc.revolutionbuy.ui.sell.adapter.OffersAdapter;
import com.revauc.revolutionbuy.util.Constants;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class NotificationsFragment extends BaseFragment implements OnNotificationClickListener, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "NotificationsFragment";
    private FragmentNotificationsBinding mBinder;
    private int page=1;
    private int limit=10;
    private NotificationAdapter mAdapter;
    private List<NotificationDto> mNotifications = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    private int mTotalCount;
    private boolean isFetching = false;


    public NotificationsFragment() {
        // Required empty public constructor
    }

    public static NotificationsFragment newInstance() {
        return new NotificationsFragment();
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
                            fetchUserNotifications(page,limit,false);
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
        mBinder = DataBindingUtil.inflate(inflater, R.layout.fragment_notifications, container, false);
        return mBinder.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBinder.toolbarNotifications.txvToolbarGeneralCenter.setText(R.string.notifications);

        mAdapter = new NotificationAdapter(getActivity(), mNotifications,this);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mBinder.recyclerView.setLayoutManager(mLayoutManager);
        mBinder.recyclerView.setLayoutManager(mLayoutManager);
        mBinder.recyclerView.setAdapter(mAdapter);
        mBinder.recyclerView.addOnScrollListener(mRecyclerListner);
        mBinder.swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        page = 1;
        fetchUserNotifications(page,limit,true);
    }

    private void fetchUserNotifications(final int page, int limit, boolean showLoading) {

        if (isFetching) {
            return;
        }

        if(showLoading)
        {
            mBinder.swipeRefreshLayout.setRefreshing(true);
        }

        isFetching = true;
        AuthWebServices apiService = RequestController.createRetrofitRequest(false);

        apiService.getUserNotifications(page,limit).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<NotificationResponse>(getActivity()) {

            @Override
            public void onResponse(NotificationResponse response) {
                hideProgressBar();
                if (response != null && response.isSuccess()) {
                    if(response.getResult()!=null && response.getResult().getNotification()!=null)
                    {
                        if(page==1)
                        {
                            mNotifications.clear();
                            mTotalCount = response.getResult().getTotalCount();
                        }
                        mNotifications.addAll(response.getResult().getNotification());
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
        if(mNotifications !=null && !mNotifications.isEmpty())
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
            page = 1;
            mBinder.swipeRefreshLayout.setRefreshing(false);
            fetchUserNotifications(page,limit,true);
        }
    }

    @Override
    public void onNotificationClicked(NotificationDto notificationDto) {
        switch (notificationDto.getType())
        {
            case Constants.TYPE_OFFER_SENT:
                Intent intent = new Intent(getActivity(),SellerOffersActivity.class);
                intent.putExtra(Constants.EXTRA_PRODUCT_ID,notificationDto.getBuyerProductId());
                startActivity(intent);
                break;
            case Constants.TYPE_BUYER_UNLOCKED:
                fetchCurrentOffers(1,notificationDto);
                break;
            case Constants.TYPE_BUYER_MARKED_COMPLETE:
                fetchCurrentOffers(1,notificationDto);
                break;
            case Constants.TYPE_PRODUCT_SOLD_BY_ANOTHER:
                fetchCurrentOffers(1,notificationDto);
                break;
            case Constants.TYPE_SELLER_MARKED_COMPLETE:
                fetchPurchasedItems(notificationDto);
                break;
        }
    }

    private void fetchCurrentOffers(int type, final NotificationDto notificationDto) {

        if (isFetching) {
            return;
        }

        showProgressBar();

        isFetching = true;
        AuthWebServices apiService = RequestController.createRetrofitRequest(false);

        apiService.getSellerOffers(type).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<SellerOffersResponse>(getActivity()) {

            @Override
            public void onResponse(SellerOffersResponse response) {
                hideProgressBar();
                if (response != null && response.isSuccess()) {
                    if(response.getResult()!=null && response.getResult().getSellerProduct()!=null)
                    {
                        for(SellerOfferDto sellerOfferDto:response.getResult().getSellerProduct())
                        {
                            if(sellerOfferDto.getId()==notificationDto.getSellerProductId())
                            {
                                Intent intent = new Intent(getActivity(),SellerOwnOfferDetailActivity.class);
                                intent.putExtra(Constants.EXTRA_PRODUCT_DETAIL,sellerOfferDto);
                                startActivity(intent);
                                break;
                            }
                        }
                    }
                } else {
                    showToast(response.getMessage());
//                    showSnackBarFromBottom(response.getMessage(), mBinding.mainContainer, true);
                }
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

    private void fetchPurchasedItems(final NotificationDto notificationDto) {

        if (isFetching) {
            return;
        }

        mBinder.swipeRefreshLayout.setRefreshing(true);

        isFetching = true;
        AuthWebServices apiService = RequestController.createRetrofitRequest(false);

        apiService.getBuyerPurchasedList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<PurchasedResponse>(getActivity()) {

            @Override
            public void onResponse(PurchasedResponse response) {
                hideProgressBar();
                if (response != null && response.isSuccess()) {
                    if(response.getResult()!=null && response.getResult().getBuyerProduct()!=null)
                    {
                       for(PurchasedProductDto purchasedProductDto:response.getResult().getBuyerProduct())
                       {
                           if(purchasedProductDto.getSellerProducts().get(0).getBuyerProductId()==notificationDto.getBuyerProductId())
                           {
                               Intent intent = new Intent(getActivity(),PurchasedItemDetailActivity.class);
                               intent.putExtra(Constants.EXTRA_PRODUCT_DETAIL,purchasedProductDto);
                               startActivity(intent);
                               break;
                           }
                       }
                    }
                } else {
                    showToast(response.getMessage());
//                    showSnackBarFromBottom(response.getMessage(), mBinding.mainContainer, true);
                }
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
}
