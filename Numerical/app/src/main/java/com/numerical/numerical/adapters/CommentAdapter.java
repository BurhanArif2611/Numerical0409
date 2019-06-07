package com.numerical.numerical.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
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

import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    Context context;
    JSONArray jsonArray;
    ArrayList<String> stringArrayList;
    String Name;


    public CommentAdapter(Context context, ArrayList<String> stringArrayList) {
        this.jsonArray = jsonArray;
        this.context = context;
        this.Name = Name;
        this.stringArrayList = stringArrayList;

    }




   /* public CommentAdapter(Context context, List<Example> stringList) {
        this.context = context;
        this.stringList = stringList;
    }*/

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_adapter, parent, false);
        CommentAdapter.ViewHolder viewHolder = new CommentAdapter.ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull final CommentAdapter.ViewHolder holder, final int position) {
      
    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, date_tv, placeofimage_tv;
        ImageView item_img;
        RelativeLayout main_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            item_img = (ImageView) itemView.findViewById(R.id.item_img);
            title = (TextView) itemView.findViewById(R.id.title);
            date_tv = (TextView) itemView.findViewById(R.id.date_tv);
            placeofimage_tv = (TextView) itemView.findViewById(R.id.placeofimage_tv);

        }
    }
}
