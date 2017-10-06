package com.revauc.revolutionbuy.ui.buy.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ItemProductListBinding;

/*
Copyright © 2017 Block Partee. All rights reserved.
Developed by Appster.
*/

public class PurchasedAdapter extends RecyclerView.Adapter<PurchasedAdapter.MyViewHolder> {

    private Context mContext;
    private ItemProductListBinding mBinding;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvtitle;
        TextView tvCategories;
        public MyViewHolder(View view) {
            super(view);
            tvtitle = mBinding.textTitle;
            tvCategories = mBinding.textCategories;
        }
    }

    public PurchasedAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_product_list, parent, false);

        return new MyViewHolder(mBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 20;
    }


}
