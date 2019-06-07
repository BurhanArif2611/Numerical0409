package com.numerical.numerical.adapters;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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
import com.numerical.numerical.activity.DashBoardActivity;
import com.numerical.numerical.activity.LatestFeedDetailActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class Tags_Adapter extends RecyclerView.Adapter<Tags_Adapter.ViewHolder> {
    Context context;
    List<String> stringList;
    String Department_id = "";
    JSONArray jsonArray;


    public Tags_Adapter(Context context, List<String> stringList) {
        this.stringList = stringList;
        this.context = context;
        /* */
    }


    

   /* public Tags_Adapter(Context context, List<Example> stringList) {
        this.context = context;
        this.stringList = stringList;
    }*/

    @NonNull
    @Override
    public Tags_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tags_list_adapter, parent, false);
        Tags_Adapter.ViewHolder viewHolder = new Tags_Adapter.ViewHolder(v);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull final Tags_Adapter.ViewHolder holder, final int position) {
        holder.tags_tv.setText(stringList.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("Calling", "Tags");
                bundle.putString("Id", stringList.get(position));
                bundle.putString("Name", stringList.get(position));
                ErrorMessage.I(context, DashBoardActivity.class, bundle);

            }
        });
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tags_tv;


        public ViewHolder(View itemView) {
            super(itemView);
            tags_tv = (TextView) itemView.findViewById(R.id.tags_tv);

        }
    }

}
