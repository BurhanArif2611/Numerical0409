package com.pixelmarketo.nrh.adapter;

import android.content.Context;
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
import com.pixelmarketo.nrh.models.SelectImage;

import java.util.List;

public class UploadImage_Adapter extends RecyclerView.Adapter<UploadImage_Adapter.MyViewHolder> {
    View view;
    Context context;
    List<SelectImage> arrayList;
    RadioButton radioButton = null;

    public UploadImage_Adapter(Context context, List<SelectImage> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public UploadImage_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.upload_image_adapter, parent, false);
        return new UploadImage_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final UploadImage_Adapter.MyViewHolder holder, int position) {
        //holder.item_img.setImageBitmap(arrayList.get(position).getImage());

        Glide.with(context).load(arrayList.get(position).getImage_path()).into(holder.item_img);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView item_img;
        TextView userNameTItleTv,companyNameTv,contactTv,moreDetailsTv;

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
