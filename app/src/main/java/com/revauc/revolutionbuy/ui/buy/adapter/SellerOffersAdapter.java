package com.revauc.revolutionbuy.ui.buy.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ItemSellerOfferBinding;
import com.revauc.revolutionbuy.databinding.ItemSellerOwnOfferBinding;
import com.revauc.revolutionbuy.listeners.OnWishlistClickListener;
import com.revauc.revolutionbuy.network.response.seller.SellerOfferDto;
import com.revauc.revolutionbuy.widget.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/*
Copyright © 2017 Block Partee. All rights reserved.
Developed by Appster.
*/

public class SellerOffersAdapter extends RecyclerView.Adapter<SellerOffersAdapter.MyViewHolder> {

    private final List<SellerOfferDto> mBuyerProducts;
    private final OnWishlistClickListener onWishlistClickListener;
    private Context mContext;
    private ItemSellerOfferBinding mBinding;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView imageProduct;
        TextView tvBy;
        TextView tvFrom;
        TextView tvPrice;

        public MyViewHolder(View view) {
            super(view);
            imageProduct = mBinding.imageItem;
            tvBy = mBinding.textBy;
            tvFrom = mBinding.textFrom;
            tvPrice = mBinding.textFor;;
        }
    }

    public SellerOffersAdapter(Context mContext, List<SellerOfferDto> mBuyerProducts, OnWishlistClickListener onWishlistClickListener) {
        this.onWishlistClickListener = onWishlistClickListener;
        this.mContext = mContext;
        this.mBuyerProducts = mBuyerProducts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_seller_offer, parent, false);

        return new MyViewHolder(mBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final SellerOfferDto sellerOfferDto = mBuyerProducts.get(position);
        holder.tvBy.setText("By "+sellerOfferDto.getUser().getName());
        holder.tvFrom.setText("From "+sellerOfferDto.getUser().getCity().getName()+", "+sellerOfferDto.getUser().getCity().getState().getName());
        holder.tvPrice.setText((sellerOfferDto.getDescription().split("&&")[0])+" "+sellerOfferDto.getPrice());

        if (sellerOfferDto.getSellerProductImages() != null && !sellerOfferDto.getSellerProductImages().isEmpty()) {
            Picasso.with(mContext).load(sellerOfferDto.getSellerProductImages().get(0)
                    .getImageName()).placeholder(R.drawable.ic_placeholder_purchase_detail).into(holder.imageProduct);
        } else {
            holder.imageProduct.setImageResource(R.drawable.ic_placeholder_purchase_detail);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onWishlistClickListener.onWishlistItemClicked(sellerOfferDto.getBuyerProduct());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBuyerProducts.size();
    }


}
