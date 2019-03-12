package com.revauc.revolutionbuy.ui.sell.adapter;

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
import com.revauc.revolutionbuy.listeners.OnSellerOfferClickListener;
import com.revauc.revolutionbuy.listeners.OnWishlistClickListener;
import com.revauc.revolutionbuy.network.response.seller.SellerOfferDto;
import com.revauc.revolutionbuy.widget.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;



public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.MyViewHolder> {

    private final List<SellerOfferDto> mBuyerProducts;
    private final OnSellerOfferClickListener onWishlistClickListener;
    private final int type;
    private Context mContext;
    private ItemSellerOwnOfferBinding mBinding;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView imageProduct;
        TextView tvtitle;
        TextView tvBuyerLabel;
        TextView tvBuyerName;
        TextView tvPrice;

        public MyViewHolder(View view) {
            super(view);
            imageProduct = mBinding.imageItem;
            tvtitle = mBinding.textProductName;
            tvBuyerLabel = mBinding.textBuyerLabel;
            tvBuyerName = mBinding.textBuyerName;
            tvPrice = mBinding.textProductPrice;
            tvBuyerLabel.setText(type==1?"Interested buyer:":"Buyer:");
        }
    }

    public OffersAdapter(Context mContext, List<SellerOfferDto> mBuyerProducts, OnSellerOfferClickListener onWishlistClickListener,int type) {
        this.onWishlistClickListener = onWishlistClickListener;
        this.mContext = mContext;
        this.mBuyerProducts = mBuyerProducts;
        this.type = type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_seller_own_offer, parent, false);

        return new MyViewHolder(mBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final SellerOfferDto sellerOfferDto = mBuyerProducts.get(position);
        holder.tvtitle.setText(sellerOfferDto.getBuyerProduct().getTitle());
        holder.tvBuyerName.setText(sellerOfferDto.getBuyerProduct().getUser().getName());
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
                onWishlistClickListener.onSellerOfferClicked(sellerOfferDto);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBuyerProducts.size();
    }


}
