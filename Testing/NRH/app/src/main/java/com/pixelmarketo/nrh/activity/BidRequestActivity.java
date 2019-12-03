package com.pixelmarketo.nrh.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.pixelmarketo.nrh.BaseActivity;
import com.pixelmarketo.nrh.Firebase.Config;
import com.pixelmarketo.nrh.R;
import com.pixelmarketo.nrh.VenderDashboardActivity;
import com.pixelmarketo.nrh.fragment.Vender.ApprovedFragment;
import com.pixelmarketo.nrh.fragment.Vender.PendingFragment;
import com.pixelmarketo.nrh.models.Service_Models.Example;
import com.pixelmarketo.nrh.models.Service_Models.Result;
import com.pixelmarketo.nrh.utility.ErrorMessage;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BidRequestActivity extends BaseActivity {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.no_data_found_tv)
    TextView noDataFoundTv;
    @BindView(R.id.title_txt)
    TextView titleTxt;
    Result result;
    String Status = "";

    @Override
    protected int getContentResId() {
        return R.layout.activity_bid_request;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("");
        ButterKnife.bind(this);

        tabLayout.addTab(tabLayout.newTab().setText("Pending Bid List"));
        tabLayout.addTab(tabLayout.newTab().setText("Approve Bid List"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        MyTabAdapter adapter = new MyTabAdapter(BidRequestActivity.this, getSupportFragmentManager(), tabLayout.getTabCount());
        pager.setAdapter(adapter);

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString("check").equals("Activity")) {
                result = (Result) bundle.getSerializable("selected_service");
                Status = bundle.getString("status");
                titleTxt.setText(result.getServiceName());
            }else  if (bundle.getString("check").equals("notification")) {
                titleTxt.setText(bundle.getString("selected_service"));
            }else if (bundle.getString("check").equals("NewRequest")) {
                JSONObject jsonObject = null;
                try {
                    Gson gson = new Gson();
                    jsonObject = new JSONObject(bundle.getString("message"));
                    String Allresponse = jsonObject.toString();
                    ErrorMessage.E("NewRequest Data"+Allresponse);
                    result = gson.fromJson(Allresponse, Result.class);
                    ErrorMessage.E("NewRequest check"+result.getServiceName());
                } catch (Exception e) {
                    e.printStackTrace();
                    ErrorMessage.E("NewRequest Exception"+e.toString());
                }

            }else if (bundle.getString("check").equals("Approve_Bid_Request")) {
                JSONObject jsonObject = null;
                try {
                    Gson gson = new Gson();
                    jsonObject = new JSONObject(bundle.getString("message"));
                    String Allresponse = jsonObject.toString();
                    ErrorMessage.E("NewRequest Data"+Allresponse);
                    result = gson.fromJson(Allresponse, Result.class);
                    pager.setAdapter(adapter);
                    pager.setCurrentItem(2);
                    ErrorMessage.E("NewRequest check"+result.getServiceName());
                } catch (Exception e) {
                    e.printStackTrace();
                    ErrorMessage.E("NewRequest Exception"+e.toString());
                }

            }
        }

        LocalBroadcastManager.getInstance(BidRequestActivity.this).
                registerReceiver(AcceptedRequest, new IntentFilter(Config.NewRequest));
    }
    private BroadcastReceiver AcceptedRequest = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                    ErrorMessage.E("New Request" + intent.getStringExtra("message"));
                    JSONObject jsonObject = new JSONObject(intent.getStringExtra("message"));
                    Gson gson = new Gson();
                    String Allresponse = jsonObject.toString();
                    result = gson.fromJson(Allresponse, Result.class);

                } catch (Exception e) {
                    e.printStackTrace();
                    ErrorMessage.E("Exception iner" + e.toString());
                }



        }
    };
    public void ChangeTilte(String Title) {
        titleTxt.setText(Title);
    }

    public class MyTabAdapter extends FragmentPagerAdapter {

        private Context myContext;
        int totalTabs;

        public MyTabAdapter(Context context, FragmentManager fm, int totalTabs) {
            super(fm);
            myContext = context;
            this.totalTabs = totalTabs;
        }

        // this is for fragment tabs
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    PendingFragment homeFragment = new PendingFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("result", result);
                    bundle.putString("Check", "0");
                    homeFragment.setArguments(bundle);
                    return homeFragment;
                case 1:
                    ApprovedFragment sportFragment = new ApprovedFragment();
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("result", result);
                    bundle1.putString("Check", "1");
                    sportFragment.setArguments(bundle1);
                    return sportFragment;
                default:
                    return null;
            }
        }

        // this counts total number of tabs
        @Override
        public int getCount() {
            return totalTabs;
        }
    }


}
