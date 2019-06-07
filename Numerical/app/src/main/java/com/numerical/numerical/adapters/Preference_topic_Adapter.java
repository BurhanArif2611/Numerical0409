package com.numerical.numerical.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.numerical.numerical.Models.Topics.Example;
import com.numerical.numerical.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class Preference_topic_Adapter extends RecyclerView.Adapter<Preference_topic_Adapter.ViewHolder> {
    Context context;
    JSONArray jsonArray;

    public Preference_topic_Adapter(Context context, JSONArray jsonArray) {
        this.jsonArray = jsonArray;
        this.context = context;
        /* */
    }
    @NonNull
    @Override
    public Preference_topic_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.preference_topic_list_adapter, parent, false);
        Preference_topic_Adapter.ViewHolder viewHolder = new Preference_topic_Adapter.ViewHolder(v);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull final Preference_topic_Adapter.ViewHolder holder, final int position) {
        Example example = null;
        try {
            //JSONArray jsonArray=new JSONArray(jsonArray);
            JSONObject jsonObject = jsonArray.getJSONObject(position);
            Glide.with(context).load(jsonObject.getString("assetPath")).into(holder.item_img);
            holder.item_title_tv.setText(jsonObject.getString("topic"));
        } catch (Exception e) {
            e.printStackTrace();
        }


        
    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_title_tv;
        ImageView item_img;


        public ViewHolder(View itemView) {
            super(itemView);
            item_img = (ImageView) itemView.findViewById(R.id.item_img);
            item_title_tv = (TextView) itemView.findViewById(R.id.item_title_tv);


        }
    }
}
