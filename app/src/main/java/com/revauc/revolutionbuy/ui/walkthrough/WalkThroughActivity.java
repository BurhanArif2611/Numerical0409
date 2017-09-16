package com.revauc.revolutionbuy.ui.walkthrough;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ActivityWalkthroughBinding;
import com.revauc.revolutionbuy.ui.BaseActivity;
import com.revauc.revolutionbuy.ui.auth.EnterAppActivity;
import com.revauc.revolutionbuy.util.Constants;


public class WalkThroughActivity extends BaseActivity implements View.OnClickListener {


    private ActivityWalkthroughBinding walkThroughActivityBinding;
    private boolean isFromSettings;
    private TypedArray imgsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        walkThroughActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_walkthrough);
        if (getIntent() != null) {
            isFromSettings = getIntent().getBooleanExtra(Constants.EXTRA_FROM_SETTINGS, false);
        }

        setUpView();
    }

    private void setUpView() {
        setupPager();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        setUpView();
        super.onConfigurationChanged(newConfig);
    }

    private void setupPager() {

        imgsArray = getResources().obtainTypedArray(R.array.walkthrough_imgs);
        ViewPager.PageTransformer viewPagerTransformer = new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                page.setRotationY(position * -30f);
            }
        };
        String[] labelArray = getResources().getStringArray(R.array.walkthrough_labels);
        String[] descArray = getResources().getStringArray(R.array.walkthrough_descs);

        WalkThroughPagerAdapter pagerAdapter = new WalkThroughPagerAdapter(getSupportFragmentManager(), imgsArray,labelArray,descArray);
        walkThroughActivityBinding.viewpagerWalkthrough.setPageTransformer(true, viewPagerTransformer);
        walkThroughActivityBinding.viewpagerWalkthrough.setAdapter(pagerAdapter);
        walkThroughActivityBinding.indicatorWalkthrough.setViewPager(walkThroughActivityBinding.viewpagerWalkthrough);


        if (isFromSettings) {
            walkThroughActivityBinding.textviewSkipWalkthrough.setText(R.string.back);
        } else {
            walkThroughActivityBinding.viewpagerWalkthrough.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (position == 3) {
                        walkThroughActivityBinding.textviewNext.setVisibility(View.INVISIBLE);
                        walkThroughActivityBinding.textviewSkipWalkthrough.setText(R.string.get_started);
                    }
                    else if(position ==2)
                    {
                        walkThroughActivityBinding.textviewNext.setVisibility(View.VISIBLE);
                        walkThroughActivityBinding.textviewSkipWalkthrough.setText(R.string.text_skip);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            walkThroughActivityBinding.textviewSkipWalkthrough.setOnClickListener(this);
            walkThroughActivityBinding.textviewNext.setOnClickListener(this);
        }

    }


    private void skipTutorial() {
        if (isFromSettings) {
            onBackPressed();
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        } else {
//            PreferenceUtil.setWalkthroughViewed(true);
            startActivity(new Intent(this, EnterAppActivity.class));
            finish();
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        imgsArray.recycle();
    }

    @Override
    public String getActivityName() {
        return null;
    }

    @Override
    public void onClick(View v) {
        if (v == walkThroughActivityBinding.textviewSkipWalkthrough) {
            skipTutorial();
        }

        if (v == walkThroughActivityBinding.textviewNext) {
            int currentItem = walkThroughActivityBinding.viewpagerWalkthrough.getCurrentItem();
            if(currentItem<3)
            {
                walkThroughActivityBinding.viewpagerWalkthrough.setCurrentItem(currentItem+1,true);
            }
        }
    }
}
