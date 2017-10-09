package com.revauc.revolutionbuy.ui.buy.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ItemCategoriesBinding;
import com.revauc.revolutionbuy.databinding.ItemProductListBinding;
import com.revauc.revolutionbuy.listeners.OnCategorySelectListener;
import com.revauc.revolutionbuy.network.response.buyer.BuyerProductDto;
import com.revauc.revolutionbuy.network.response.buyer.CategoryDto;
import com.revauc.revolutionbuy.widget.roundedimageview.RoundedImageView;

import java.util.List;

/*
Copyright © 2017 Block Partee. All rights reserved.
Developed by Appster.
*/

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.MyViewHolder> {

    private final List<BuyerProductDto> mBuyerProducts;
    private Context mContext;
    private ItemProductListBinding mBinding;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView imageProduct;
        TextView tvtitle;
        TextView tvCategories;
        public MyViewHolder(View view) {
            super(view);
            imageProduct = mBinding.imageProduct;
            tvtitle = mBinding.textTitle;
            tvCategories = mBinding.textCategories;
        }
    }

    public WishListAdapter(Context mContext, List<BuyerProductDto> mBuyerProducts) {
        this.mContext = mContext;
        this.mBuyerProducts = mBuyerProducts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_product_list, parent, false);

        return new MyViewHolder(mBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        BuyerProductDto buyerProductDto = mBuyerProducts.get(position);
        holder.tvtitle.setText(buyerProductDto.getTitle());
        holder.tvCategories.setText(buyerProductDto.getBuyerProductCategoriesString()+"");
    }

    @Override
    public int getItemCount() {
        return mBuyerProducts.size();
    }


}
