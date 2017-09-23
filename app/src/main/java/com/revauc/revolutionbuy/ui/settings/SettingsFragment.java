package com.revauc.revolutionbuy.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.FragmentSettingsBinding;
import com.revauc.revolutionbuy.ui.BaseActivity;
import com.revauc.revolutionbuy.ui.auth.CreateProfileActivity;
import com.revauc.revolutionbuy.ui.sell.SellOptionsGridAdapter;
import com.revauc.revolutionbuy.util.Constants;


public class SettingsFragment extends Fragment implements View.OnClickListener {

    private FragmentSettingsBinding mBinder;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinder = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false);

        return mBinder.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
    }

    private void setupViews() {
        mBinder.toolbarSettings.txvToolbarGeneralCenter.setText(R.string.settings);
        mBinder.textYourProfile.setOnClickListener(this);
        mBinder.textLogout.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.text_your_profile:
                Intent intent = new Intent(getActivity(), CreateProfileActivity.class);
                intent.putExtra(Constants.EXTRA_FROM_SETTINGS,true);
                startActivity(intent);
                    break;
            case R.id.text_logout:
                ((BaseActivity)getActivity()).logoutUserApi();
                break;
        }
    }
}
