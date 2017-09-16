package com.revauc.revolutionbuy.ui.walkthrough;

import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class WalkThroughPagerAdapter extends FragmentStatePagerAdapter {

    private final TypedArray imageResArray;
    private final String[] labelArray;
    private final String[] descArray;

    public WalkThroughPagerAdapter(FragmentManager fm, TypedArray imageResArray, String[] labelArray, String[] descArray) {
        super(fm);
        this.imageResArray = imageResArray;
        this.labelArray = labelArray;
        this.descArray = descArray;
    }

    @Override
    public Fragment getItem(int position) {
        WalkThroughFragment fragment = new WalkThroughFragment();
        fragment.setWalkThroughDesc(imageResArray.getResourceId(position, -1),labelArray[position],descArray[position]);
        return fragment;
    }

    @Override
    public int getCount() {
        return imageResArray.length();
    }
}

