package com.revauc.revolutionbuy.ui.buy;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ActivityAddTitleBinding;
import com.revauc.revolutionbuy.databinding.ActivityBuyerProductDetailBinding;
import com.revauc.revolutionbuy.network.response.buyer.BuyerProductDto;
import com.revauc.revolutionbuy.ui.BaseActivity;
import com.revauc.revolutionbuy.ui.buy.adapter.ProductImageAdapter;
import com.revauc.revolutionbuy.util.Constants;
import com.squareup.picasso.Picasso;


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
    }


    @Override
    public void onClick(View view) {

    }
}

