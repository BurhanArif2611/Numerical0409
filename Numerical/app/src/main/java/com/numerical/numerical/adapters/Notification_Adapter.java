package com.numerical.numerical.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.numerical.numerical.Models.LatestFeed.Numeron;
import com.numerical.numerical.R;
import com.numerical.numerical.activity.LatestFeedDetailActivity;

import java.util.List;

public class Notification_Adapter extends RecyclerView.Adapter<Notification_Adapter.ViewHolder> {
    Context context;
    List<String> stringList;


    public Notification_Adapter(Context context, List<String> numeronList) {
        this.stringList = numeronList;
        this.context = context;

    }
    @NonNull
    @Override
    public Notification_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_adapter, parent, false);
        Notification_Adapter.ViewHolder viewHolder = new Notification_Adapter.ViewHolder(v);
        return viewHolder;
    }

   
    @Override
    public void onBindViewHolder(@NonNull final Notification_Adapter.ViewHolder holder, final int position) {
       

    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView numeral_tv, title_tv, maintitle_tv;
        WebView webview;
        LinearLayout main_layout;
        ImageView numerical_Image;

        public ViewHolder(View itemView) {
            super(itemView);

           /* webview = (WebView) itemView.findViewById(R.id.webview);
            main_layout = (LinearLayout) itemView.findViewById(R.id.main_layout);
            numerical_Image = (ImageView) itemView.findViewById(R.id.numerical_Image);
            numeral_tv = (TextView) itemView.findViewById(R.id.numeral_tv);
            title_tv = (TextView) itemView.findViewById(R.id.title_tv);*/


        }
    }

}
