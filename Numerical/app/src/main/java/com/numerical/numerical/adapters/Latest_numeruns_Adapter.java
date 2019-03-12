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
import com.numerical.numerical.Models.LatestFeed.Numeron;
import com.numerical.numerical.R;
import com.numerical.numerical.Utility.ErrorMessage;
import com.numerical.numerical.activity.LatestFeedDetailActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class Latest_numeruns_Adapter extends RecyclerView.Adapter<Latest_numeruns_Adapter.ViewHolder> {
    Context context;
    List<Numeron> stringList;




    public Latest_numeruns_Adapter(Context context, List<Numeron> numeronList) {
        this.stringList = numeronList;
        this.context = context;
        /* */
    }


    

   /* public Latest_numeruns_Adapter(Context context, List<Example> stringList) {
        this.context = context;
        this.stringList = stringList;
    }*/

    @NonNull
    @Override
    public Latest_numeruns_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.numerons_adapter, parent, false);
        Latest_numeruns_Adapter.ViewHolder viewHolder = new Latest_numeruns_Adapter.ViewHolder(v);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull final Latest_numeruns_Adapter.ViewHolder holder, final int position) {
      holder.numeral_count_tv.setText(stringList.get(position).getNumeral());
      holder.title_tv.setText(stringList.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView numeral_count_tv,title_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            numeral_count_tv = (TextView) itemView.findViewById(R.id.numeral_count_tv);
            title_tv = (TextView) itemView.findViewById(R.id.title_tv);

        }
    }

}