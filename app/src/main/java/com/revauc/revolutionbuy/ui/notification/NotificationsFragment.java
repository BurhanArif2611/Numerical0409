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
import com.revauc.revolutionbuy.network.request.auth.NotificationDetailRequest;
import com.revauc.revolutionbuy.network.response.buyer.BuyerProductDto;
import com.revauc.revolutionbuy.network.response.buyer.PurchasedProductDto;
import com.revauc.revolutionbuy.network.response.buyer.PurchasedResponse;
import com.revauc.revolutionbuy.network.response.profile.NotificationDetailPurchaseResponse;
import com.revauc.revolutionbuy.network.response.profile.NotificationDetailResponse;
import com.revauc.revolutionbuy.network.response.profile.NotificationDetailUnlockResponse;
import com.revauc.revolutionbuy.network.response.profile.NotificationDto;
import com.revauc.revolutionbuy.network.response.profile.NotificationResponse;
import com.revauc.revolutionbuy.network.response.seller.SellerOfferDto;
import com.revauc.revolutionbuy.network.response.seller.SellerOffersResponse;
import com.revauc.revolutionbuy.network.retrofit.AuthWebServices;
import com.revauc.revolutionbuy.network.retrofit.DefaultApiObserver;
import com.revauc.revolutionbuy.ui.BaseFragment;
import com.revauc.revolutionbuy.ui.buy.BuyerProductDetailActivity;
import com.revauc.revolutionbuy.ui.buy.PurchasedItemDetailActivity;
import com.revauc.revolutionbuy.ui.buy.SellerOfferDetailActivity;
import com.revauc.revolutionbuy.ui.buy.SellerOffersActivity;
import com.revauc.revolutionbuy.ui.dashboard.DashboardActivity;
import com.revauc.revolutionbuy.ui.sell.SellerOwnOfferDetailActivity;
import com.revauc.revolutionbuy.ui.sell.adapter.OffersAdapter;
import com.revauc.revolutionbuy.util.Constants;
import com.revauc.revolutionbuy.util.PreferenceUtil;
import com.revauc.revolutionbuy.widget.BottomMemberAlert;

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
        if(!PreferenceUtil.isLoggedIn())
        {
            mBinder.swipeRefreshLayout.setEnabled(false);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(PreferenceUtil.isLoggedIn())
        {
            page = 1;
            fetchUserNotifications(page,limit,true);
        }
        else
        {
            BottomMemberAlert.getInstance(getActivity(),getString(R.string.need_to_be_a_member),getString(R.string.sign_up),getString(R.string.cancel)).show();
        }

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

                        ((DashboardActivity)getActivity()).updateBadgeCount(response.getResult().getTotalUnreadCount());

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
    public void onNotificationClicked(NotificationDto notificationDto,int position) {
        mNotifications.get(position).setIsRead(0);
        mAdapter.notifyDataSetChanged();
        fetchNotificationDetail(notificationDto.getId(),notificationDto.getType());
    }

    private void fetchNotificationDetail(int notificationId, final int type)
    {
        if (isFetching) {
            return;
        }

        showProgressBar();

        isFetching = true;
        AuthWebServices apiService = RequestController.createRetrofitRequest(false);


        if(type==Constants.TYPE_SELLER_MARKED_COMPLETE)
        {

            apiService.getNotificationDetailForPurchase(new NotificationDetailRequest(notificationId)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<NotificationDetailPurchaseResponse>(getActivity()) {

                @Override
                public void onResponse(NotificationDetailPurchaseResponse response) {
                    isFetching = false;
                    hideProgressBar();
                    if (response != null && response.isSuccess()) {
                        if(response.getResult()!=null)
                        {
                            if(response.getResult().getPurchasedProduct()!=null)
                            {
                                Intent intent = new Intent(getActivity(),PurchasedItemDetailActivity.class);
                                intent.putExtra(Constants.EXTRA_PRODUCT_DETAIL,response.getResult().getPurchasedProduct());
                                startActivity(intent);
                            }
                            else
                            {
                                showToast(response.getMessage());
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
                    isFetching = false;
                    if (baseResponse != null) {
                        String errorMessage = baseResponse.getMessage();
                        showToast(errorMessage);
//                    Utils.showSnackbar(errorMessage, mBinder.mainContainer, true);
                    }
                }
            });
        }
        else if(type==Constants.TYPE_BUYER_UNLOCKED)
        {

            apiService.getNotificationDetailForUnlock(new NotificationDetailRequest(notificationId)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<NotificationDetailUnlockResponse>(getActivity()) {

                @Override
                public void onResponse(NotificationDetailUnlockResponse response) {
                    isFetching = false;
                    hideProgressBar();
                    if (response != null && response.isSuccess()) {
                        if(response.getResult()!=null)
                        {
                            if(response.getResult().getBuyerProduct()!=null)
                            {
                                Intent intent = new Intent(getActivity(),BuyerProductDetailActivity.class);
                                intent.putExtra(Constants.EXTRA_PRODUCT_DETAIL,response.getResult().getBuyerProduct());
                                startActivity(intent);
                            }
                            else
                            {
                                showToast(response.getMessage());
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
                    isFetching = false;
                    if (baseResponse != null) {
                        String errorMessage = baseResponse.getMessage();
                        showToast(errorMessage);
//                    Utils.showSnackbar(errorMessage, mBinder.mainContainer, true);
                    }
                }
            });
        }
        else
        {
            apiService.getNotificationDetail(new NotificationDetailRequest(notificationId)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<NotificationDetailResponse>(getActivity()) {

                @Override
                public void onResponse(NotificationDetailResponse response) {
                    hideProgressBar();
                    isFetching = false;
                    if (response != null && response.isSuccess()) {
                        if(response.getResult()!=null)
                        {
                            switch (type)
                            {
                                case Constants.TYPE_OFFER_SENT:
                                    if(response.getResult().getSellerProduct()!=null)
                                    {
                                        Intent intent = new Intent(getActivity(),SellerOfferDetailActivity.class);
                                        intent.putExtra(Constants.EXTRA_PRODUCT_DETAIL,response.getResult().getSellerProduct());
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        showToast(response.getMessage());
                                    }
                                    break;
                                case Constants.TYPE_BUYER_MARKED_COMPLETE:
                                    if(response.getResult().getSellerProduct()!=null)
                                    {
                                        Intent intent = new Intent(getActivity(),SellerOwnOfferDetailActivity.class);
                                        intent.putExtra(Constants.EXTRA_PRODUCT_DETAIL,response.getResult().getSellerProduct());
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        showToast(response.getMessage());
                                    }
                                    break;
                                case Constants.TYPE_PRODUCT_SOLD_BY_ANOTHER:
                                    if(response.getResult().getSellerProduct()!=null)
                                    {
                                        Intent intent = new Intent(getActivity(),SellerOwnOfferDetailActivity.class);
                                        intent.putExtra(Constants.EXTRA_PRODUCT_DETAIL,response.getResult().getSellerProduct());
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        showToast(response.getMessage());
                                    }
                                    break;
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
                    isFetching = false;
                    if (baseResponse != null) {
                        String errorMessage = baseResponse.getMessage();
                        showToast(errorMessage);
//                    Utils.showSnackbar(errorMessage, mBinder.mainContainer, true);
                    }
                }
            });
        }
    }

}