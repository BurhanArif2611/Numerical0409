package com.revauc.revolutionbuy.ui.sell;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ActivityCountryChooserBinding;
import com.revauc.revolutionbuy.databinding.ActivitySelectCategoriesBinding;
import com.revauc.revolutionbuy.listeners.OnCategorySelectListener;
import com.revauc.revolutionbuy.listeners.OnCurrencySelectListener;
import com.revauc.revolutionbuy.network.BaseResponse;
import com.revauc.revolutionbuy.network.RequestController;
import com.revauc.revolutionbuy.network.response.buyer.BuyerProductCategoryDto;
import com.revauc.revolutionbuy.network.response.buyer.BuyerProductDto;
import com.revauc.revolutionbuy.network.response.buyer.CategoriesResponse;
import com.revauc.revolutionbuy.network.response.buyer.CategoryDto;
import com.revauc.revolutionbuy.network.retrofit.AuthWebServices;
import com.revauc.revolutionbuy.network.retrofit.DefaultApiObserver;
import com.revauc.revolutionbuy.ui.BaseActivity;
import com.revauc.revolutionbuy.ui.buy.AddTitleActivity;
import com.revauc.revolutionbuy.ui.buy.adapter.CategoriesAdapter;
import com.revauc.revolutionbuy.ui.buy.adapter.ItemListedActivity;
import com.revauc.revolutionbuy.ui.sell.adapter.CurrencyAdapter;
import com.revauc.revolutionbuy.util.Constants;
import com.revauc.revolutionbuy.widget.SingleActionBottomSheetAlert;

import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 *  24/09/17.
 */

public class CurrencyChooserActivity extends BaseActivity implements View.OnClickListener, OnCurrencySelectListener {


    private ActivityCountryChooserBinding mBinding;
    private String[] mCurrencyCodes;
    private CurrencyAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_country_chooser);
        mBinding.toolbarChooser.ivToolBarLeft.setImageResource(R.drawable.ic_back_blue);
        mBinding.toolbarChooser.ivToolBarLeft.setVisibility(View.VISIBLE);
        mBinding.toolbarChooser.ivToolBarLeft.setOnClickListener(this);
        mBinding.toolbarChooser.txvToolbarGeneralCenter.setText(R.string.select_currency);

        setCurrencyData();
    }


    private void setCurrencyData() {
        mAdapter = new CurrencyAdapter(CurrencyChooserActivity.this,this);
        RecyclerView.LayoutManager lay = new LinearLayoutManager(CurrencyChooserActivity.this);
        mBinding.recyclerView.setLayoutManager(lay);
        mBinding.recyclerView.setAdapter(mAdapter);

        // Capture Text in EditText
        mBinding.editCountry.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = mBinding.editCountry.getText().toString().toLowerCase(Locale.getDefault());
                mAdapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_tool_bar_left:
                onBackPressed();
                break;
        }

    }

    @Override
    public void onCurrencySelected(String currency) {
        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_CURRENCY_NAME,currency);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }
}

