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
import com.pixelmarketo.nrh.TypesOfServiceActivity;
import com.pixelmarketo.nrh.VenderDashboardActivity;
import com.pixelmarketo.nrh.Vender_RegistrationActivity;
import com.pixelmarketo.nrh.models.Service_Models.Result;
import com.pixelmarketo.nrh.utility.ErrorMessage;

import java.util.List;

public class Vender_services_adapter extends RecyclerView.Adapter<Vender_services_adapter.MyViewHolder> {
    View view;
    Context activity;
    List<Result> arrayList;
    String Check = "";

    public Vender_services_adapter(Context policyActivity, List<Result> arrayList, String Check) {
        this.activity = policyActivity;
        this.arrayList = arrayList;
        this.Check = Check;
    }

    @NonNull
    @Override
    public Vender_services_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vender_service_adapter, parent, false);
        return new Vender_services_adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vender_services_adapter.MyViewHolder holder, final int position) {
        final Result userInfo = arrayList.get(position);
        if (Check.equals("VenderDashboard")) {
            holder.item_name.setTextColor(activity.getResources().getColor(R.color.primary_text));
        }else {
            holder.item_name.setTextColor(activity.getResources().getColor(R.color.white));
        }
        holder.item_name.setText(userInfo.getServiceName());

        Glide.with(activity).load(arrayList.get(position).getServiceImage()).into(holder.item_img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Check.equals("VenderDashboard")) {
                    holder.verified_img.setVisibility(View.VISIBLE);
                    ((VenderDashboardActivity) activity).GET_ServiceId(userInfo);
                } else {
                    holder.verified_img.setVisibility(View.VISIBLE);
                    ((Vender_RegistrationActivity) activity).GET_ServiceId(userInfo.getServiceId());
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
        ImageView item_img, verified_img;


        public MyViewHolder(View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_name);
            item_img = itemView.findViewById(R.id.item_img);
            verified_img = itemView.findViewById(R.id.verified_img);
        }
    }
}
