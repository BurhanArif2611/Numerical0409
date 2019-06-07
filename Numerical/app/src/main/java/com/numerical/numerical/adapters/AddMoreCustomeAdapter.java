package com.numerical.numerical.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.numerical.numerical.Models.LatestFeed.Example;
import com.numerical.numerical.R;
import com.numerical.numerical.Utility.ErrorMessage;
import com.numerical.numerical.activity.LatestFeedDetailActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddMoreCustomeAdapter extends RecyclerView.Adapter<AddMoreCustomeAdapter.ViewHolder> {
    Context context;
    ArrayList<String> stringArrayList;


    public AddMoreCustomeAdapter(Context context, ArrayList<String> stringArrayList) {
        this.context = context;
        this.stringArrayList = stringArrayList;
    }

    @NonNull
    @Override
    public AddMoreCustomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.addcustomelist_adapter, parent, false);
        AddMoreCustomeAdapter.ViewHolder viewHolder = new AddMoreCustomeAdapter.ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull final AddMoreCustomeAdapter.ViewHolder holder, final int position) {

    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, date_tv;


        public ViewHolder(View itemView) {
            super(itemView);
            date_tv = (TextView) itemView.findViewById(R.id.date_tv);

        }
    }

}
