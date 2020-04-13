package com.numerical.numerical.adapters;

import android.app.Activity;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.numerical.numerical.Models.LatestFeed.Numeron;
import com.numerical.numerical.R;
import com.numerical.numerical.Utility.ApiClient;
import com.numerical.numerical.Utility.Const;
import com.numerical.numerical.Utility.ErrorMessage;
import com.numerical.numerical.Utility.NetworkUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Example_Adapter extends PagerAdapter {
    Context context;
    private List<String> numeronList;
    private LayoutInflater inflater;


    public Example_Adapter(Context context, List<String> riskQuestionModelList) {
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
        View views = inflater.inflate(R.layout.example_layout, view, false);
        view.addView(views);

        return views; }
}
