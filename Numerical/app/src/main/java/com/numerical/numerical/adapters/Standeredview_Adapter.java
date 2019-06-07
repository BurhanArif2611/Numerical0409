package com.numerical.numerical.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.numerical.numerical.Models.LatestFeed.Numeron;
import com.numerical.numerical.R;
import com.numerical.numerical.Utility.ApiClient;
import com.numerical.numerical.Utility.Const;
import com.numerical.numerical.Utility.ErrorMessage;
import com.numerical.numerical.Utility.NetworkUtil;
import com.numerical.numerical.activity.LatestFeedDetailActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Standeredview_Adapter extends PagerAdapter {
    Context context;
    private List<Numeron> numeronList;
    private LayoutInflater inflater;
    private TextView likecountTv;

    public Standeredview_Adapter(Context context, List<Numeron> riskQuestionModelList) {
        this.context = context;
        this.numeronList = riskQuestionModelList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return numeronList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View views = inflater.inflate(R.layout.standerdview_adapter, view, false);
        ImageView item_img = (ImageView) views.findViewById(R.id.item_img);
        TextView dateTv = (TextView) views.findViewById(R.id.date_tv);
        likecountTv = (TextView) views.findViewById(R.id.likecount_tv);
        TextView viewTv = (TextView) views.findViewById(R.id.view_tv);
        TextView commentTv = (TextView) views.findViewById(R.id.comment_tv);
        TextView sourceTv = (TextView) views.findViewById(R.id.source_tv);
        TextView placeofimage_tv = (TextView) views.findViewById(R.id.placeofimage_tv);
        TextView numeral_tv = (TextView) views.findViewById(R.id.numeral_tv);
        TextView numeral_detail_tv = (TextView) views.findViewById(R.id.numeral_detail_tv);
        RecyclerView tagsRv = (RecyclerView) views.findViewById(R.id.tags_rv);
        ErrorMessage.E("assetpath" + numeronList.get(position).getAssetPath());
        try {
            if (numeronList.get(position).getAssetPath() == null) {
                placeofimage_tv.setVisibility(View.VISIBLE);
                numeral_tv.setVisibility(View.GONE);
                item_img.setVisibility(View.GONE);
                placeofimage_tv.setText(numeronList.get(position).getNumeral());
                if (numeronList.get(position).getSubType().equals("panel-angry")) {
                    placeofimage_tv.setBackgroundColor(context.getResources().getColor(R.color.panel_angry));
                } else if (numeronList.get(position).getSubType().equals("panel-formal")) {
                    placeofimage_tv.setBackgroundColor(context.getResources().getColor(R.color.panel_formal));
                } else if (numeronList.get(position).getSubType().equals("panel-sad")) {
                    placeofimage_tv.setBackgroundColor(context.getResources().getColor(R.color.panel_sad));
                } else if (numeronList.get(position).getSubType().equals("panel-calm")) {
                    placeofimage_tv.setBackgroundColor(context.getResources().getColor(R.color.panel_calm));
                } else if (numeronList.get(position).getSubType().equals("panel-positive")) {
                    placeofimage_tv.setBackgroundColor(context.getResources().getColor(R.color.panel_positive));
                } else if (numeronList.get(position).getSubType().equals("panel-royal")) {
                    placeofimage_tv.setBackgroundColor(context.getResources().getColor(R.color.panel_royal));
                } else if (numeronList.get(position).getSubType().equals("panel-happy")) {
                    placeofimage_tv.setBackgroundColor(context.getResources().getColor(R.color.panel_happy));
                }
            } else {
                placeofimage_tv.setVisibility(View.GONE);
                item_img.setVisibility(View.VISIBLE);
                if (numeronList.get(position).getAssetPath().contains("https://")) {
                    Glide.with(context).load(numeronList.get(position).getAssetPath()).into(item_img);
                } else if (numeronList.get(position).getAssetPath().contains("http://")) {
                    Glide.with(context).load(numeronList.get(position).getAssetPath()).into(item_img);
                } else {
                    Glide.with(context).load("https://numerical.co.in" + numeronList.get(position).getAssetPath()).into(item_img);
                }
            }
            viewTv.setText("0");
            sourceTv.setText(numeronList.get(position).getSource());
            numeral_tv.setText(numeronList.get(position).getNumeral());
            numeral_detail_tv.setText(numeronList.get(position).getTitle());
            ErrorMessage.E("Likes" + numeronList.get(position).getLikes());
            if (numeronList.get(position).getLikes() == null) {
                likecountTv.setText("0");
            } else {
                likecountTv.setText("" + numeronList.get(position).getLikes());
            }
            commentTv.setText("0");

            SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat output = new SimpleDateFormat("dd MMM yyyy - hh:mm a");
            Date d = null;
            try {
                d = input.parse(numeronList.get(position).getLastModified());
            } catch (Exception e) {
                e.printStackTrace();
            }
            String formatted = output.format(d);
            dateTv.setText("Last Modified: " + formatted);

            Tags_Adapter tags_adapter = new Tags_Adapter(context, numeronList.get(position).getCategories());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            tagsRv.setLayoutManager(linearLayoutManager);
            tagsRv.setItemAnimator(new DefaultItemAnimator());
            tagsRv.setAdapter(tags_adapter);
            tags_adapter.notifyDataSetChanged();
            tagsRv.smoothScrollToPosition(1);

            sourceTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                    browserIntent.setData(Uri.parse(numeronList.get(position).getUrl()));
                    context.startActivity(browserIntent);
                }
            });
            view.addView(views);
        } catch (Exception e) {
            ErrorMessage.E("Exception" + e.toString());
        }
        LocalBroadcastManager.getInstance(context).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, Intent intent) {
                String message = intent.getStringExtra("message");
                String Position = String.valueOf(position + 1);
                ErrorMessage.E("StandardAdapter" + intent.getStringExtra("position") + ">>>" + Position);
                if (intent.getStringExtra("position").equals(Position)) {
                    if (NetworkUtil.isNetworkAvailable(context)) {
                        Call<ResponseBody> call = ApiClient.getLoadInterface().LikeNumerouns(Const.ENDPOINT.LikeNumerouns + message + "/numeron/" + numeronList.get(position).getId() + "/like");
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                ErrorMessage.E("error code" + response.code());
                                if (response.isSuccessful()) {
                                    try {
                                        int Count = 0;
                                        if (response.code() == 200) {
                                            Count = Integer.parseInt(likecountTv.getText().toString());
                                            Count++;
                                            ErrorMessage.E("Count" + Count);
                                            likecountTv.setText("" + Count);

                                        } else {

                                        }
                                    } catch (Exception e) {
                                        ErrorMessage.E("Standeredview" + e.toString());
                                    }
                                } else {
                                    //ErrorMessage.T(context, "Response not successful");

                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                ErrorMessage.T(context, "Response Fail");
                                System.out.println("============update profile fail  :" + t.toString());

                            }
                        });

                    } else {
                        ErrorMessage.T(context, context.getString(R.string.no_internet));
                    }
                }
            }
        }, new IntentFilter("StanderdView"));

        commentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LatestFeedDetailActivity) context).CallCommentScreen(numeronList.get(position).getId());
            }
        });
        return views;

    }
}
