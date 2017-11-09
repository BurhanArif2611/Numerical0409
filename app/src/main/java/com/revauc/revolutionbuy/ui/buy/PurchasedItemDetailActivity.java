package com.revauc.revolutionbuy.ui.buy;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ActivityPurchasedItemBinding;
import com.revauc.revolutionbuy.databinding.ActivitySellerOfferDetailBinding;
import com.revauc.revolutionbuy.network.BaseResponse;
import com.revauc.revolutionbuy.network.RequestController;
import com.revauc.revolutionbuy.network.request.auth.BuyerCompleteTransactionRequest;
import com.revauc.revolutionbuy.network.response.buyer.PurchasedProductDto;
import com.revauc.revolutionbuy.network.response.seller.SellerOfferDto;
import com.revauc.revolutionbuy.network.retrofit.AuthWebServices;
import com.revauc.revolutionbuy.network.retrofit.DefaultApiObserver;
import com.revauc.revolutionbuy.ui.BaseActivity;
import com.revauc.revolutionbuy.ui.buy.adapter.ProductImageAdapter;
import com.revauc.revolutionbuy.ui.sell.ReportItemActivity;
import com.revauc.revolutionbuy.util.Constants;
import com.revauc.revolutionbuy.util.StringUtils;
import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class PurchasedItemDetailActivity extends BaseActivity implements View.OnClickListener {


    private ActivityPurchasedItemBinding mBinding;
    private PurchasedProductDto mProductDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_purchased_item);

        mProductDetail = getIntent().getParcelableExtra(Constants.EXTRA_PRODUCT_DETAIL);

        mBinding.textTitle.setText(mProductDetail.getTitle());
        mBinding.textCategories.setText(mProductDetail.getBuyerProductCategoriesString() + "");
        if(mProductDetail.getSellerProducts()!=null && !mProductDetail.getSellerProducts().isEmpty())
        {
            if(StringUtils.isNullOrEmpty(mProductDetail.getSellerProducts().get(0).getDescription()))
            {
                mBinding.textPriceOffered.setText("");
            }
            else
            {
                mBinding.textPriceOffered.setText((mProductDetail.getSellerProducts().get(0).getDescription().split("&&")[0])+" "+mProductDetail.getSellerProducts().get(0).getPrice());
            }


            //IMAGES
            if (mProductDetail.getSellerProducts().get(0).getSellerProductImages() != null && !mProductDetail.getSellerProducts().get(0).getSellerProductImages().isEmpty()) {
                if(mProductDetail.getSellerProducts().get(0).getSellerProductImages().size()>1)
                {
                    mBinding.viewpagerImages.setAdapter(new ProductImageAdapter(this,mProductDetail.getSellerProducts().get(0).getSellerProductImages()));
                    mBinding.indicatorImages.setViewPager(mBinding.viewpagerImages);
                }
                else
                {
                    Picasso.with(this).load(mProductDetail.getSellerProducts().get(0).getSellerProductImages().get(0)
                            .getImageName()).placeholder(R.drawable.ic_placeholder_purchase_detail).into(mBinding.imageProduct);
                }
            } else {
                mBinding.imageProduct.setImageResource(R.drawable.ic_placeholder_purchase_detail);
            }
           //USER DETAIL

            if(mProductDetail.getSellerProducts().get(0).getUser()!=null)
            {
                mBinding.textSellerName.setText(mProductDetail.getSellerProducts().get(0).getUser().getName());
                mBinding.textSellerLocation.setText(mProductDetail.getSellerProducts().get(0).getUser().getCity().getName()+", "+mProductDetail.getSellerProducts().get(0).getUser().getCity().getState().getName());
                mBinding.textItemSold.setText(""+mProductDetail.getSellerProducts().get(0).getUser().getSoldCount());
                mBinding.textItemPurchased.setText(""+mProductDetail.getSellerProducts().get(0).getUser().getPurchasedCount());
                mBinding.textMobileNo.setText(mProductDetail.getSellerProducts().get(0).getUser().getMobile());
            }

        }


        mBinding.imageBack.setOnClickListener(this);
        mBinding.textMobile.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.image_back:
                onBackPressed();
                break;
            case R.id.text_mobile:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mProductDetail.getSellerProducts().get(0).getUser().getMobile()));
                startActivity(intent);
                break;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

}

