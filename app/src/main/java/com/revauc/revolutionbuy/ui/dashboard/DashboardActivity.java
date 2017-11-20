package com.revauc.revolutionbuy.ui.dashboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ActivityDashboardBinding;
import com.revauc.revolutionbuy.databinding.ActivitySignUpBinding;
import com.revauc.revolutionbuy.eventbusmodel.OnSignUpClicked;
import com.revauc.revolutionbuy.network.BaseResponse;
import com.revauc.revolutionbuy.network.RequestController;
import com.revauc.revolutionbuy.network.request.auth.NotificationDetailRequest;
import com.revauc.revolutionbuy.network.response.buyer.CategoriesResponse;
import com.revauc.revolutionbuy.network.response.buyer.PurchasedProductDto;
import com.revauc.revolutionbuy.network.response.buyer.PurchasedResponse;
import com.revauc.revolutionbuy.network.response.profile.NotificationCountResponse;
import com.revauc.revolutionbuy.network.response.profile.NotificationDetailPurchaseResponse;
import com.revauc.revolutionbuy.network.response.profile.NotificationDetailResponse;
import com.revauc.revolutionbuy.network.response.profile.NotificationDetailUnlockResponse;
import com.revauc.revolutionbuy.network.response.profile.NotificationDto;
import com.revauc.revolutionbuy.network.response.seller.SellerOfferDto;
import com.revauc.revolutionbuy.network.response.seller.SellerOffersResponse;
import com.revauc.revolutionbuy.network.retrofit.AuthWebServices;
import com.revauc.revolutionbuy.network.retrofit.DefaultApiObserver;
import com.revauc.revolutionbuy.notification.NotificationPayload;
import com.revauc.revolutionbuy.ui.BaseActivity;
import com.revauc.revolutionbuy.ui.ComingSoonFragment;
import com.revauc.revolutionbuy.ui.auth.SignUpActivity;
import com.revauc.revolutionbuy.ui.buy.BuyFragment;
import com.revauc.revolutionbuy.ui.buy.BuyerProductDetailActivity;
import com.revauc.revolutionbuy.ui.buy.PurchasedItemDetailActivity;
import com.revauc.revolutionbuy.ui.buy.SelectCategoriesActivity;
import com.revauc.revolutionbuy.ui.buy.SellerOfferDetailActivity;
import com.revauc.revolutionbuy.ui.buy.SellerOffersActivity;
import com.revauc.revolutionbuy.ui.buy.adapter.CategoriesAdapter;
import com.revauc.revolutionbuy.ui.notification.NotificationsFragment;
import com.revauc.revolutionbuy.ui.sell.ReportItemActivity;
import com.revauc.revolutionbuy.ui.sell.SellFragment;
import com.revauc.revolutionbuy.ui.sell.SellerOwnOfferDetailActivity;
import com.revauc.revolutionbuy.ui.settings.SettingsFragment;
import com.revauc.revolutionbuy.util.Constants;
import com.revauc.revolutionbuy.util.LogUtils;
import com.revauc.revolutionbuy.util.PreferenceUtil;
import com.revauc.revolutionbuy.util.Utils;
import com.revauc.revolutionbuy.widget.BottomMemberAlert;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class DashboardActivity extends BaseActivity {

    private ActivityDashboardBinding mBinder;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_sell:
//                    mBinder.includeActionBar.fabButton.setVisibility(View.INVISIBLE);
//                    mBinder.includeActionBar.spinner.setVisibility(View.VISIBLE);
//                    mBinder.includeActionBar.toolbarTitle.setVisibility(View.INVISIBLE);
//
//                    if (getNavigationMode() != NAVIGATION_FEED) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content, SellFragment.newInstance())
                                .commit();
//                        setNavigationMode(NAVIGATION_FEED);
//                    }
                    break;
                case R.id.navigation_buy:
//                    mBinder.includeActionBar.fabButton.setVisibility(View.INVISIBLE);
//                    mBinder.includeActionBar.spinner.setVisibility(View.VISIBLE);
//                    mBinder.includeActionBar.toolbarTitle.setVisibility(View.INVISIBLE);
//                    if (getNavigationMode() != NAVIGATION_UPCOMING) {
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.content, UpcomingContainerFragment.newInstance("", ""))
//                                .commit();
//                        setNavigationMode(NAVIGATION_UPCOMING);
//                    }
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, BuyFragment.newInstance())
                            .commit();
                    break;

                case R.id.navigation_notifications:

