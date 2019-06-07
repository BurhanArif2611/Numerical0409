package com.numerical.numerical.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.numerical.numerical.R;
import com.numerical.numerical.adapters.Latest_numeruns_Adapter;
import com.numerical.numerical.adapters.Notification_Adapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationActivity extends BaseActivity {

    @BindView(R.id.notification_list_rv)
    RecyclerView notificationListRv;
    ArrayList<String> stringArrayList = new ArrayList<>();

    @Override
    protected int getContentResId() {
        return R.layout.activity_notification;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("NOTIFICATION");
        ButterKnife.bind(this);
        stringArrayList.add("");
        stringArrayList.add("");
        stringArrayList.add("");
        stringArrayList.add("");
        stringArrayList.add("");
        stringArrayList.add("");
        Notification_Adapter side_rv_adapter = new Notification_Adapter(NotificationActivity.this, stringArrayList);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(NotificationActivity.this);
        notificationListRv.setLayoutManager(gridLayoutManager);
        notificationListRv.setItemAnimator(new DefaultItemAnimator());
        notificationListRv.setNestedScrollingEnabled(false);
        notificationListRv.setAdapter(side_rv_adapter);

    }
}
