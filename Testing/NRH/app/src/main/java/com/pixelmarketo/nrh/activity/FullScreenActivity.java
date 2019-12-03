package com.pixelmarketo.nrh.activity;

import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;


import com.pixelmarketo.nrh.BaseActivity;
import com.pixelmarketo.nrh.R;

import com.pixelmarketo.nrh.adapter.ProductImageAdapter;
import com.pixelmarketo.nrh.models.vender.service.Image;
import com.pixelmarketo.nrh.models.vender.service.Result;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

public class FullScreenActivity extends BaseActivity {


    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.indicator)
    CircleIndicator indicator;
    private Result userInfo;

    @Override
    protected int getContentResId() {
        return R.layout.activity_full_screen;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("Service Image's");
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            userInfo = (Result) bundle.getSerializable("ALLData");
            ArrayList<String> arrayList = new ArrayList<>();
            for (int i=0;i<userInfo.getImage().size();i++) {
                arrayList.add(userInfo.getImage().get(i).getImage());
            }
            ProductImageAdapter guideAdapter = new ProductImageAdapter(FullScreenActivity.this, arrayList);
            pager.setAdapter(guideAdapter);
            if (arrayList.size() > 1) {
                indicator.setViewPager(pager);
            }
        }
    }
}
