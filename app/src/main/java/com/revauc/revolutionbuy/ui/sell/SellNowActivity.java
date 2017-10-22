package com.revauc.revolutionbuy.ui.sell;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.AdapterView;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ActivityAddPhotosBinding;
import com.revauc.revolutionbuy.databinding.ActivityOfferSentBinding;
import com.revauc.revolutionbuy.databinding.ActivitySellNowBinding;
import com.revauc.revolutionbuy.network.BaseResponse;
import com.revauc.revolutionbuy.network.RequestController;
import com.revauc.revolutionbuy.network.retrofit.AuthWebServices;
import com.revauc.revolutionbuy.network.retrofit.DefaultApiObserver;
import com.revauc.revolutionbuy.ui.BaseActivity;
import com.revauc.revolutionbuy.ui.buy.adapter.ItemListedActivity;
import com.revauc.revolutionbuy.util.Constants;
import com.revauc.revolutionbuy.util.ImagePickerUtils;
import com.revauc.revolutionbuy.util.LogUtils;
import com.revauc.revolutionbuy.util.StringUtils;

import java.io.File;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class SellNowActivity extends BaseActivity implements View.OnClickListener {


    private ActivitySellNowBinding mBinding;
    private String mFilePathPrimary, mFilePathOne, mFilePathTwo;
    private boolean isPrimaryImageRemoved, isFirstImageRemoved, isSecondImageRemoved;
    private int requestedFor;
    private final BroadcastReceiver mReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };
    private String mCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sell_now);
        mBinding.toolbarAddPhoto.ivToolBarLeft.setImageResource(R.drawable.ic_back_blue);
        mBinding.toolbarAddPhoto.ivToolBarLeft.setOnClickListener(this);
        mBinding.toolbarAddPhoto.txvToolbarGeneralCenter.setText(R.string.sell_now);
        mBinding.imageOnePlaceholder.setOnClickListener(this);
        mBinding.imageTwoPlaceholder.setOnClickListener(this);
        mBinding.imageThreePlaceholder.setOnClickListener(this);
        mBinding.textSendOffer.setOnClickListener(this);
        final String[] mCurrencyCodes = getResources().getStringArray(R.array.currency_names);
        mBinding.spinnerCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                LogUtils.LOGD("CURRENCY",""+mCurrencyCodes[position]);
                if(position==0)
                {
                    mBinding.textSelectedCurrency.setText("");
                }
                else
                {
                    mBinding.textSelectedCurrency.setText(mCurrencyCodes[position].split(" ")[0]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        LocalBroadcastManager.getInstance(this).registerReceiver(mReciever, new IntentFilter(OfferSentActivity.BROAD_OFFER_SENT_COMPLETE));

        mCategory = getIntent().getStringExtra(Constants.EXTRA_CATEGORY);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReciever);
    }


    private ImagePickerUtils.OnImagePickerListener imageListener = new ImagePickerUtils.OnImagePickerListener() {
        @Override
        public void success(String name, String path) {
            if(requestedFor==1)
            {
                mFilePathPrimary = path;
                isPrimaryImageRemoved = false;
                mBinding.imageOne.setImageBitmap(BitmapFactory.decodeFile(path));
                mBinding.imageOnePlaceholder.setVisibility(View.INVISIBLE);
                mBinding.textPrimary.setVisibility(View.VISIBLE);
                mBinding.imageRemoveOne.setVisibility(View.VISIBLE);
                mBinding.imageRemoveOne.setOnClickListener(SellNowActivity.this);
            }
            else if(requestedFor==2)
            {
                mFilePathOne = path;
                isFirstImageRemoved = false;
                mBinding.imageTwo.setImageBitmap(BitmapFactory.decodeFile(path));
                mBinding.imageTwoPlaceholder.setVisibility(View.INVISIBLE);
                mBinding.imageRemoveTwo.setVisibility(View.VISIBLE);
                mBinding.imageRemoveTwo.setOnClickListener(SellNowActivity.this);
            }
            else if(requestedFor==3)
            {
                mFilePathTwo = path;
                isSecondImageRemoved = false;
                mBinding.imageThree.setImageBitmap(BitmapFactory.decodeFile(path));
                mBinding.imageThreePlaceholder.setVisibility(View.INVISIBLE);
                mBinding.imageRemoveThree.setVisibility(View.VISIBLE);
                mBinding.imageRemoveThree.setOnClickListener(SellNowActivity.this);
            }
            requestedFor=0;
        }

        @Override
        public void fail(String message) {
            showSnackBarFromBottom(message, mBinding.mainContainer, true);
            if(requestedFor==1)
            {
                mFilePathPrimary = "";
                isPrimaryImageRemoved = false;
            }
            else if(requestedFor==2)
            {
                mFilePathOne = "";
                isFirstImageRemoved = false;
            }
            else if(requestedFor==3)
            {
                mFilePathTwo = "";
                isSecondImageRemoved = false;
            }
            requestedFor=0;
        }

        @Override
        public void onImageRemove() {
            if(requestedFor==1)
            {
                mFilePathPrimary = "";
                isPrimaryImageRemoved = true;
                mBinding.textPrimary.setVisibility(View.INVISIBLE);
                mBinding.imageOnePlaceholder.setVisibility(View.VISIBLE);
                mBinding.imageRemoveOne.setVisibility(View.INVISIBLE);
            }
            else if(requestedFor==2)
            {
                mFilePathOne = "";
                isSecondImageRemoved = true;
                mBinding.imageTwoPlaceholder.setVisibility(View.VISIBLE);
                mBinding.imageRemoveTwo.setVisibility(View.INVISIBLE);
            }
            else if(requestedFor==3)
            {
                mFilePathTwo = "";
                isSecondImageRemoved = true;
                mBinding.imageThreePlaceholder.setVisibility(View.VISIBLE);
                mBinding.imageRemoveThree.setVisibility(View.INVISIBLE);
            }
            requestedFor=0;
        }
    };


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_toolbar_general_left:
                onBackPressed();
                break;
            case R.id.text_send_offer:
                validateDetails();
                break;
            case R.id.image_one_placeholder:
                requestedFor = 1;
                ImagePickerUtils.add(getSupportFragmentManager(), imageListener, false);
                break;
            case R.id.image_two_placeholder:
                requestedFor = 2;
                ImagePickerUtils.add(getSupportFragmentManager(), imageListener, false);
                break;
            case R.id.image_three_placeholder:
                requestedFor = 3;
                ImagePickerUtils.add(getSupportFragmentManager(), imageListener, false);
                break;
            case R.id.image_remove_one:
                mFilePathPrimary = "";
                isPrimaryImageRemoved = true;
                mBinding.imageOne.setImageBitmap(null);
                mBinding.textPrimary.setVisibility(View.INVISIBLE);
                mBinding.imageOnePlaceholder.setVisibility(View.VISIBLE);
                mBinding.imageRemoveOne.setVisibility(View.INVISIBLE);
                break;
            case R.id.image_remove_two:
                mFilePathOne = "";
                isSecondImageRemoved = true;
                mBinding.imageTwo.setImageBitmap(null);
                mBinding.imageTwoPlaceholder.setVisibility(View.VISIBLE);
                mBinding.imageRemoveTwo.setVisibility(View.INVISIBLE);
                break;
            case R.id.image_remove_three:
                mFilePathTwo = "";
                isSecondImageRemoved = true;
                mBinding.imageThree.setImageBitmap(null);
                mBinding.imageThreePlaceholder.setVisibility(View.VISIBLE);
                mBinding.imageRemoveThree.setVisibility(View.INVISIBLE);
                break;
        }

    }

    private void sendOfferToBuyer(String price,String description) {
        showProgressBar();

        //File creating from selected URL
        File file1 = null;
        File file2 = null;
        File file3 = null;
        if (mFilePathPrimary != null) {
            file1 = new File(mFilePathPrimary);
        }

        if (mFilePathOne != null) {
            file2 = new File(mFilePathOne);
        }

        if (mFilePathTwo != null) {
            file3 = new File(mFilePathTwo);
        }


        //Creating Profile Details
        RequestBody priceBody = RequestBody.create(MediaType.parse("text/plain"), price);
        RequestBody descBody = RequestBody.create(MediaType.parse("text/plain"), description);
        RequestBody catBody = RequestBody.create(MediaType.parse("text/plain"), mCategory);

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("price", priceBody);
        map.put("buyerProductId", catBody);
        map.put("description", descBody);

        MultipartBody.Part body1 = null;
        if (file1 != null) {
            // create RequestBody instance from file
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file1);

            // MultipartBody.Part is used to send also the actual file name
            body1 = MultipartBody.Part.createFormData("image1", file1.getName(), requestFile);
        }

        MultipartBody.Part body2 = null;
        if (file2 != null) {
            // create RequestBody instance from file
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file2);

            // MultipartBody.Part is used to send also the actual file name
            body2 = MultipartBody.Part.createFormData("image2", file2.getName(), requestFile);
        }

        MultipartBody.Part body3 = null;
        if (file3 != null) {
            // create RequestBody instance from file
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file3);

            // MultipartBody.Part is used to send also the actual file name
            body3 = MultipartBody.Part.createFormData("image3", file3.getName(), requestFile);
        }


        AuthWebServices apiService = RequestController.createRetrofitRequest(false);
        Observable<BaseResponse> observable;
        if(file1==null && file2==null && file3==null)
        {
            observable = apiService.sendOfferToBuyer(map);
        }
        else
        {
            observable = apiService.sendOfferToBuyer(map, body1, body2, body3);
        }

        apiService.sendOfferToBuyer(map, body1,body2,body3).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DefaultApiObserver<BaseResponse>(this) {

                    @Override
                    public void onResponse(BaseResponse response) {
                        hideProgressBar();
                        if (response.isSuccess()) {
                            Intent intent = new Intent(SellNowActivity.this, OfferSentActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                            }
                    }

                    @Override
                    public void onError(Throwable call, BaseResponse baseResponse) {
                        hideProgressBar();
                        if (baseResponse != null) {
                            String errorMessage = baseResponse.getMessage();
                            showSnackBarFromBottom(errorMessage, mBinding.mainContainer, true);
                        }
                    }
                });
    }

    private void validateDetails() {
        String currency = mBinding.textSelectedCurrency.getText().toString();
        String price = mBinding.editPrice.getText().toString();
        String description = mBinding.editDescription.getText().toString();

        if(StringUtils.isNullOrEmpty(currency))
        {
            showSnackBarFromBottom("You need to select the currency for this item",mBinding.mainContainer,true);
        }
        else if(StringUtils.isNullOrEmpty(price))
        {
            showSnackBarFromBottom("You need to enter the price for this item",mBinding.mainContainer,true);
        }
        else if(StringUtils.isNullOrEmpty(description))
        {
            showSnackBarFromBottom("You need to enter the description for this item",mBinding.mainContainer,true);
        }
        else
        {
            sendOfferToBuyer(price,currency+"&&"+description);
        }

    }
}

