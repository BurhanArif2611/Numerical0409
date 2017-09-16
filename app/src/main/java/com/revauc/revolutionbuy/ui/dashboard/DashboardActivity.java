package com.revauc.revolutionbuy.ui.dashboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
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
import com.revauc.revolutionbuy.ui.BaseActivity;
import com.revauc.revolutionbuy.ui.ComingSoonFragment;
import com.revauc.revolutionbuy.ui.sell.SellFragment;
import com.revauc.revolutionbuy.ui.settings.SettingsFragment;
import com.revauc.revolutionbuy.util.Constants;
import com.revauc.revolutionbuy.util.LogUtils;
import com.revauc.revolutionbuy.util.Utils;

import org.greenrobot.eventbus.EventBus;

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
                            .replace(R.id.content, ComingSoonFragment.newInstance())
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
                            .replace(R.id.content, ComingSoonFragment.newInstance())
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);

        prepareViews();

    }

    private void prepareViews() {

        mBinder.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mBinder.navigation.enableShiftingMode(false);
        mBinder.navigation.enableItemShiftingMode(false);
        mBinder.navigation.setTextVisibility(true);
        mBinder.navigation.enableAnimation(false);

        mBinder.navigation.setTypeface(Typeface.createFromAsset(
                getAssets(),
                getString(R.string.font_avenir_regular)));
        mBinder.navigation.setItemTextColor(Utils.getTabTextColors(this));
        mBinder.navigation.setItemIconTintList(Utils.getTabTextColors(this));

        mBinder.navigation.setIconSize(getResources().getInteger(R.integer.tab_icon_size), getResources().getInteger(R.integer.tab_icon_size));


        mBinder.navigation.setCurrentItem(0);


    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public String getActivityName() {
        return null;
    }
}