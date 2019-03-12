

package com.revauc.revolutionbuy.ui.buy;


import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.FragmentBuyBinding;
import com.revauc.revolutionbuy.eventbusmodel.OnSignUpClicked;
import com.revauc.revolutionbuy.ui.BaseFragment;
import com.revauc.revolutionbuy.ui.auth.SignUpActivity;
import com.revauc.revolutionbuy.util.PreferenceUtil;
import com.revauc.revolutionbuy.widget.BottomMemberAlert;
import com.revauc.revolutionbuy.widget.typeface.CustomTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class BuyFragment extends BaseFragment implements TabLayout.OnTabSelectedListener, View.OnClickListener {
    private static final String TAG = "SellFragment";
    private FragmentBuyBinding mBinder;


    public BuyFragment() {
        // Required empty public constructor
    }

    public static BuyFragment newInstance() {
        return new BuyFragment();
    }

    @Override
    public String getFragmentName() {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinder = DataBindingUtil.inflate(inflater, R.layout.fragment_buy, container, false);

        return mBinder.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBinder.toolbarBuy.txvToolbarGeneralCenter.setText(R.string.buy);
        BuySectionAdapter notificationsSectionAdapter = new BuySectionAdapter(getActivity().getSupportFragmentManager(),getActivity());
        mBinder.viewpagerBuy.setAdapter(notificationsSectionAdapter);
        mBinder.toolbarBuy.buyTabs.setupWithViewPager(mBinder.viewpagerBuy);
        for (int i = 0; i < mBinder.toolbarBuy.buyTabs.getTabCount(); i++) {
            CustomTextView tv=(CustomTextView) LayoutInflater.from(getActivity()).inflate(R.layout.layout_tab_item,null);
            mBinder.toolbarBuy.buyTabs.getTabAt(i).setCustomView(tv);
        }
        mBinder.toolbarBuy.buyTabs.addOnTabSelectedListener(this);
        mBinder.floatingActionButton.setOnClickListener(this);

        if(!PreferenceUtil.isLoggedIn())
        {
            BottomMemberAlert.getInstance(getActivity(),getString(R.string.need_to_be_a_member),getString(R.string.sign_up),getString(R.string.cancel)).show();
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if(tab.getPosition()==0)
        {
            mBinder.floatingActionButton.setVisibility(View.VISIBLE);
            mBinder.floatingActionButton.setOnClickListener(this);
        }
        else
        {
            mBinder.floatingActionButton.setVisibility(View.INVISIBLE);
            mBinder.floatingActionButton.setOnClickListener(null);
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.floating_action_button:
                if(PreferenceUtil.isLoggedIn())
                {
                    startActivity(new Intent(getActivity(),SelectCategoriesActivity.class));
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
                else
                {
                    BottomMemberAlert.getInstance(getActivity(),getString(R.string.need_to_be_a_member),getString(R.string.sign_up),getString(R.string.cancel)).show();
                }

                break;
        }
    }

    @Override
    public void onDetach() {
        EventBus.getDefault().unregister(this);
        super.onDetach();
    }


    @Subscribe
    public void onSignUp(OnSignUpClicked onSignUpClicked)
    {
        getActivity().startActivity(new Intent(getActivity(), SignUpActivity.class));
        getActivity().finish();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }
}
