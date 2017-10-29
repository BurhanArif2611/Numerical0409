package com.revauc.revolutionbuy.ui.notification;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ItemNotificationBinding;
import com.revauc.revolutionbuy.databinding.ItemSellerOwnOfferBinding;
import com.revauc.revolutionbuy.listeners.OnNotificationClickListener;
import com.revauc.revolutionbuy.network.response.profile.NotificationDto;
import com.revauc.revolutionbuy.network.response.seller.SellerOfferDto;
import com.revauc.revolutionbuy.util.Constants;
import com.revauc.revolutionbuy.util.Utils;
import com.revauc.revolutionbuy.widget.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;



public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private final List<NotificationDto> mNotifications;
    private final OnNotificationClickListener onNotificationClickListener;
    private Context mContext;
    private ItemNotificationBinding mBinding;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView imageProduct;
        TextView tvDesc;
        TextView tvTime;

        public MyViewHolder(View view) {
            super(view);

            tvDesc = mBinding.textDescription;
            tvTime = mBinding.textTime;
        }
    }

    public NotificationAdapter(Context mContext, List<NotificationDto> mNotifications, OnNotificationClickListener onNotificationClickListener) {
        this.onNotificationClickListener = onNotificationClickListener;
        this.mContext = mContext;
        this.mNotifications = mNotifications;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_notification, parent, false);

        return new MyViewHolder(mBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final NotificationDto notificationDto = mNotifications.get(position);
        holder.tvDesc.setText(notificationDto.getDescription());
        holder.tvTime.setText(Utils.getDuration(notificationDto.getTimestamp()*1000,mContext));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNotificationClickListener.onNotificationClicked(notificationDto);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNotifications.size();
    }


}
