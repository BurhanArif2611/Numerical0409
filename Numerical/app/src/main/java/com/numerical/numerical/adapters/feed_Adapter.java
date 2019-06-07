package com.numerical.numerical.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.numerical.numerical.Models.LatestFeed.Example;
import com.numerical.numerical.R;
import com.numerical.numerical.Utility.ErrorMessage;
import com.numerical.numerical.activity.DashBoardActivity;
import com.numerical.numerical.activity.LatestFeedDetailActivity;


import org.json.JSONArray;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;


public class feed_Adapter extends RecyclerView.Adapter<feed_Adapter.ViewHolder> {
    Context context;
    JSONArray jsonArray;
    ArrayList<String> stringArrayList;
    String Name;


    public feed_Adapter(Context context, JSONArray jsonArray, ArrayList<String> stringArrayList, String Name) {
        this.jsonArray = jsonArray;
        this.context = context;
        this.Name = Name;
        this.stringArrayList = stringArrayList;

    }


    

   /* public feed_Adapter(Context context, List<Example> stringList) {
        this.context = context;
        this.stringList = stringList;
    }*/

    @NonNull
    @Override
    public feed_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_list_adapter, parent, false);
        feed_Adapter.ViewHolder viewHolder = new feed_Adapter.ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull final feed_Adapter.ViewHolder holder, final int position) {
        Example example = null;
        try {
            //JSONArray jsonArray=new JSONArray(jsonArray);
            String response = stringArrayList.get(position);
            //JSONObject jsonObject = jsonArray.getJSONObject(position);
            JSONObject jsonObject = new JSONObject(response);
            Gson gson = new Gson();
            String responseData = jsonObject.toString();
            example = gson.fromJson(responseData, Example.class);

            SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat output = new SimpleDateFormat("dd MMM yyyy - hh:mm a");
            Date d = null;
            try {
                d = input.parse(jsonObject.getString("lastModified"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            String formatted = output.format(d);
            holder.title.setText(jsonObject.getString("title"));
            holder.date_tv.setText(formatted);
            if (example.getIsCollection()) {
                holder.item_img.setVisibility(View.VISIBLE);
                holder.placeofimage_tv.setVisibility(View.GONE);
                Glide.with(context).load(jsonObject.getString("assetPath")).into(holder.item_img);
            } else {
                holder.item_img.setVisibility(View.GONE);
                holder.placeofimage_tv.setVisibility(View.VISIBLE);
                SpannableStringBuilder builder = new SpannableStringBuilder();

                SpannableString str1= new SpannableString(example.getNumerons().get(0).getNumeral());
                str1.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.primary_text)), 0, str1.length(), 0);
                str1.setSpan(new AbsoluteSizeSpan(context.getResources().getDimensionPixelSize(R.dimen.text_size_20)), 0, str1.length(), SPAN_INCLUSIVE_INCLUSIVE); // set size
                str1.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, str1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                builder.append(str1);

                SpannableString str2= new SpannableString(" "+jsonObject.getString("title"));
                str2.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.secondary_text)), 0, str2.length(), 0);
                builder.append(str2);

                holder.placeofimage_tv.setText(example.getNumerons().get(0).getNumeral());
                if (example.getNumerons().get(0).getSubType().equals("panel-angry")) {
                    holder.placeofimage_tv.setBackgroundColor(context.getResources().getColor(R.color.panel_angry));
                } else if (example.getNumerons().get(0).getSubType().equals("panel-formal")) {
                    holder.placeofimage_tv.setBackgroundColor(context.getResources().getColor(R.color.panel_formal));
                } else if (example.getNumerons().get(0).getSubType().equals("panel-sad")) {
                    holder.placeofimage_tv.setBackgroundColor(context.getResources().getColor(R.color.panel_sad));
                } else if (example.getNumerons().get(0).getSubType().equals("panel-calm")) {
                    holder.placeofimage_tv.setBackgroundColor(context.getResources().getColor(R.color.panel_calm));
                } else if (example.getNumerons().get(0).getSubType().equals("panel-positive")) {
                    holder.placeofimage_tv.setBackgroundColor(context.getResources().getColor(R.color.panel_positive));
                } else if (example.getNumerons().get(0).getSubType().equals("panel-royal")) {
                    holder.placeofimage_tv.setBackgroundColor(context.getResources().getColor(R.color.panel_royal));
                } else if (example.getNumerons().get(0).getSubType().equals("panel-happy")) {
                    holder.placeofimage_tv.setBackgroundColor(context.getResources().getColor(R.color.panel_happy));
                }else if (example.getNumerons().get(0).getSubType().equals("Standard-IMAGE")) {
                    holder.title.setText(builder, TextView.BufferType.SPANNABLE);
                    holder.item_img.setVisibility(View.VISIBLE);
                    holder.placeofimage_tv.setVisibility(View.GONE);
                    String assetPath="";
                    JSONArray jsonArray=jsonObject.getJSONArray("numerons");
                   for (int i=0;i<jsonArray.length();i++){
                       JSONObject jsonObject1=jsonArray.getJSONObject(i);
                       assetPath=jsonObject1.getString("assetPath");
                   }
                    if (assetPath.contains("https://")) {
                        Glide.with(context).load(assetPath).into(holder.item_img);
                    } else if (assetPath.contains("http://")) {
                        Glide.with(context).load(assetPath).into(holder.item_img);
                    } else {
                        Glide.with(context).load("https://numerical.co.in" + assetPath).into(holder.item_img);
                    }
                }else if (example.getNumerons().get(0).getSubType().equals("IMAGE")) {
                    holder.item_img.setVisibility(View.VISIBLE);
                    holder.placeofimage_tv.setVisibility(View.GONE);
                    String assetPath="";
                    JSONArray jsonArray=jsonObject.getJSONArray("numerons");
                   for (int i=0;i<jsonArray.length();i++){
                       JSONObject jsonObject1=jsonArray.getJSONObject(i);
                       assetPath=jsonObject1.getString("assetPath");
                   }
                    if (assetPath.contains("https://")) {
                        Glide.with(context).load(assetPath).into(holder.item_img);
                    } else if (assetPath.contains("http://")) {
                        Glide.with(context).load(assetPath).into(holder.item_img);
                    } else {
                        Glide.with(context).load("https://numerical.co.in" + assetPath).into(holder.item_img);
                    }
                }
            }



        } catch (Exception e) {
            e.printStackTrace();
            ErrorMessage.E("Exception" + e.toString());
        }


        final Example finalExample = example;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("ItemData", finalExample);
                bundle.putString("Name", Name);
                ErrorMessage.I(context, LatestFeedDetailActivity.class, bundle);
            }
        });


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
