package com.revauc.revolutionbuy.ui.dashboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
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
import com.revauc.revolutionbuy.network.response.buyer.PurchasedProductDto;
import com.revauc.revolutionbuy.network.response.buyer.PurchasedResponse;
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
import com.revauc.revolutionbuy.ui.buy.PurchasedItemDetailActivity;
import com.revauc.revolutionbuy.ui.buy.SellerOffersActivity;
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


public class DashboardActivity extends BaseActivity implements View.OnClickListener {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);

        mNotificationPayload = getIntent().getParcelableExtra(Constants.EXTRA_NOTIFICATION);

        prepareViews();

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
            handleNotificationNavigation();
        }
    }

    private void handleNotificationNavigation() {
        switch (mNotificationPayload.getType())
        {
            case Constants.TYPE_OFFER_SENT:
                Intent intent = new Intent(this,SellerOffersActivity.class);
                intent.putExtra(Constants.EXTRA_PRODUCT_ID,mNotificationPayload.getBuyerProductId());
                startActivity(intent);
                break;
            case Constants.TYPE_BUYER_UNLOCKED:
                fetchCurrentOffers(1);
                break;
            case Constants.TYPE_BUYER_MARKED_COMPLETE:
                fetchCurrentOffers(1);
                break;
            case Constants.TYPE_PRODUCT_SOLD_BY_ANOTHER:
                fetchCurrentOffers(1);
                break;
            case Constants.TYPE_SELLER_MARKED_COMPLETE:
                fetchPurchasedItems();
                break;
        }
    }

    private void fetchCurrentOffers(int type) {

        if (isFetching) {
            return;
        }

        showProgressBar();

        isFetching = true;
        AuthWebServices apiService = RequestController.createRetrofitRequest(false);

        apiService.getSellerOffers(type).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<SellerOffersResponse>(this) {

            @Override
            public void onResponse(SellerOffersResponse response) {
                hideProgressBar();
                if (response != null && response.isSuccess()) {
                    if(response.getResult()!=null && response.getResult().getSellerProduct()!=null)
                    {
                        for(SellerOfferDto sellerOfferDto:response.getResult().getSellerProduct())
                        {
                            if(sellerOfferDto.getId()==Integer.parseInt(mNotificationPayload.getSellerProductId()))
                            {
                                Intent intent = new Intent(DashboardActivity.this,SellerOwnOfferDetailActivity.class);
                                intent.putExtra(Constants.EXTRA_PRODUCT_DETAIL,sellerOfferDto);
                                startActivity(intent);
                                break;
                            }
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
                if (baseResponse != null) {
                    String errorMessage = baseResponse.getMessage();
                    showToast(errorMessage);
//                    Utils.showSnackbar(errorMessage, mBinder.mainContainer, true);
                }
            }
        });
    }

    private void fetchPurchasedItems() {

        showProgressBar();

        if (isFetching) {
            return;
        }

        isFetching = true;
        AuthWebServices apiService = RequestController.createRetrofitRequest(false);

        apiService.getBuyerPurchasedList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<PurchasedResponse>(this) {

            @Override
            public void onResponse(PurchasedResponse response) {
                hideProgressBar();
                if (response != null && response.isSuccess()) {
                    if(response.getResult()!=null && response.getResult().getBuyerProduct()!=null)
                    {
                        for(PurchasedProductDto purchasedProductDto:response.getResult().getBuyerProduct())
                        {
                            if(purchasedProductDto.getSellerProducts().get(0).getBuyerProductId()==Integer.parseInt(mNotificationPayload.getBuyerProductId()))
                            {
                                Intent intent = new Intent(DashboardActivity.this,PurchasedItemDetailActivity.class);
                                intent.putExtra(Constants.EXTRA_PRODUCT_DETAIL,purchasedProductDto);
                                startActivity(intent);
                                break;
                            }
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
                if (baseResponse != null) {
                    String errorMessage = baseResponse.getMessage();
                    showToast(errorMessage);
//                    Utils.showSnackbar(errorMessage, mBinder.mainContainer, true);
                }
            }
        });
    }


    @Override
    public void onClick(View view) {

    }

}