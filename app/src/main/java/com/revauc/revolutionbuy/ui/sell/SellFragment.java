
package com.revauc.revolutionbuy.ui.sell;


import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;


import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.FragmentSellBinding;
import com.revauc.revolutionbuy.eventbusmodel.OnSignUpClicked;
import com.revauc.revolutionbuy.ui.BaseFragment;
import com.revauc.revolutionbuy.ui.auth.SignUpActivity;
import com.revauc.revolutionbuy.ui.settings.ChangePasswordActivity;
import com.revauc.revolutionbuy.util.Constants;
import com.revauc.revolutionbuy.util.PreferenceUtil;
import com.revauc.revolutionbuy.widget.BottomMemberAlert;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class SellFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "SellFragment";
    private FragmentSellBinding mBinder;


    public SellFragment() {
        // Required empty public constructor
    }

    public static SellFragment newInstance() {
        return new SellFragment();
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
        mBinder = DataBindingUtil.inflate(inflater, R.layout.fragment_sell, container, false);

        return mBinder.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        setupOptionsGrid();
    }

    private void setupOptionsGrid() {
        mBinder.toolbarSell.txvToolbarGeneralCenter.setText(R.string.sell);
        mBinder.toolbarSell.ivToolBarLeft.setImageResource(R.drawable.ic_search);
        mBinder.toolbarSell.ivToolBarRight.setImageResource(R.drawable.ic_cart);
        mBinder.toolbarSell.ivToolBarLeft.setOnClickListener(this);
        mBinder.toolbarSell.ivToolBarRight.setOnClickListener(this);
        final String[] titles = getResources().getStringArray(R.array.categories_labels);
        TypedArray imgsArray = getResources().obtainTypedArray(R.array.categories_imgs);
        mBinder.gridOptions.setAdapter(new SellOptionsGridAdapter(getActivity(),titles,imgsArray));
        mBinder.gridOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getActivity(),SellerProductLlistingActivity.class);
                intent.putExtra(Constants.EXTRA_CATEGORY_NAME,titles[position]);
                intent.putExtra(Constants.EXTRA_CATEGORY,""+(position+1));
                startActivity(intent);
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDetach() {
        EventBus.getDefault().unregister(this);
        super.onDetach();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.iv_tool_bar_right:
                if(PreferenceUtil.isLoggedIn())
                {
                    startActivity(new Intent(getActivity(),SellerOfferActivity.class));
                }
                else
                {
                    BottomMemberAlert.getInstance(getActivity(),getString(R.string.need_to_be_a_member),getString(R.string.sign_up),getString(R.string.cancel)).show();
                }

                break;
            case R.id.iv_tool_bar_left:
                startActivity(new Intent(getActivity(),SellerProductSearchActivity.class));
                break;
        }
    }

    @Subscribe
    public void onSignUp(OnSignUpClicked onSignUpClicked)
    {
        getActivity().startActivity(new Intent(getActivity(), SignUpActivity.class));
        getActivity().finish();

    }
}
