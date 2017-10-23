/*
 * Copyright © 2017 Block Partee. All rights reserved.
 * Developed by Appster.
 */

package com.revauc.revolutionbuy.ui.sell;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.ui.ComingSoonFragment;
import com.revauc.revolutionbuy.ui.buy.PurchasedFragment;
import com.revauc.revolutionbuy.ui.buy.WishListFragment;


/**
 * Setup Tabs of a contest details in an viewpager
 */
class YourOfferSectionAdapter extends FragmentStatePagerAdapter {


    // --Commented out by Inspection (29/09/17, 9:59 AM):Context mContext;
    private String[] mBuyTabs;
    private SparseArray<Fragment> mPageReferenceMap;

    public YourOfferSectionAdapter(FragmentManager fm, Context context) {
        super(fm);
        mPageReferenceMap = new SparseArray<>();
        mBuyTabs = context.getResources().getStringArray(R.array.your_offer_tabs);
//        mContext = context;


    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                Fragment currentOffersFragment = CurrentOffersFragment.newInstance();
                mPageReferenceMap.put(position, currentOffersFragment);
                return currentOffersFragment;
            case 1:
                Fragment soldOffersFragment = SoldOffersFragment.newInstance();
                mPageReferenceMap.put(position, soldOffersFragment);
                return soldOffersFragment;
            default:
                return ComingSoonFragment.newInstance();
        }


    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        mPageReferenceMap.remove(position);
    }

    public Fragment getFragment(int key) {
        return mPageReferenceMap.get(key);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return mBuyTabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mBuyTabs[position];
    }
}