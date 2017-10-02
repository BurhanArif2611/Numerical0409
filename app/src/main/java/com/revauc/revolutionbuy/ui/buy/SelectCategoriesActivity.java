package com.revauc.revolutionbuy.ui.buy;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ActivityProfileBinding;
import com.revauc.revolutionbuy.databinding.ActivitySelectCategoriesBinding;
import com.revauc.revolutionbuy.network.BaseResponse;
import com.revauc.revolutionbuy.network.RequestController;
import com.revauc.revolutionbuy.network.response.UserDto;
import com.revauc.revolutionbuy.network.response.buyer.CategoriesResponse;
import com.revauc.revolutionbuy.network.response.buyer.CategoryDto;
import com.revauc.revolutionbuy.network.response.profile.StateResponse;
import com.revauc.revolutionbuy.network.retrofit.AuthWebServices;
import com.revauc.revolutionbuy.network.retrofit.DefaultApiObserver;
import com.revauc.revolutionbuy.ui.BaseActivity;
import com.revauc.revolutionbuy.ui.auth.CreateProfileActivity;
import com.revauc.revolutionbuy.util.Constants;
import com.revauc.revolutionbuy.util.PreferenceUtil;
import com.revauc.revolutionbuy.util.StringUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by nishant on 24/09/17.
 */

public class SelectCategoriesActivity extends BaseActivity implements View.OnClickListener {


    private ActivitySelectCategoriesBinding mBinding;
    private List<CategoryDto> mCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_select_categories);
        mBinding.toolbarBuyer.tvToolbarGeneralLeft.setText(R.string.back);
        mBinding.toolbarBuyer.txvToolbarGeneralCenter.setText(R.string.select_categories);
        mBinding.toolbarBuyer.tvToolbarGeneralRight.setText(R.string.next);
        mBinding.toolbarBuyer.tvToolbarGeneralLeft.setOnClickListener(this);
        mBinding.toolbarBuyer.txvToolbarGeneralCenter.setText(R.string.select_categories);
        mBinding.toolbarBuyer.tvToolbarGeneralRight.setOnClickListener(this);

        fetchCategories();

    }

    private void fetchCategories() {
            AuthWebServices apiService = RequestController.createRetrofitRequest(false);

            apiService.getCategories().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<CategoriesResponse>(this) {

                @Override
                public void onResponse(CategoriesResponse response) {
                    if (response != null && response.isSuccess()) {
                        mCategories = response.getResult().getCategory();

                    } else {
                        showSnackBarFromBottom(response.getMessage(), mBinding.mainContainer, true);
                    }

                }

                @Override
                public void onError(Throwable call, BaseResponse baseResponse) {
                    if (baseResponse != null) {
                        String errorMessage = baseResponse.getMessage();
                        showSnackBarFromBottom(errorMessage, mBinding.mainContainer, true);
                    }
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
        switch (view.getId())
        {
            case R.id.image_back:
                onBackPressed();

                break;
            case R.id.text_edit:
                Intent intent = new Intent(SelectCategoriesActivity.this, CreateProfileActivity.class);
                intent.putExtra(Constants.EXTRA_FROM_SETTINGS,true);
                startActivity(intent);
                break;
        }

    }

}

