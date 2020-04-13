package com.pixelmarketo.mechdeals.adapters;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pixelmarketo.mechdeals.R;

import com.pixelmarketo.mechdeals.database.UserProfileHelper;
import com.pixelmarketo.mechdeals.models.ServicePackegeModel.Result;
import com.pixelmarketo.mechdeals.startup.MainActivity;
import com.pixelmarketo.mechdeals.startup.TypesOfServiceActivity;

import java.util.List;

public class ServicePackege_Adapter extends RecyclerView.Adapter<ServicePackege_Adapter.MyViewHolder> {
    View view;
    Context activity;
    List<Result> arrayList;
    RadioButton radioButton = null;


    public ServicePackege_Adapter(Context policyActivity, List<Result> arrayList) {
        this.activity = policyActivity;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ServicePackege_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_packege_adapter, parent, false);
        return new ServicePackege_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ServicePackege_Adapter.MyViewHolder holder, final int position) {
        final Result listCoupon = arrayList.get(position);
        holder.packege_name_tv.setText(listCoupon.getServiceName());
        holder.actuall_price_tv.setText("CC Price :₹ " + listCoupon.getPrice());
        holder.markete_price_tv.setText("Market :₹ " + listCoupon.getPrice());
        holder.time_tv.setText("Time :" + listCoupon.getTime());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.basic_points_tv.setText(Html.fromHtml(listCoupon.getBasicPoint(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.basic_points_tv.setText(Html.fromHtml(listCoupon.getBasicPoint()));

        }
        Glide.with(activity).load(listCoupon.getServiceImg()).into(holder.packege_img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TypesOfServiceActivity) activity).GetService_ID(listCoupon.getId());
            }
        });
        holder.info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(activity);
                dialog.setContentView(R.layout.info_layout);
                dialog.setCanceledOnTouchOutside(false);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                final TextView information_tv = (TextView) dialog.findViewById(R.id.information_tv);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    information_tv.setText(Html.fromHtml(listCoupon.getServiceDescription(), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    information_tv.setText(Html.fromHtml(listCoupon.getServiceDescription()));
                }
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView packege_img;
        TextView packege_name_tv, basic_points_tv, actuall_price_tv, markete_price_tv, time_tv;
        Button info_btn;

        public MyViewHolder(View itemView) {
            super(itemView);
            packege_img = (ImageView) itemView.findViewById(R.id.packege_img);
            packege_name_tv = (TextView) itemView.findViewById(R.id.packege_name_tv);
            basic_points_tv = (TextView) itemView.findViewById(R.id.basic_points_tv);
            actuall_price_tv = (TextView) itemView.findViewById(R.id.actuall_price_tv);
            markete_price_tv = (TextView) itemView.findViewById(R.id.markete_price_tv);
            time_tv = (TextView) itemView.findViewById(R.id.time_tv);
            info_btn = (Button) itemView.findViewById(R.id.info_btn);


        }
    }

}
