package com.revauc.revolutionbuy.ui.buy.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.network.response.buyer.BuyerProductImageDto;
import com.squareup.picasso.Picasso;

import java.util.List;



public class ProductImageAdapter  extends PagerAdapter {

    private final List<BuyerProductImageDto> mProductImageDtoList;
    Context mContext;
    LayoutInflater mLayoutInflater;

    public ProductImageAdapter(Context context, List<BuyerProductImageDto> productImageDtoList) {
        mContext = context;
        mProductImageDtoList = productImageDtoList;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mProductImageDtoList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.item_product_image, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.product_image);
        Picasso.with(mContext).load(mProductImageDtoList.get(position).getImageName()).
                placeholder(R.drawable.ic_placeholder_purchase_detail).into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}