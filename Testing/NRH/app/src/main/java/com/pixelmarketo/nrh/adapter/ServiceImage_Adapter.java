package com.pixelmarketo.nrh.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pixelmarketo.nrh.R;
import com.pixelmarketo.nrh.activity.FullScreenActivity;
import com.pixelmarketo.nrh.models.SelectImage;
import com.pixelmarketo.nrh.models.vender.service.Image;
import com.pixelmarketo.nrh.models.vender.service.Result;
import com.pixelmarketo.nrh.utility.ErrorMessage;

import java.util.List;

public class ServiceImage_Adapter extends RecyclerView.Adapter<ServiceImage_Adapter.MyViewHolder> {
    View view;
    Context context;
    List<Image> arrayList;
    RadioButton radioButton = null;
    Result result;
    public ServiceImage_Adapter(Context context, List<Image> arrayList, Result userInfo) {
        this.context = context;
        this.arrayList = arrayList;
        this.result = userInfo;
    }

    @NonNull
    @Override
    public ServiceImage_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upload_image_adapter, parent, false);
        return new ServiceImage_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ServiceImage_Adapter.MyViewHolder holder, int position) {
        //holder.item_img.setImageBitmap(arrayList.get(position).getImage());
        Image image=arrayList.get(position);
        Glide.with(context).load(arrayList.get(position).getImage()).into(holder.item_img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("ALLData", result);
                ErrorMessage.I(context, FullScreenActivity.class, bundle);

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView item_img;
        TextView userNameTItleTv, companyNameTv, contactTv, moreDetailsTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            item_img = itemView.findViewById(R.id.item_img);
           /* userNameTItleTv=itemView.findViewById(R.id.userNameTItleTv);
            companyNameTv=itemView.findViewById(R.id.companyNameTv);
            contactTv=itemView.findViewById(R.id.contactTv);
            moreDetailsTv=itemView.findViewById(R.id.moreDetailsTv);
*/
        }
    }
}
