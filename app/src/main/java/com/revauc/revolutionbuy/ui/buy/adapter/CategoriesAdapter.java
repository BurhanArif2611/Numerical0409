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
import com.revauc.revolutionbuy.listeners.OnCategorySelectListener;
import com.revauc.revolutionbuy.network.response.buyer.CategoryDto;

import java.util.ArrayList;
import java.util.List;

/*
Copyright © 2017 Block Partee. All rights reserved.
Developed by Appster.
*/

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {

    private final OnCategorySelectListener mListener;
    private List<CategoryDto> model;
    private Context mContext;
    private ItemCategoriesBinding mBinding;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategory;
        ImageView ivSelect;
        public MyViewHolder(View view) {
            super(view);
            tvCategory = mBinding.textCategory;
            ivSelect = mBinding.imageSelected;
        }
    }

    public CategoriesAdapter(Context mContext, List<CategoryDto> model, OnCategorySelectListener listener) {
        this.model = model;
        this.mContext = mContext;
        this.mListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_categories, parent, false);

        return new MyViewHolder(mBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.tvCategory.setText(model.get(position).getName());

        holder.ivSelect.setImageResource(model.get(position).isSelected()?R.drawable.ic_select:R.drawable.ic_unselect);

        holder.ivSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onCategorySelected(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return model.size();
    }


}
