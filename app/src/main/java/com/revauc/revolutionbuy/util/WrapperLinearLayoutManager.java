package com.revauc.revolutionbuy.util;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * ant on 26/05/17.
 */

public class WrapperLinearLayoutManager extends LinearLayoutManager {
    public WrapperLinearLayoutManager(Context context) {
        super(context);
    }


    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            LogUtils.LOGE("probe", "meet a IOOBE in RecyclerView");
        }
    }
}
