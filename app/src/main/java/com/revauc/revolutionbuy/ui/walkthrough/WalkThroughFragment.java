package com.revauc.revolutionbuy.ui.walkthrough;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.FragmentWalkthroughBinding;


public class WalkThroughFragment extends Fragment {

    FragmentWalkthroughBinding mBinder;
    private int imageResId;
    private String label;
    private String desc;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mBinder = DataBindingUtil.inflate(inflater, R.layout.fragment_walkthrough, container, false);
        mBinder.imageWalkthrough.setImageResource(imageResId);
        mBinder.textLabel.setText(label);
        mBinder.textDescription.setText(desc);
        return mBinder.getRoot();

    }


    public void setWalkThroughDesc(int imageResId,String label,String desc) {
        this.imageResId = imageResId;
        this.label = label;
        this.desc = desc;
    }
}