//                    mBinder.includeActionBar.fabButton.setVisibility(View.VISIBLE);
//                    mBinder.includeActionBar.spinner.setVisibility(View.VISIBLE);
//                    mBinder.includeActionBar.toolbarTitle.setVisibility(View.INVISIBLE);
//                    if (getNavigationMode() != NAVIGATION_LIVE) {
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.content, LiveContainerFragment.newInstance())
//                                .commit();
//                        setNavigationMode(NAVIGATION_LIVE);
//                    }

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content, NotificationsFragment.newInstance())
                                .commit();

                    break;
                case R.id.navigation_settings:
//                    mBinder.includeActionBar.fabButton.setVisibility(View.VISIBLE);
//                    mBinder.includeActionBar.spinner.setVisibility(View.VISIBLE);
//                    mBinder.includeActionBar.toolbarTitle.setVisibility(View.INVISIBLE);
//                    if (getNavigationMode() != NAVIGATION_HISTORY) {
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.content, HistoryContainerFragment.newInstance())
//                                .commit();
//                        setNavigationMode(NAVIGATION_HISTORY);
//                    }
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, SettingsFragment.newInstance())
                            .commit();

                    break;
            }

            // mSpinnerAdapter.notifyDataSetChanged();
            //sendNavigationDetails();
            return true;
        }

    };
    private NotificationPayload mNotificationPayload;
    private boolean isFetching;
    private Integer mBadgeCount;
    private TextView tvBadgeCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);

        mNotificationPayload = getIntent().getParcelableExtra(Constants.EXTRA_NOTIFICATION);

        prepareViews();

    }

    @Override
    protected void onStart() {
        super.onStart();
        getBadgeCount();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        mNotificationPayload = intent.getParcelableExtra(Constants.EXTRA_NOTIFICATION);
        if(mNotificationPayload!=null)
        {
            handleNotificationNavigation();
        }
    }

    private void prepareViews() {

        if(getIntent().getBooleanExtra(Constants.EXTRA_IS_FROM_PROFILE,false))
        {
            showSnackBarFromBottom(getString(R.string.profile_has_been_setup),mBinder.mainContainer,false);
        }

        mBinder.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mBinder.navigation.enableShiftingMode(false);
        mBinder.navigation.enableItemShiftingMode(false);
        mBinder.navigation.setTextVisibility(true);
        mBinder.navigation.enableAnimation(false);

        mBinder.navigation.setTypeface(Typeface.createFromAsset(
                getAssets(),
                getString(R.string.font_avenir_regular)));
        mBinder.navigation.setTextSize(10);
        mBinder.navigation.setItemTextColor(Utils.getTabTextColors(this));
        mBinder.navigation.setItemIconTintList(Utils.getTabTextColors(this));

        mBinder.navigation.setIconSize(getResources().getInteger(R.integer.tab_icon_size), getResources().getInteger(R.integer.tab_icon_size));


        mBinder.navigation.setCurrentItem(0);

        if(mNotificationPayload!=null)
        {
            if(mNotificationPayload.getBadge()!=null && !mNotificationPayload.getBadge().equalsIgnoreCase("0"))
            {
                BottomNavigationMenuView bottomNavigationMenuView =
                        (BottomNavigationMenuView) mBinder.navigation.getChildAt(0);
                View v = bottomNavigationMenuView.getChildAt(2);
                BottomNavigationItemView itemView = (BottomNavigationItemView) v;

                View badge = LayoutInflater.from(this)
                        .inflate(R.layout.layout_badge, bottomNavigationMenuView, false);
                tvBadgeCount = (TextView)badge.findViewById(R.id.notifications_badge);
                tvBadgeCount.setText(mNotificationPayload.getBadge());
                itemView.addView(badge);
            }
            handleNotificationNavigation();
        }

    }

    private void handleNotificationNavigation() {
        fetchNotificationDetail(mNotificationPayload.getId(),mNotificationPayload.getType());
    }

    private void fetchNotificationDetail(int notificationId, final int type)
    {
        if (isFetching) {
            return;
        }

        showProgressBar();

        isFetching = true;
        AuthWebServices apiService = RequestController.createRetrofitRequest(false);


        if(type==Constants.TYPE_SELLER_MARKED_COMPLETE)
        {

            apiService.getNotificationDetailForPurchase(new NotificationDetailRequest(notificationId)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<NotificationDetailPurchaseResponse>(DashboardActivity.this) {

                @Override
                public void onResponse(NotificationDetailPurchaseResponse response) {
                    isFetching = false;
                    hideProgressBar();
                    if (response != null && response.isSuccess()) {
                        if(response.getResult()!=null)
                        {
                            if(response.getResult().getPurchasedProduct()!=null)
                            {
                                Intent intent = new Intent(DashboardActivity.this,PurchasedItemDetailActivity.class);
                                intent.putExtra(Constants.EXTRA_PRODUCT_DETAIL,response.getResult().getPurchasedProduct());
                                startActivity(intent);
                            }
                            else
                            {
                                showToast(response.getMessage());
                            }
                        }
                    } else {
                        showToast(response.getMessage());
//                    showSnackBarFromBottom(response.getMessage(), mBinding.mainContainer, true);
                    }
                }

                @Override
                public void onError(Throwable call, BaseResponse baseResponse) {
                    hideProgressBar();
                    isFetching = false;
                    if (baseResponse != null) {
                        String errorMessage = baseResponse.getMessage();
                        showToast(errorMessage);
//                    Utils.showSnackbar(errorMessage, mBinder.mainContainer, true);
                    }
                }
            });
        }
        else if(type==Constants.TYPE_BUYER_UNLOCKED)
        {

            apiService.getNotificationDetailForUnlock(new NotificationDetailRequest(notificationId)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<NotificationDetailUnlockResponse>(this) {

                @Override
                public void onResponse(NotificationDetailUnlockResponse response) {
                    isFetching = false;
                    hideProgressBar();
                    if (response != null && response.isSuccess()) {
                        if(response.getResult()!=null)
                        {
                            if(response.getResult().getBuyerProduct()!=null)
                            {
                                Intent intent = new Intent(DashboardActivity.this,BuyerProductDetailActivity.class);
                                intent.putExtra(Constants.EXTRA_PRODUCT_DETAIL,response.getResult().getBuyerProduct());
                                startActivity(intent);
                            }
                            else
                            {
                                showToast(response.getMessage());
                            }
                        }
                    } else {
                        showToast(response.getMessage());
//                    showSnackBarFromBottom(response.getMessage(), mBinding.mainContainer, true);
                    }
                }

                @Override
                public void onError(Throwable call, BaseResponse baseResponse) {
                    hideProgressBar();
                    isFetching = false;
                    if (baseResponse != null) {
                        String errorMessage = baseResponse.getMessage();
                        showToast(errorMessage);
//                    Utils.showSnackbar(errorMessage, mBinder.mainContainer, true);
                    }
                }
            });
        }
        else
        {
            apiService.getNotificationDetail(new NotificationDetailRequest(notificationId)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<NotificationDetailResponse>(DashboardActivity.this) {

                @Override
                public void onResponse(NotificationDetailResponse response) {
                    hideProgressBar();
                    isFetching = false;
                    if (response != null && response.isSuccess()) {
                        if(response.getResult()!=null)
                        {
                            switch (type)
                            {
                                case Constants.TYPE_OFFER_SENT:
                                    if(response.getResult().getSellerProduct()!=null)
                                    {
                                        Intent intent = new Intent(DashboardActivity.this,SellerOfferDetailActivity.class);
                                        intent.putExtra(Constants.EXTRA_PRODUCT_DETAIL,response.getResult().getSellerProduct());
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        showToast(response.getMessage());
                                    }
                                    break;

                                case Constants.TYPE_BUYER_MARKED_COMPLETE:
                                    if(response.getResult().getSellerProduct()!=null)
                                    {
                                        Intent intent = new Intent(DashboardActivity.this,SellerOwnOfferDetailActivity.class);
                                        intent.putExtra(Constants.EXTRA_PRODUCT_DETAIL,response.getResult().getSellerProduct());
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        showToast(response.getMessage());
                                    }
                                    break;
                                case Constants.TYPE_PRODUCT_SOLD_BY_ANOTHER:
                                    if(response.getResult().getSellerProduct()!=null)
                                    {
                                        Intent intent = new Intent(DashboardActivity.this,SellerOwnOfferDetailActivity.class);
                                        intent.putExtra(Constants.EXTRA_PRODUCT_DETAIL,response.getResult().getSellerProduct());
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        showToast(response.getMessage());
                                    }
                                    break;
                            }

                        }
                    } else {
                        showToast(response.getMessage());
//                    showSnackBarFromBottom(response.getMessage(), mBinding.mainContainer, true);
                    }
                }

                @Override
                public void onError(Throwable call, BaseResponse baseResponse) {
                    hideProgressBar();
                    isFetching = false;
                    if (baseResponse != null) {
                        String errorMessage = baseResponse.getMessage();
                        showToast(errorMessage);
//                    Utils.showSnackbar(errorMessage, mBinder.mainContainer, true);
                    }
                }
            });
        }
    }

    private void getBadgeCount() {
        AuthWebServices apiService = RequestController.createRetrofitRequest(false);

        apiService.getUnreadNotificationsCount().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<NotificationCountResponse>(this) {

            @Override
            public void onResponse(NotificationCountResponse response) {

                if (response != null && response.isSuccess()) {
                    mBadgeCount = response.getResult().getCount();
                    if(mBadgeCount>0)
                    {
                        BottomNavigationMenuView bottomNavigationMenuView =
                                (BottomNavigationMenuView) mBinder.navigation.getChildAt(0);
                        View v = bottomNavigationMenuView.getChildAt(2);
                        BottomNavigationItemView itemView = (BottomNavigationItemView) v;

                        View badge = LayoutInflater.from(DashboardActivity.this)
                                .inflate(R.layout.layout_badge, bottomNavigationMenuView, false);
                        tvBadgeCount = (TextView)badge.findViewById(R.id.notifications_badge);
                        tvBadgeCount.setGravity(Gravity.CENTER);
                        tvBadgeCount.setText(""+mBadgeCount);
                        itemView.addView(badge);
                    }
                }

            }

            @Override
            public void onError(Throwable call, BaseResponse baseResponse) {
                if (baseResponse != null) {

                }
            }
        });
    }

    public void updateBadgeCount(int badgeCount)
    {
        mBadgeCount = badgeCount;
        if(tvBadgeCount==null)
        {
            if(mBadgeCount>0)
            {
                BottomNavigationMenuView bottomNavigationMenuView =
                        (BottomNavigationMenuView) mBinder.navigation.getChildAt(0);
                View v = bottomNavigationMenuView.getChildAt(2);
                BottomNavigationItemView itemView = (BottomNavigationItemView) v;

                View badge = LayoutInflater.from(DashboardActivity.this)
                        .inflate(R.layout.layout_badge, bottomNavigationMenuView, false);
                tvBadgeCount = (TextView)badge.findViewById(R.id.notifications_badge);
                tvBadgeCount.setGravity(Gravity.CENTER);
                tvBadgeCount.setText(""+mBadgeCount);
                itemView.addView(badge);
            }
        }
        else
        {
            if(mBadgeCount>0)
            {
                tvBadgeCount.setText(""+mBadgeCount);
            }
            else
            {
                tvBadgeCount.setText(""+mBadgeCount);
                tvBadgeCount.setVisibility(View.INVISIBLE);
            }

        }

    }


}