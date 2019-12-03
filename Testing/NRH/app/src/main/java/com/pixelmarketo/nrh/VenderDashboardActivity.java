package com.pixelmarketo.nrh;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.pixelmarketo.nrh.Firebase.Config;
import com.pixelmarketo.nrh.activity.BidRequestActivity;
import com.pixelmarketo.nrh.activity.ContactUsActivity;
import com.pixelmarketo.nrh.activity.HelpActivity;
import com.pixelmarketo.nrh.activity.Vender_HistoryActivity;
import com.pixelmarketo.nrh.adapter.Vender_services_adapter;
import com.pixelmarketo.nrh.database.UserProfileHelper;
import com.pixelmarketo.nrh.fragment.Vender.ApprovedFragment;
import com.pixelmarketo.nrh.fragment.Vender.PendingFragment;
import com.pixelmarketo.nrh.models.Service_Models.Example;
import com.pixelmarketo.nrh.models.Service_Models.Result;
import com.pixelmarketo.nrh.utility.AppConfig;
import com.pixelmarketo.nrh.utility.ErrorMessage;
import com.pixelmarketo.nrh.utility.LoadInterface;
import com.pixelmarketo.nrh.utility.NetworkUtil;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VenderDashboardActivity extends AppCompatActivity {


    @BindView(R.id.imageView)
    CircleImageView imageView;
    @BindView(R.id.user_name_tv)
    TextView userNameTv;
    @BindView(R.id.user_email_tv)
    TextView userEmailTv;
    @BindView(R.id.select_services_tv)
    TextView selectServicesTv;
    Example example;
    @BindView(R.id.service_open_card)
    CardView serviceOpenCard;
    @BindView(R.id.service_underground_card)
    CardView serviceUndergroundCard;

    Dialog dialog;
    @BindView(R.id.view_profile_tv)
    TextView viewProfileTv;

    @BindView(R.id.logoutItemNav)
    TextView logoutItemNav;
    @BindView(R.id.facebook_img)
    ImageView facebookImg;
    @BindView(R.id.insta_img)
    ImageView instaImg;
    @BindView(R.id.gmail_img)
    ImageView gmailImg;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.history_tv)
    TextView historyTv;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.pager)
    ViewPager pager;
    String Status = "";
    Result result;
    @BindView(R.id.about_us_tv)
    TextView aboutUsTv;
    @BindView(R.id.terms_condition_Nav)
    TextView termsConditionNav;
    @BindView(R.id.contact_tv)
    TextView contactTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vender_dashboard);
        ButterKnife.bind(this);
        if (UserProfileHelper.getInstance().getUserProfileModel().size() > 0) {
            userNameTv.setText(UserProfileHelper.getInstance().getUserProfileModel().get(0).getDisplayName());
            getAllServices_according_tokan(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUid());
        }
        selectServicesTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (example.getResult().size() > 0) {
                    dialog = new Dialog(VenderDashboardActivity.this);
                    dialog.setContentView(R.layout.services_popup);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    RecyclerView recyclerView = dialog.findViewById(R.id.my_service_Rv);

                    if (example.getResult().size() > 0) {
                        Vender_services_adapter myOrderAdapter = new Vender_services_adapter(VenderDashboardActivity.this, example.getResult(), "VenderDashboard");
                        GridLayoutManager mLayoutManager = new GridLayoutManager(VenderDashboardActivity.this, 3);
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(myOrderAdapter);
                    }
                    dialog.show();
                }

            }

        });

        tabLayout.addTab(tabLayout.newTab().setText("Pending Bid List"));
        tabLayout.addTab(tabLayout.newTab().setText("Approve Bid List"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


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
                Result result = (Result) bundle.getSerializable("selected_service");
                Status = bundle.getString("status");
                //titleTxt.setText(result.getServiceName());
            } else if (bundle.getString("check").equals("notification")) {
                //titleTxt.setText(bundle.getString("selected_service"));
            } else if (bundle.getString("check").equals("NewRequest")) {
                JSONObject jsonObject = null;
                try {
                    Gson gson = new Gson();
                    jsonObject = new JSONObject(bundle.getString("message"));
                    String Allresponse = jsonObject.toString();
                    ErrorMessage.E("NewRequest Data" + Allresponse);
                    Result result = gson.fromJson(Allresponse, Result.class);
                    ErrorMessage.E("NewRequest check" + result.getServiceName());
                } catch (Exception e) {
                    e.printStackTrace();
                    ErrorMessage.E("NewRequest Exception" + e.toString());
                }

            } else if (bundle.getString("check").equals("Approve_Bid_Request")) {
                JSONObject jsonObject = null;
                try {
                    Gson gson = new Gson();
                    jsonObject = new JSONObject(bundle.getString("message"));
                    String Allresponse = jsonObject.toString();
                    ErrorMessage.E("NewRequest Data" + Allresponse);
                    Result result = gson.fromJson(Allresponse, Result.class);
                    MyTabAdapter adapter = new MyTabAdapter(VenderDashboardActivity.this, getSupportFragmentManager(), tabLayout.getTabCount(), result);
                    pager.setAdapter(adapter);
                    pager.setCurrentItem(2);
                    pager.getAdapter().notifyDataSetChanged();
                    adapter.notifyDataSetChanged();
                    ErrorMessage.E("NewRequest check" + result.getServiceName());
                } catch (Exception e) {
                    e.printStackTrace();
                    ErrorMessage.E("NewRequest Exception" + e.toString());
                }

            }
        }
        LocalBroadcastManager.getInstance(this).
                registerReceiver(AcceptedRequest, new IntentFilter(Config.NewRequest));

    }

    private BroadcastReceiver AcceptedRequest = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
               /* try {
                    ErrorMessage.E("New Request" + intent.getStringExtra("message"));
                    JSONObject jsonObject = new JSONObject(intent.getStringExtra("message"));
                    Bundle bundle = new Bundle();
                    bundle.putString("selected_service", jsonObject.getString("service"));
                    bundle.putString("selected_service_id", jsonObject.getString("service_type"));
                    bundle.putString("sub_service", jsonObject.getString("service_sub_name"));
                    bundle.putString("sub_service_id", jsonObject.getString("service_sub_type"));
                    bundle.putString("check", "notification");
                    ErrorMessage.I(VenderDashboardActivity.this, BidRequestActivity.class, bundle);
                } catch (Exception e) {
                    e.printStackTrace();
                    ErrorMessage.E("Exception iner" + e.toString());
                }*/
                try {
                    ErrorMessage.E("New Request" + intent.getStringExtra("message"));
                    JSONObject jsonObject = new JSONObject(intent.getStringExtra("message"));
                    Gson gson = new Gson();
                    String Allresponse = jsonObject.toString();
                    Result result = gson.fromJson(Allresponse, Result.class);

                } catch (Exception e) {
                    e.printStackTrace();
                    ErrorMessage.E("Exception iner" + e.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
                ErrorMessage.E("Exception outer" + e.toString());
            }


        }
    };

    private void getAllServices_according_tokan(String tokan) {
        if (NetworkUtil.isNetworkAvailable(VenderDashboardActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(VenderDashboardActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.service_by_vendor_id(tokan);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            materialDialog.dismiss();
                            Gson gson = new Gson();
                            JSONObject object = null;
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("getAllServices_according_tokan  response" + object.toString());
                            if (object.getString("status").equals("200")) {
                                String Allresponse = object.toString();
                                example = gson.fromJson(Allresponse, Example.class);

                                selectServicesTv.setText(example.getResult().get(0).getServiceName());
                                Result result = example.getResult().get(0);
                                if (example.getResult().size() > 0) {
                                    MyTabAdapter adapter = new MyTabAdapter(VenderDashboardActivity.this, getSupportFragmentManager(), tabLayout.getTabCount(), result);
                                    pager.setAdapter(adapter);
                                    pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                                    pager.getAdapter().notifyDataSetChanged();
                                    adapter.notifyDataSetChanged();
                                }

                            }
                        } catch (Exception e) {

                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("Exception" + e.toString());
                        }
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    materialDialog.dismiss();

                }
            });


        } else {
            ErrorMessage.T(VenderDashboardActivity.this, "Internet Not Found! ");
        }

    }

    public void GET_ServiceId(Result userInfo) {
        dialog.dismiss();
        selectServicesTv.setText(userInfo.getServiceName());
        result = userInfo;
        ErrorMessage.E("GET_ServiceId" + userInfo.getSubservice().size());
        MyTabAdapter adapter = new MyTabAdapter(VenderDashboardActivity.this, getSupportFragmentManager(), tabLayout.getTabCount(), result);
        pager.setAdapter(adapter);
        pager.getAdapter().notifyDataSetChanged();
        //tabLayout.setupWithViewPager(pager);
        adapter.notifyDataSetChanged();
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    @OnClick({R.id.service_open_card, R.id.service_underground_card, R.id.view_profile_tv, R.id.logoutItemNav, R.id.menu_img, R.id.history_tv, R.id.about_us_tv,R.id.terms_condition_Nav,R.id.contact_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.service_open_card:
                Result result = null;
                Bundle bundle = new Bundle();
                bundle.putSerializable("selected_service", result);
                bundle.putString("status", "0");
                bundle.putString("check", "Activity");
                ErrorMessage.I(VenderDashboardActivity.this, BidRequestActivity.class, bundle);
                break;
            case R.id.service_underground_card:
                Result result1 = null;
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("selected_service", result1);
                bundle1.putString("status", "1");
                bundle1.putString("check", "Activity");
                ErrorMessage.I(VenderDashboardActivity.this, BidRequestActivity.class, bundle1);
                break;
            case R.id.view_profile_tv:
                Bundle bundle2 = new Bundle();
                bundle2.putString("Check", "Vender");
                ErrorMessage.I(VenderDashboardActivity.this, UpdateProfileActivity.class, bundle2);
                break;
            case R.id.logoutItemNav:
                logout_PopUP();
                break;
            case R.id.menu_img:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                break;
            case R.id.history_tv:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    ErrorMessage.I(VenderDashboardActivity.this, Vender_HistoryActivity.class, null);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                break;
            case R.id.about_us_tv:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    Bundle bundle11 = new Bundle();
                    bundle11.putString("Check", "AboutUs");
                    ErrorMessage.I(VenderDashboardActivity.this, HelpActivity.class, bundle11);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                break;
            case R.id.terms_condition_Nav:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    Bundle bundle3 = new Bundle();
                    bundle3.putString("Check", "terms_condition");
                    ErrorMessage.I(VenderDashboardActivity.this, HelpActivity.class, bundle3);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                break;
            case R.id.contact_tv:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    Bundle bundle4 = new Bundle();
                    bundle4.putString("Check", "AboutUs");
                    ErrorMessage.I(VenderDashboardActivity.this, ContactUsActivity.class, bundle4);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                break;
        }
    }

    public void logout_PopUP() {
        final Dialog dialog = new Dialog(VenderDashboardActivity.this);
        dialog.setContentView(R.layout.logout_popup);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        Button submit_btn = (Button) dialog.findViewById(R.id.submit_btn);
        Button cancel_btn = (Button) dialog.findViewById(R.id.cancel_btn);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                UserProfileHelper.getInstance().delete();
                ErrorMessage.I_clear(VenderDashboardActivity.this, ServiceTypeActivity.class, null);
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public class MyTabAdapter extends FragmentStatePagerAdapter {
        private Context myContext;
        int totalTabs;
        Result result;

        public MyTabAdapter(Context context, FragmentManager fm, int totalTabs, Result result1) {
            super(fm);
            myContext = context;
            this.totalTabs = totalTabs;
            this.result = result1;
            notifyDataSetChanged();
            ErrorMessage.E("MyTabAdapter" + result.getSubservice().size());
        }

        // this is for fragment tabs
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    PendingFragment homeFragment = new PendingFragment();
                    ErrorMessage.E("MyTabAdapter getItem" + result.getSubservice().size());
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
                    notifyDataSetChanged();
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

        public int getItemPosition() {
            return POSITION_NONE;
        }
    }

    public void selectPage() {
        /*MyTabAdapter adapter = new MyTabAdapter(VenderDashboardActivity.this, getSupportFragmentManager(), tabLayout.getTabCount(),result);
        pager.setAdapter(adapter);
        pager.setCurrentItem(2);
        tabLayout.setupWithViewPager(pager);
        pager.getAdapter().notifyDataSetChanged();
        adapter.notifyDataSetChanged();
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));*/
        if (UserProfileHelper.getInstance().getUserProfileModel().size() > 0) {
            getAllServices_according_tokan(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUid());
        }
    }
}
