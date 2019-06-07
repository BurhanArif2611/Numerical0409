package com.numerical.numerical.adapters;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import com.numerical.numerical.Models.Topics.Example;
import com.numerical.numerical.R;
import com.numerical.numerical.Utility.ErrorMessage;
import com.numerical.numerical.activity.DashBoardActivity;
import com.numerical.numerical.activity.LatestFeedDetailActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class Topics_Adapter extends RecyclerView.Adapter<Topics_Adapter.ViewHolder> {
    Context context;
    JSONArray jsonArray;

    public Topics_Adapter(Context context, JSONArray jsonArray) {
        this.jsonArray = jsonArray;
        this.context = context;
        /* */
    }
    @NonNull
    @Override
    public Topics_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.topics_list_adapter, parent, false);
        Topics_Adapter.ViewHolder viewHolder = new Topics_Adapter.ViewHolder(v);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull final Topics_Adapter.ViewHolder holder, final int position) {
        Example example = null;
        try {
            //JSONArray jsonArray=new JSONArray(jsonArray);
            JSONObject jsonObject = jsonArray.getJSONObject(position);
            Glide.with(context).load(jsonObject.getString("assetPath")).into(holder.item_img);
            holder.topics_name_tv.setText(jsonObject.getString("topic"));
            holder.topic_count_tv.setText("" + jsonObject.getString("numeronCount"));

            Gson gson = new Gson();
            String responseData = jsonObject.toString();
            example = gson.fromJson(responseData, Example.class);

            Tags_Adapter tags_adapter = new Tags_Adapter(context, example.getCategories());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true);
            holder.tags_rv.setLayoutManager(linearLayoutManager);
            holder.tags_rv.setItemAnimator(new DefaultItemAnimator());
            holder.tags_rv.setNestedScrollingEnabled(false);
            holder.tags_rv.setAdapter(tags_adapter);


        } catch (Exception e) {
            e.printStackTrace();
        }


        final Example finalExample = example;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DashBoardActivity)context).Topics("Topics",finalExample.getId(),holder.topics_name_tv.getText().toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView topics_name_tv,topic_count_tv;
        ImageView item_img;
        RecyclerView tags_rv;

        public ViewHolder(View itemView) {
            super(itemView);
            item_img = (ImageView) itemView.findViewById(R.id.item_img);
            topics_name_tv = (TextView) itemView.findViewById(R.id.topics_name_tv);
            topic_count_tv = (TextView) itemView.findViewById(R.id.topic_count_tv);
            tags_rv = (RecyclerView) itemView.findViewById(R.id.tags_rv);

        }
    }

}
