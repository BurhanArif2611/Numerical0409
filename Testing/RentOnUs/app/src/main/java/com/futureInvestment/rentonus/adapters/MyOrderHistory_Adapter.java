package com.futureInvestment.rentonus.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.futureInvestment.rentonus.R;
import com.futureInvestment.rentonus.Utility.ErrorMessage;
import com.futureInvestment.rentonus.activity.MyBookingActivity;
import com.futureInvestment.rentonus.activity.OrderDetailPageActivity;
import com.futureInvestment.rentonus.models.Booking_History.Datum;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyOrderHistory_Adapter extends RecyclerView.Adapter<MyOrderHistory_Adapter.ViewHolder> {
    Context activity;
    List<Datum> results;

    public MyOrderHistory_Adapter(Context orderHistoryActivity, List<Datum> result) {
        this.results = result;
        this.activity = orderHistoryActivity;
    }

    @NonNull
    @Override
    public MyOrderHistory_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_history_recycleview, parent, false);
        MyOrderHistory_Adapter.ViewHolder viewHolder = new MyOrderHistory_Adapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderHistory_Adapter.ViewHolder holder, final int position) {
        Datum datum=results.get(position);
        MyorderAdapter side_rv_adapter = new MyorderAdapter(activity, datum.getProducts(),datum);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
        holder.my_booking_item_rv.setLayoutManager(mLayoutManager);
        holder.my_booking_item_rv.setItemAnimator(new DefaultItemAnimator());
        holder.my_booking_item_rv.setNestedScrollingEnabled(false);
        holder.my_booking_item_rv.setAdapter(side_rv_adapter);
        side_rv_adapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView my_booking_item_rv;
        public ViewHolder(View itemView) {
            super(itemView);
            my_booking_item_rv = (RecyclerView) itemView.findViewById(R.id.my_booking_item_rv);
            //mrp_cost.setPaintFlags(product_discount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }
    }
}
