package com.pixelmarketo.nrh.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pixelmarketo.nrh.R;
import com.pixelmarketo.nrh.ServiceForms.Baggi_HourseActivity;
import com.pixelmarketo.nrh.ServiceForms.Caters;
import com.pixelmarketo.nrh.ServiceForms.TentActivity;
import com.pixelmarketo.nrh.ServiceForms.VideoGrapherActivity;
import com.pixelmarketo.nrh.TypesOfServiceActivity;
import com.pixelmarketo.nrh.activity.user.CantensActivity;
import com.pixelmarketo.nrh.models.Service_Models.Result;
import com.pixelmarketo.nrh.models.Service_Models.Subservice;
import com.pixelmarketo.nrh.utility.ErrorMessage;

import java.util.List;

public class SubService_Adapter extends RecyclerView.Adapter<SubService_Adapter.MyViewHolder> {
    View view;
    Context activity;
    List<Subservice> arrayList;
    Result result;

    public SubService_Adapter(Context policyActivity, List<Subservice> arrayList, Result result) {
        this.activity = policyActivity;
        this.arrayList = arrayList;
        this.result = result;
    }

    @NonNull
    @Override
    public SubService_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_service_item, parent, false);
        return new SubService_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubService_Adapter.MyViewHolder holder, final int position) {
        final Subservice userInfo = arrayList.get(position);
        holder.item_name.setText(userInfo.getSubServiceName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (result.getServiceName().toLowerCase().contains("baggi/hourse")) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("AllData", result);
                    bundle.putSerializable("SubService", userInfo);
                    ErrorMessage.I(activity, Baggi_HourseActivity.class, bundle);
                } else if (result.getServiceName().toLowerCase().contains("decoration")){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("AllData", result);
                    bundle.putSerializable("SubService", userInfo);
                    ErrorMessage.I(activity, TentActivity.class, bundle);
                }else if (result.getServiceName().toLowerCase().contains("band")){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("AllData", result);
                    bundle.putSerializable("SubService", userInfo);
                    ErrorMessage.I(activity, TentActivity.class, bundle);
                }else if (result.getServiceName().toLowerCase().contains("dj")){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("AllData", result);
                    bundle.putSerializable("SubService", userInfo);
                    ErrorMessage.I(activity, TentActivity.class, bundle);
                }else if (result.getServiceName().toLowerCase().contains("dhol")){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("AllData", result);
                    bundle.putSerializable("SubService", userInfo);
                    ErrorMessage.I(activity, TentActivity.class, bundle);
                }else if (result.getServiceName().toLowerCase().contains("event planer")){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("AllData", result);
                    bundle.putSerializable("SubService", userInfo);
                    ErrorMessage.I(activity, TentActivity.class, bundle);
                }else if (result.getServiceName().toLowerCase().contains("water cane")){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("AllData", result);
                    bundle.putSerializable("SubService", userInfo);
                    ErrorMessage.I(activity, TentActivity.class, bundle);
                }else if (result.getServiceName().toLowerCase().contains("waiter")){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("AllData", result);
                    bundle.putSerializable("SubService", userInfo);
                    ErrorMessage.I(activity, TentActivity.class, bundle);
                }else if (result.getServiceName().toLowerCase().contains("halwai")){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("AllData", result);
                    bundle.putSerializable("SubService", userInfo);
                    ErrorMessage.I(activity, TentActivity.class, bundle);
                }else if (result.getServiceName().toLowerCase().contains("caters")) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("AllData", userInfo);
                    ErrorMessage.I(activity, Caters.class, bundle);
                }else if (result.getServiceName().toLowerCase().contains("baggi/hourse")) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("AllData", userInfo);
                    ErrorMessage.I(activity, Baggi_HourseActivity.class, bundle);
                } else if (result.getServiceName().toLowerCase().contains("video grapher")) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("AllData", userInfo);
                    ErrorMessage.I(activity, VideoGrapherActivity.class, bundle);
                }
                else if (result.getServiceName().toLowerCase().contains("traveller")) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("AllData", result);
                    ErrorMessage.I(activity, CantensActivity.class, bundle);
                }
                else {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("AllData", result);
                    bundle.putSerializable("SubService", userInfo);
                    ErrorMessage.I(activity, TentActivity.class, bundle);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_name;

        public MyViewHolder(View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.sub_service_txt);

        }
    }
}
