package com.revauc.revolutionbuy.ui.sell;

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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ActivityAddTitleBinding;
import com.revauc.revolutionbuy.databinding.ActivityCategoriesProductsListingBinding;
import com.revauc.revolutionbuy.listeners.OnWishlistClickListener;
import com.revauc.revolutionbuy.network.BaseResponse;
import com.revauc.revolutionbuy.network.RequestController;
import com.revauc.revolutionbuy.network.request.auth.ProductSearchRequest;
import com.revauc.revolutionbuy.network.response.buyer.BuyerProductDto;
import com.revauc.revolutionbuy.network.response.buyer.WishlistResponse;
import com.revauc.revolutionbuy.network.response.seller.SellerProductsResponse;
import com.revauc.revolutionbuy.network.retrofit.AuthWebServices;
import com.revauc.revolutionbuy.network.retrofit.DefaultApiObserver;
import com.revauc.revolutionbuy.ui.BaseActivity;
import com.revauc.revolutionbuy.ui.buy.AddDescriptionActivity;
import com.revauc.revolutionbuy.ui.buy.BuyerProductDetailActivity;
import com.revauc.revolutionbuy.ui.buy.adapter.ItemListedActivity;
import com.revauc.revolutionbuy.ui.buy.adapter.WishListAdapter;
import com.revauc.revolutionbuy.util.Constants;
import com.revauc.revolutionbuy.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class SellerProductLlistingActivity extends BaseActivity implements View.OnClickListener, OnWishlistClickListener, SwipeRefreshLayout.OnRefreshListener {


    private ActivityCategoriesProductsListingBinding mBinding;
    private String mCategory;
    private String mCategoryName;
    private int page=1;
    private int limit=10;
    private WishListAdapter mAdapter;
    private List<BuyerProductDto> mSellerProducts = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    private int mTotalCount;
    private boolean isFetching = false;
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
                            fetchSellersProductListing(page,limit,false);
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
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_categories_products_listing);
        mCategoryName = getIntent().getStringExtra(Constants.EXTRA_CATEGORY_NAME);
        mCategory = getIntent().getStringExtra(Constants.EXTRA_CATEGORY);
        mBinding.toolbarSell.ivToolBarLeft.setImageResource(R.drawable.ic_back_blue);
        mBinding.toolbarSell.ivToolBarRight.setImageResource(R.drawable.ic_cart);
        mBinding.toolbarSell.txvToolbarGeneralCenter.setText(mCategoryName);
        mBinding.toolbarSell.ivToolBarLeft.setOnClickListener(this);
        mBinding.toolbarSell.ivToolBarRight.setOnClickListener(this);

        //Setting recycler
        mAdapter = new WishListAdapter(this,mSellerProducts,this);
        mLayoutManager = new LinearLayoutManager(this);
        mBinding.recyclerViewProducts.setLayoutManager(mLayoutManager);
        mBinding.recyclerViewProducts.setLayoutManager(mLayoutManager);
        mBinding.recyclerViewProducts.setAdapter(mAdapter);
        mBinding.recyclerViewProducts.addOnScrollListener(mRecyclerListner);
        mBinding.swipeRefreshLayout.setOnRefreshListener(this);

        fetchSellersProductListing(page,limit,true);

        LocalBroadcastManager.getInstance(this).registerReceiver(mReciever, new IntentFilter(OfferSentActivity.BROAD_OFFER_SENT_COMPLETE));
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
                startActivity(new Intent(this,SellerOfferActivity.class));
                break;
        }

    }

    private void fetchSellersProductListing(final int page, int limit,boolean showLoading) {

        if (isFetching) {
            return;
        }

        if(showLoading)
        {
            mBinding.swipeRefreshLayout.setRefreshing(true);
        }

        isFetching = true;
        AuthWebServices apiService = RequestController.createRetrofitRequest(false);

        apiService.getSellerProductsListing(new ProductSearchRequest(page,limit,"",mCategory)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<SellerProductsResponse>(this) {

            @Override
            public void onResponse(SellerProductsResponse response) {
                hideProgressBar();
                if (response != null && response.isSuccess()) {
                    if(response.getResult()!=null && response.getResult().getProduct()!=null)
                    {
                        if(page==1)
                        {
                            mSellerProducts.clear();
                            mTotalCount = response.getResult().getTotalCount();
                        }
                        mSellerProducts.addAll(response.getResult().getProduct());
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
        if(mSellerProducts!=null && !mSellerProducts.isEmpty())
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
            fetchSellersProductListing(page,limit,true);
        }
    }

    @Override
    public void onWishlistItemClicked(BuyerProductDto buyerProduct) {
        Intent intent = new Intent(this,SellerProductDetailActivity.class);
        intent.putExtra(Constants.EXTRA_PRODUCT_DETAIL,buyerProduct);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReciever);
    }
}

