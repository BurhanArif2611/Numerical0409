package com.revauc.revolutionbuy.ui.sell;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.facebook.rebound.ui.Util;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.analytics.AnalyticsManager;
import com.revauc.revolutionbuy.databinding.ActivitySellerOwnOfferDetailBinding;
import com.revauc.revolutionbuy.databinding.ActivitySellerProductDetailBinding;
import com.revauc.revolutionbuy.eventbusmodel.OnButtonClicked;
import com.revauc.revolutionbuy.network.BaseResponse;
import com.revauc.revolutionbuy.network.RequestController;
import com.revauc.revolutionbuy.network.request.auth.BuyerCompleteTransactionRequest;
import com.revauc.revolutionbuy.network.response.buyer.BuyerProductDto;
import com.revauc.revolutionbuy.network.response.seller.SellerOfferDto;
import com.revauc.revolutionbuy.network.retrofit.AuthWebServices;
import com.revauc.revolutionbuy.network.retrofit.DefaultApiObserver;
import com.revauc.revolutionbuy.ui.BaseActivity;
import com.revauc.revolutionbuy.ui.buy.ItemPurchasedActivity;
import com.revauc.revolutionbuy.ui.buy.SellerOfferDetailActivity;
import com.revauc.revolutionbuy.ui.buy.adapter.ProductImageAdapter;
import com.revauc.revolutionbuy.util.Constants;
import com.revauc.revolutionbuy.util.Utils;
import com.revauc.revolutionbuy.widget.BottomSheetAlertInverse;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class SellerOwnOfferDetailActivity extends BaseActivity implements View.OnClickListener {


    private ActivitySellerOwnOfferDetailBinding mBinding;
    private SellerOfferDto mProductDetail;
    private final BroadcastReceiver mReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };
    private MixpanelAPI mixpanelAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_seller_own_offer_detail);
        mixpanelAPI = MixpanelAPI.getInstance(this, getString(R.string.mixpanel_token));
        mProductDetail = getIntent().getParcelableExtra(Constants.EXTRA_PRODUCT_DETAIL);

        mBinding.textTitle.setText(mProductDetail.getBuyerProduct().getTitle());
        mBinding.textCategories.setText(mProductDetail.getBuyerProduct().getBuyerProductCategoriesString() + "");
        mBinding.textYourOfferPrice.setText((mProductDetail.getDescription().split("&&")[0]) + " " + mProductDetail.getPrice());
        mBinding.textStatus.setText(Utils.getSellerOfferStates(mProductDetail.getState()));

        //Handle States
        if (mProductDetail.getState() == Constants.STATE_ITEM_SOLD_TO_BUYER) {
            mBinding.textStatus.setTextColor(ContextCompat.getColor(this, R.color.text_color_green87));
            mBinding.textStatus.setBackgroundResource(R.drawable.bg_status_green);
        } else {
            mBinding.textStatus.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary87));
            mBinding.textStatus.setBackgroundResource(R.drawable.bg_status_blue);
            if (mProductDetail.getState() == Constants.STATE_BUYER_ACCEPTED_MY_OFFER) {
                mBinding.textMarkComplete.setVisibility(View.VISIBLE);
                mBinding.textMarkComplete.setOnClickListener(this);
            }
        }

        if (mProductDetail.getSellerProductImages() != null && !mProductDetail.getSellerProductImages().isEmpty()) {
            if (mProductDetail.getSellerProductImages().size() > 1) {
                mBinding.viewpagerImages.setAdapter(new ProductImageAdapter(this, mProductDetail.getSellerProductImages()));
                mBinding.indicatorImages.setViewPager(mBinding.viewpagerImages);
            } else {
                Picasso.with(this).load(mProductDetail.getSellerProductImages().get(0)
                        .getImageName()).placeholder(R.drawable.ic_placeholder_purchase_detail).into(mBinding.imageProduct);
            }
        } else {
            mBinding.imageProduct.setImageResource(R.drawable.ic_placeholder_purchase_detail);
        }

        if (mProductDetail.getBuyerProduct().getUser() != null) {
            mBinding.textBuyerName.setText(mProductDetail.getBuyerProduct().getUser().getName());
            mBinding.textBuyerLocation.setText(mProductDetail.getBuyerProduct().getUser().getCity().getName() + ", " + mProductDetail.getBuyerProduct().getUser().getCity().getState().getName());
        }

        mBinding.imageBack.setOnClickListener(this);
        mBinding.imageDelete.setOnClickListener(this);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        LocalBroadcastManager.getInstance(this).registerReceiver(mReciever, new IntentFilter(ItemSoldActivity.BROAD_OFFER_SOLD));
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.image_back:
                onBackPressed();
                break;
            case R.id.image_delete:
                BottomSheetAlertInverse.getInstance(this,Constants.REQUEST_CODE_SELLER_PRODUCT_DELETE, getString(R.string.sure_to_delete_offer), getString(R.string.text_delete), getString(R.string.cancel)).show();
                break;
            case R.id.text_mark_complete:
                markSellerTransactionComplete();
                break;
            case R.id.text_report_item:

                break;
        }

    }



    @Subscribe
    public void onDelete(OnButtonClicked onDeleteClicked) {
        if(onDeleteClicked.getRequestCode()==Constants.REQUEST_CODE_SELLER_PRODUCT_DELETE)
        {
            deleteSellerProduct(mProductDetail.getId());
        }
    }

    private void deleteSellerProduct(int id) {
        showProgressBar();
        AuthWebServices apiService = RequestController.createRetrofitRequest(false);

        apiService.deleteSellerProduct(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<BaseResponse>(this) {

            @Override
            public void onResponse(BaseResponse response) {
                hideProgressBar();
                if (response.isSuccess()) {
                    onBackPressed();
                    showToast(response.getMessage());
                } else {
                    showSnackBarFromBottom(response.getMessage(), mBinding.mainContainer, true);
                }

            }

            @Override
            public void onError(Throwable call, BaseResponse baseResponse) {
                hideProgressBar();
                if (baseResponse != null) {
                    String errorMessage = baseResponse.getMessage();
                    showSnackBarFromBottom(errorMessage, mBinding.mainContainer, true);
                }
            }
        });
    }

    private void markSellerTransactionComplete() {
        showProgressBar();
        AuthWebServices apiService = RequestController.createRetrofitRequest(false);

        apiService.markSellerTransactionComplete(new BuyerCompleteTransactionRequest("Received in Cash", mProductDetail.getBuyerProduct().getTitle(), mProductDetail.getBuyerProductId(), mProductDetail.getId(), mProductDetail.getBuyerProduct().getUser().getId())).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<BaseResponse>(this) {

            @Override
            public void onResponse(BaseResponse response) {
                hideProgressBar();
                if (response.isSuccess()) {
                    AnalyticsManager.trackMixpanelEvent(mixpanelAPI,"Deal Successful");
                    startActivity(new Intent(SellerOwnOfferDetailActivity.this, ItemSoldActivity.class));
//                    showToast(response.getMessage());
                } else {
                    showSnackBarFromBottom(response.getMessage(), mBinding.mainContainer, true);
                }

            }

            @Override
            public void onError(Throwable call, BaseResponse baseResponse) {
                hideProgressBar();
                if (baseResponse != null) {
                    String errorMessage = baseResponse.getMessage();
                    showSnackBarFromBottom(errorMessage, mBinding.mainContainer, true);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    protected void onDestroy() {
        mixpanelAPI.flush();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReciever);
    }
}

