package com.pixelmarketo.nrh;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.pixelmarketo.nrh.Firebase.Config;
import com.pixelmarketo.nrh.activity.ContactUsActivity;
import com.pixelmarketo.nrh.activity.HelpActivity;
import com.pixelmarketo.nrh.activity.user.HistoryServiceActivity;
import com.pixelmarketo.nrh.adapter.Service_Adapter;
import com.pixelmarketo.nrh.database.UserProfileHelper;
import com.pixelmarketo.nrh.models.ProductImageAdapter;
import com.pixelmarketo.nrh.models.Service_Models.Example;
import com.pixelmarketo.nrh.utility.AppConfig;
import com.pixelmarketo.nrh.utility.ErrorMessage;
import com.pixelmarketo.nrh.utility.LoadInterface;
import com.pixelmarketo.nrh.utility.NetworkUtil;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDashboardActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.logoutItemNav)
    TextView logoutItemNav;
    @BindView(R.id.view_profile_tv)
    TextView viewProfileTv;
    @BindView(R.id.imageView)
    CircleImageView imageView;
    @BindView(R.id.user_name_tv)
    TextView userNameTv;
    @BindView(R.id.user_email_tv)
    TextView userEmailTv;
    @BindView(R.id.myOrderRv)
    RecyclerView my_services;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    @BindView(R.id.menu_img)
    ImageView menuImg;
    @BindView(R.id.history_tv)
    TextView historyTv;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.indicator)
    CircleIndicator indicator;
    @BindView(R.id.about_us_tv)
    TextView aboutUsTv;
    @BindView(R.id.terms_condition_Nav)
    TextView termsConditionNav;
    @BindView(R.id.contact_tv)
    TextView contactTv;
    private AppBarConfiguration mAppBarConfiguration;
    DrawerLayout drawer;
    private static int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        ButterKnife.bind(this);
        swiperefresh.setOnRefreshListener(UserDashboardActivity.this);
        swiperefresh.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark, android.R.color.holo_orange_dark, android.R.color.holo_blue_dark);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
        drawer = findViewById(R.id.drawer_layout);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /*mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_tools, R.id.nav_share, R.id.nav_send).setDrawerLayout(drawer).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);*/
        /* NavigationUI.setupWithNavController(navigationView, navController);*/
        if (UserProfileHelper.getInstance().getUserProfileModel().size() > 0) {
            userNameTv.setText(UserProfileHelper.getInstance().getUserProfileModel().get(0).getDisplayName());
            userEmailTv.setText(UserProfileHelper.getInstance().getUserProfileModel().get(0).getEmaiiId());
            if (!UserProfileHelper.getInstance().getUserProfileModel().get(0).getProfile_pic().equals("")) {
                Glide.with(UserDashboardActivity.this).load(UserProfileHelper.getInstance().getUserProfileModel().get(0).getProfile_pic()).placeholder(R.drawable.ic_defult_user).into(imageView);
            }
        }
        getAllServices();
        LocalBroadcastManager.getInstance(this).
                registerReceiver(New_Bid_Request, new IntentFilter(Config.New_Bid_Request));


    }

    private BroadcastReceiver New_Bid_Request = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                try {
                    ErrorMessage.E("New Request" + intent.getStringExtra("message"));
                    JSONObject jsonObject = new JSONObject(intent.getStringExtra("message"));
                    Bundle bundle = new Bundle();
                    bundle.putString("service", jsonObject.getString("service"));
                    bundle.putString("service_id", jsonObject.getString("service_id"));
                    bundle.putString("Check", "UserDashboardPage");
                    ErrorMessage.I(UserDashboardActivity.this, HistoryServiceActivity.class, bundle);

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

    public void logout_PopUP() {
        final Dialog dialog = new Dialog(UserDashboardActivity.this);
        dialog.setContentView(R.layout.logout_popup);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Button submit_btn = (Button) dialog.findViewById(R.id.submit_btn);
        Button cancel_btn = (Button) dialog.findViewById(R.id.cancel_btn);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                UserProfileHelper.getInstance().delete();
                ErrorMessage.I_clear(UserDashboardActivity.this, ServiceTypeActivity.class, null);
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @OnClick({R.id.view_profile_tv, R.id.logoutItemNav, R.id.menu_img, R.id.history_tv, R.id.about_us_tv,R.id.terms_condition_Nav,R.id.contact_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_profile_tv:
                ErrorMessage.I(UserDashboardActivity.this, UpdateProfileActivity.class, null);
                break;
            case R.id.logoutItemNav:
                logout_PopUP();
                break;
            case R.id.menu_img:
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
                break;
            case R.id.history_tv:
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                    ErrorMessage.I(UserDashboardActivity.this, HistoryServiceActivity.class, null);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
                break;
            case R.id.about_us_tv:
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                    Bundle bundle = new Bundle();
                    bundle.putString("Check", "AboutUs");
                    ErrorMessage.I(UserDashboardActivity.this, HelpActivity.class, bundle);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
                break;
            case R.id.terms_condition_Nav:
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                    Bundle bundle = new Bundle();
                    bundle.putString("Check", "terms_condition");
                    ErrorMessage.I(UserDashboardActivity.this, HelpActivity.class, bundle);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
                break;
            case R.id.contact_tv:
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                    Bundle bundle = new Bundle();
                    bundle.putString("Check", "AboutUs");
                    ErrorMessage.I(UserDashboardActivity.this, ContactUsActivity.class, bundle);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
                break;
        }
    }

    private void getAllServices() {
        if (NetworkUtil.isNetworkAvailable(UserDashboardActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(UserDashboardActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.ServiceList();
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            materialDialog.dismiss();
                            Gson gson = new Gson();
                            JSONObject object = null;
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("getAllOrders  response" + object.toString());
                            if (object.getString("status").equals("200")) {
                                swiperefresh.setRefreshing(false);
                                String Allresponse = object.toString();
                                Example example = gson.fromJson(Allresponse, Example.class);

                                if (example.getResult().size() > 0) {
                                    Service_Adapter myOrderAdapter = new Service_Adapter(UserDashboardActivity.this, example.getResult());
                                    LinearLayoutManager mLayoutManager = new GridLayoutManager(UserDashboardActivity.this, 3);
                                    my_services.setLayoutManager(mLayoutManager);
                                    my_services.setItemAnimator(new DefaultItemAnimator());
                                    my_services.setAdapter(myOrderAdapter);
                                }
                                if (example.getAdvertisement().size() > 0) {
                                    if (example.getAdvertisement().size() > 0) {
                                        ProductImageAdapter guideAdapter = new ProductImageAdapter(UserDashboardActivity.this, example.getAdvertisement());
                                        pager.setAdapter(guideAdapter);
                                        pager.setOffscreenPageLimit(example.getAdvertisement().size());
                                        //pager.setPageTransformer(false, new FadePageTransformer());ZoomOutSlideTransformer
                                        // pager.setPageTransformer(false, new ZoomOutSlideTransformer());
                                        if (example.getAdvertisement().size() > 1) {
                                            indicator.setViewPager(pager);
                                        }
                                        final Handler handler = new Handler();

                                        final Runnable update = new Runnable() {
                                            public void run() {
                                                /*pager.setCurrentItem(currentPage, true);*/
                                                if (currentPage == Integer.MAX_VALUE) {
                                                    currentPage = 0;
                                                }
                                                pager.setCurrentItem(currentPage++, true);
                                            }
                                        };
                                        Timer timer = new Timer();
                                        timer.schedule(new TimerTask() {

                                            @Override
                                            public void run() {
                                                handler.post(update);
                                            }
                                        }, 5000, 5000);
                                    }
                                    indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                                        @Override
                                        public void onPageSelected(int position) {
                                            currentPage = position;
                                        }

                                        @Override
                                        public void onPageScrolled(int pos, float arg1, int arg2) {

                                        }

                                        @Override
                                        public void onPageScrollStateChanged(int pos) {

                                        }
                                    });

                                }

                            }
                        } catch (Exception e) {
                            swiperefresh.setRefreshing(false);
                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("Exception" + e.toString());
                        }
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    materialDialog.dismiss();
                    swiperefresh.setRefreshing(false);
                }
            });


        } else {
            swiperefresh.setRefreshing(false);
            ErrorMessage.T(UserDashboardActivity.this, "Internet Not Found! ");
        }

    }

    @Override
    public void onRefresh() {
        getAllServices();
    }
}
