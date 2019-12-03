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
import com.pixelmarketo.nrh.utility.ErrorMessage;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Service_Adapter extends RecyclerView.Adapter<Service_Adapter.MyViewHolder> {
    View view;
    Context activity;
    List<Result> arrayList;


    public Service_Adapter(Context policyActivity, List<Result> arrayList) {
        this.activity = policyActivity;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Service_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_adapter, parent, false);
        return new Service_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Service_Adapter.MyViewHolder holder, final int position) {
        final Result userInfo = arrayList.get(position);
        holder.item_name.setText(userInfo.getServiceName());
        ErrorMessage.E("Servicename"+userInfo.getServiceName());
        Glide.with(activity).load(arrayList.get(position).getServiceImage()).into(holder.item_img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userInfo.getSubservice().size() > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("AllData", userInfo);
                    ErrorMessage.I(activity, TypesOfServiceActivity.class, bundle);
                } else {
                    if (userInfo.getServiceName().toLowerCase().contains("bands")) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("AllData", userInfo);
                        ErrorMessage.I(activity, TentActivity.class, bundle);
                    } else if (userInfo.getServiceName().toLowerCase().contains("lights")) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("AllData", userInfo);
                        ErrorMessage.I(activity, TentActivity.class, bundle);
                    }else if (userInfo.getServiceName().toLowerCase().contains("dj")) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("AllData", userInfo);
                        ErrorMessage.I(activity, TentActivity.class, bundle);
                    }else if (userInfo.getServiceName().toLowerCase().contains("dhol")) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("AllData", userInfo);
                        ErrorMessage.I(activity, TentActivity.class, bundle);
                    }else if (userInfo.getServiceName().toLowerCase().contains("event planner")) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("AllData", userInfo);
                        ErrorMessage.I(activity, TentActivity.class, bundle);
                    }else if (userInfo.getServiceName().toLowerCase().contains("water cane")) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("AllData", userInfo);
                        ErrorMessage.I(activity, TentActivity.class, bundle);
                    }else if (userInfo.getServiceName().toLowerCase().contains("singer/dancer")) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("AllData", userInfo);
                        ErrorMessage.I(activity, TentActivity.class, bundle);
                    }else if (userInfo.getServiceName().toLowerCase().contains("waiter")) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("AllData", userInfo);
                        ErrorMessage.I(activity, TentActivity.class, bundle);
                    }else if (userInfo.getServiceName().toLowerCase().contains("halwai")) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("AllData", userInfo);
                        ErrorMessage.I(activity, TentActivity.class, bundle);
                    }else if (userInfo.getServiceName().toLowerCase().contains("caters")) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("AllData", userInfo);
                        ErrorMessage.I(activity, Caters.class, bundle);
                    }else if (userInfo.getServiceName().toLowerCase().contains("baggi/horse")) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("AllData", userInfo);
                        ErrorMessage.I(activity, Baggi_HourseActivity.class, bundle);
                    }else if (userInfo.getServiceName().toLowerCase().contains("video grapher")) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("AllData", userInfo);
                        ErrorMessage.I(activity, VideoGrapherActivity.class, bundle);
                    }else if (userInfo.getServiceName().toLowerCase().contains("cantens")) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("AllData", userInfo);
                        ErrorMessage.I(activity, CantensActivity.class, bundle);
                    }
                    else if (userInfo.getServiceName().toLowerCase().contains("traveller")) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("AllData", userInfo);
                        ErrorMessage.I(activity, CantensActivity.class, bundle);
                    }
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
        ImageView item_img;
/* TextView bankname,cutomername,customeraddress;
        TextView call,remarks,status;*/

        public MyViewHolder(View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_name);
            item_img = itemView.findViewById(R.id.item_img);

            /*   bankname  = itemView.findViewById(R.id.bank_name);
            cutomername  = itemView.findViewById(R.id.cutomer_name);
            customeraddress  = itemView.findViewById(R.id.customer_address);
            call  = itemView.findViewById(R.id.call_now);
            remarks  = itemView.findViewById(R.id.remarks);
            status  = itemView.findViewById(R.id.status);*/
        }
    }
}
