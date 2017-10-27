package com.revauc.revolutionbuy.ui.buy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ActivitySellerOffersBinding;
import com.revauc.revolutionbuy.listeners.OnSellerOfferClickListener;
import com.revauc.revolutionbuy.listeners.OnWishlistClickListener;
import com.revauc.revolutionbuy.network.BaseResponse;
import com.revauc.revolutionbuy.network.RequestController;
import com.revauc.revolutionbuy.network.response.buyer.BuyerProductDto;
import com.revauc.revolutionbuy.network.response.seller.SellerOfferDto;
import com.revauc.revolutionbuy.network.response.seller.SellerOffersResponse;
import com.revauc.revolutionbuy.network.retrofit.AuthWebServices;
import com.revauc.revolutionbuy.network.retrofit.DefaultApiObserver;
import com.revauc.revolutionbuy.ui.BaseActivity;
import com.revauc.revolutionbuy.ui.buy.adapter.SellerOffersAdapter;
import com.revauc.revolutionbuy.ui.buy.adapter.WishListAdapter;
import com.revauc.revolutionbuy.util.Constants;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class SellerOffersActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, OnSellerOfferClickListener {


    private ActivitySellerOffersBinding mBinding;

    private int page=1;
    private int limit=10;
    private SellerOffersAdapter mAdapter;
    private List<SellerOfferDto> mSellerOffers = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    private int mTotalCount;
    private boolean isFetching = false;
    private BuyerProductDto mBuyerProduct;
    private final BroadcastReceiver mReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

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
                            fetchSellersOffers(page,limit,false);
                            mBinding.progressbarLoading.setVisibility(View.VISIBLE);
                        }
                    } else
                        mBinding.progressbarLoading.setVisibility(View.GONE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_seller_offers);
        mBuyerProduct = getIntent().getParcelableExtra(Constants.EXTRA_PRODUCT_DETAIL);
        mBinding.toolbarSell.ivToolBarLeft.setImageResource(R.drawable.ic_back_blue);
        mBinding.toolbarSell.txvToolbarGeneralCenter.setText(R.string.seller_offers);
        mBinding.textProductName.setText(mBuyerProduct.getTitle());
        mBinding.toolbarSell.ivToolBarLeft.setOnClickListener(this);

        //Setting recycler
        mAdapter = new SellerOffersAdapter(this,mSellerOffers,this);
        mLayoutManager = new LinearLayoutManager(this);
        mBinding.recyclerViewOffers.setLayoutManager(mLayoutManager);
        mBinding.recyclerViewOffers.setLayoutManager(mLayoutManager);
        mBinding.recyclerViewOffers.setAdapter(mAdapter);
        mBinding.recyclerViewOffers.addOnScrollListener(mRecyclerListner);
        mBinding.swipeRefreshLayout.setOnRefreshListener(this);

        fetchSellersOffers(page,limit,true);

        LocalBroadcastManager.getInstance(this).registerReceiver(mReciever, new IntentFilter(ItemPurchasedActivity.BROAD_OFFER_PURCHASED));
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_tool_bar_left:
                onBackPressed();

                break;
            case R.id.iv_tool_bar_right:
                onBackPressed();
                break;
        }

    }

    private void fetchSellersOffers(final int page, int limit,boolean showLoading) {

        if (isFetching) {
            return;
        }

        if(showLoading)
        {
            mBinding.swipeRefreshLayout.setRefreshing(true);
        }

        isFetching = true;
        AuthWebServices apiService = RequestController.createRetrofitRequest(false);
        apiService.getSellerOffersForBuyer(page,mBuyerProduct.getId(),limit).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<SellerOffersResponse>(this) {

            @Override
            public void onResponse(SellerOffersResponse response) {
                hideProgressBar();
                if (response != null && response.isSuccess()) {
                    if(response.getResult()!=null && response.getResult().getSellerProduct()!=null)
                    {
                        if(page==1)
                        {
                            mSellerOffers.clear();
                            mTotalCount = response.getResult().getTotalCount();
                        }
                        mSellerOffers.addAll(response.getResult().getSellerProduct());
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
        mBinding.swipeRefreshLayout.setRefreshing(false);
        mBinding.progressbarLoading.setVisibility(View.GONE);
        isFetching = false;
        if(mSellerOffers!=null && !mSellerOffers.isEmpty())
        {
            mBinding.textNoData.setVisibility(View.GONE);

        }
        else
        {
            mBinding.textNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRefresh() {
        if(!isFetching) {
            page = 1;
            mBinding.swipeRefreshLayout.setRefreshing(false);
            fetchSellersOffers(page,limit,true);
        }
    }



    @Override
    public void onSellerOfferClicked(SellerOfferDto sellerOfferDto) {
        Intent intent = new Intent(this,SellerOfferDetailActivity.class);
        intent.putExtra(Constants.EXTRA_PRODUCT_DETAIL,sellerOfferDto);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReciever);
    }
}

