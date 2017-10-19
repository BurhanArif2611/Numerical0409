package com.revauc.revolutionbuy.ui.buy;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ActivityAddTitleBinding;
import com.revauc.revolutionbuy.databinding.ActivityBuyerProductDetailBinding;
import com.revauc.revolutionbuy.eventbusmodel.OnButtonClicked;
import com.revauc.revolutionbuy.network.BaseResponse;
import com.revauc.revolutionbuy.network.RequestController;
import com.revauc.revolutionbuy.network.request.auth.MobilePinRequest;
import com.revauc.revolutionbuy.network.response.buyer.BuyerProductDto;
import com.revauc.revolutionbuy.network.retrofit.AuthWebServices;
import com.revauc.revolutionbuy.network.retrofit.DefaultApiObserver;
import com.revauc.revolutionbuy.ui.BaseActivity;
import com.revauc.revolutionbuy.ui.auth.MobilePinVerificationActivity;
import com.revauc.revolutionbuy.ui.auth.MobileVerificationActivity;
import com.revauc.revolutionbuy.ui.buy.adapter.ProductImageAdapter;
import com.revauc.revolutionbuy.util.Constants;
import com.revauc.revolutionbuy.util.PreferenceUtil;
import com.revauc.revolutionbuy.widget.BottomSheetAlertInverse;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class BuyerProductDetailActivity extends BaseActivity implements View.OnClickListener {


    private ActivityBuyerProductDetailBinding mBinding;
    private BuyerProductDto mProductDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_buyer_product_detail);

        mProductDetail = getIntent().getParcelableExtra(Constants.EXTRA_PRODUCT_DETAIL);

        mBinding.textTitle.setText(mProductDetail.getTitle());
        mBinding.textCategories.setText(mProductDetail.getBuyerProductCategoriesString() + "");
        mBinding.textDescription.setText(mProductDetail.getDescription());

        if (mProductDetail.getBuyerProductImages() != null && !mProductDetail.getBuyerProductImages().isEmpty()) {
            if(mProductDetail.getBuyerProductImages().size()>1)
            {
                mBinding.viewpagerImages.setAdapter(new ProductImageAdapter(this,mProductDetail.getBuyerProductImages()));
                mBinding.indicatorImages.setViewPager(mBinding.viewpagerImages);
            }
            else
            {
                Picasso.with(this).load(mProductDetail.getBuyerProductImages().get(0)
                        .getImageName()).placeholder(R.drawable.ic_placeholder_purchase_detail).into(mBinding.imageProduct);
            }
        } else {
            mBinding.imageProduct.setImageResource(R.drawable.ic_placeholder_purchase_detail);
        }

        mBinding.imageDelete.setOnClickListener(this);
        mBinding.imageBack.setOnClickListener(this);
        mBinding.imageShare.setOnClickListener(this);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }


    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.image_delete:
                BottomSheetAlertInverse.getInstance(this,getString(R.string.sure_to_delete),getString(R.string.text_delete),getString(R.string.cancel)).show();

            break;
            case R.id.image_back:
                onBackPressed();
                break;
            case R.id.image_share:
                shareThisProduct();
                break;
        }

    }

    @Subscribe
    public void onDelete(OnButtonClicked onDeleteClicked)
    {
        deleteBuyerProduct(mProductDetail.getId());
    }

    private void shareThisProduct() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey! Checkout this item on RevolutionBuy App.\n\n"+mProductDetail.getTitle()+"\n\n"+"https://dev.revolutionbuy.com/api/share-link?itemId="+mProductDetail.getId());
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    private void deleteBuyerProduct(int id) {
        showProgressBar();
        AuthWebServices apiService = RequestController.createRetrofitRequest(false);

        apiService.deleteBuyerProduct(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<BaseResponse>(this) {

            @Override
            public void onResponse(BaseResponse response) {
                hideProgressBar();
                if (response.isSuccess()) {
                    onBackPressed();
                    showToast(response.getMessage());
                }
                else
                {
                    showSnackBarFromBottom(response.getMessage(),mBinding.mainContainer, true);
                }

            }

            @Override
            public void onError(Throwable call, BaseResponse baseResponse) {
                hideProgressBar();
                if (baseResponse != null) {
                    String errorMessage = baseResponse.getMessage();
                    showSnackBarFromBottom(errorMessage,mBinding.mainContainer, true);
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
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}

