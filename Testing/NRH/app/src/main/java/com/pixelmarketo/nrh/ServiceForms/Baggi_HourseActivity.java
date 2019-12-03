package com.pixelmarketo.nrh.ServiceForms;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.pixelmarketo.nrh.BaseActivity;
import com.pixelmarketo.nrh.R;
import com.pixelmarketo.nrh.fragment.BaggiFragment;
import com.pixelmarketo.nrh.fragment.HourseFragment;
import com.pixelmarketo.nrh.models.Service_Models.Result;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Baggi_HourseActivity extends BaseActivity {

    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.no_data_found_tv)
    TextView noDataFoundTv;
    static Result result;
    @Override
    protected int getContentResId() {
        return R.layout.activity_baggi__hourse;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("Baggi/Horse");
        ButterKnife.bind(this);
        Bundle bundle=getIntent().getExtras();
        if (bundle != null){
            result=(Result)bundle.getSerializable("AllData");
            bundle.getString("SubService");
        }
        tabLayout.addTab(tabLayout.newTab().setText("Baggi"));
        tabLayout.addTab(tabLayout.newTab().setText("Hourse"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        MyTabAdapter adapter = new MyTabAdapter(Baggi_HourseActivity.this, getSupportFragmentManager(), tabLayout.getTabCount());
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
                    BaggiFragment homeFragment = new BaggiFragment();
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("result", result);
                    homeFragment.setArguments(bundle1);
                    return homeFragment;
                case 1:
                    HourseFragment sportFragment = new HourseFragment();
                    Bundle bundle11 = new Bundle();
                    bundle11.putSerializable("result", result);
                    sportFragment.setArguments(bundle11);
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
