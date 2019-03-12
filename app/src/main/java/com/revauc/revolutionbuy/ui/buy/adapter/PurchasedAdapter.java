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
import com.revauc.revolutionbuy.listeners.OnPurchasedClickListener;
import com.revauc.revolutionbuy.listeners.OnWishlistClickListener;
import com.revauc.revolutionbuy.network.response.buyer.BuyerProductDto;
import com.revauc.revolutionbuy.network.response.buyer.PurchasedProductDto;
import com.revauc.revolutionbuy.widget.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;



public class PurchasedAdapter extends RecyclerView.Adapter<PurchasedAdapter.MyViewHolder> {

    private final List<PurchasedProductDto> mBuyerProducts;
    private final OnPurchasedClickListener onPurchasedClickListener;
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

    public PurchasedAdapter(Context mContext, List<PurchasedProductDto> mBuyerProducts, OnPurchasedClickListener onPurchasedClickListener) {
        this.onPurchasedClickListener = onPurchasedClickListener;
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
        final PurchasedProductDto buyerProductDto = mBuyerProducts.get(position);
        holder.tvtitle.setText(buyerProductDto.getTitle());
        holder.tvCategories.setText(buyerProductDto.getBuyerProductCategoriesString() + "");

        if (buyerProductDto.getSellerProducts() != null && !buyerProductDto.getSellerProducts().isEmpty()) {
            if (buyerProductDto.getSellerProducts().get(0).getSellerProductImages() != null && !buyerProductDto.getSellerProducts().get(0).getSellerProductImages().isEmpty()) {
                Picasso.with(mContext).load(buyerProductDto.getSellerProducts().get(0).getSellerProductImages().get(0)
                        .getImageName()).placeholder(R.drawable.ic_placeholder_purchase_detail).into(holder.imageProduct);
            }
            else
            {
                holder.imageProduct.setImageResource(R.drawable.ic_placeholder_purchase_detail);
            }

        } else {
            holder.imageProduct.setImageResource(R.drawable.ic_placeholder_purchase_detail);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPurchasedClickListener.onPurchaseClicked(buyerProductDto);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBuyerProducts.size();
    }


}
