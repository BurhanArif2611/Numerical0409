package com.revauc.revolutionbuy.ui.buy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ActivityAddPhotosBinding;
import com.revauc.revolutionbuy.network.BaseResponse;
import com.revauc.revolutionbuy.network.RequestController;
import com.revauc.revolutionbuy.network.response.LoginResponse;
import com.revauc.revolutionbuy.network.response.buyer.BuyerProductDto;
import com.revauc.revolutionbuy.network.response.buyer.BuyerProductImageDto;
import com.revauc.revolutionbuy.network.retrofit.AuthWebServices;
import com.revauc.revolutionbuy.network.retrofit.DefaultApiObserver;
import com.revauc.revolutionbuy.ui.BaseActivity;
import com.revauc.revolutionbuy.ui.auth.CreateProfileActivity;
import com.revauc.revolutionbuy.ui.buy.adapter.ItemListedActivity;
import com.revauc.revolutionbuy.ui.dashboard.DashboardActivity;
import com.revauc.revolutionbuy.util.Constants;
import com.revauc.revolutionbuy.util.ImagePickerUtils;
import com.revauc.revolutionbuy.util.PreferenceUtil;
import com.revauc.revolutionbuy.util.StringUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class AddPhotosActivity extends BaseActivity implements View.OnClickListener {


    private ActivityAddPhotosBinding mBinding;
    private String mFilePathPrimary, mFilePathOne, mFilePathTwo;
    private boolean isPrimaryImageRemoved=true;
    private boolean  isFirstImageRemoved=true;
    private boolean isSecondImageRemoved=true;
    private int primaryId,firstId,secondId;
    private int requestedFor;
    private final BroadcastReceiver mReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };
    private String mTitle;
    private String mDescription;
    private String mCategory;
    private String mDeletedIds="";
    private BuyerProductDto mProductDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_photos);
        mProductDetail = getIntent().getParcelableExtra(Constants.EXTRA_PRODUCT_DETAIL);
        mBinding.toolbarAddPhoto.tvToolbarGeneralLeft.setText(R.string.cancel);
        mBinding.toolbarAddPhoto.tvToolbarGeneralLeft.setVisibility(View.VISIBLE);
        mBinding.toolbarAddPhoto.tvToolbarGeneralLeft.setOnClickListener(this);
        mBinding.toolbarAddPhoto.txvToolbarGeneralCenter.setText(R.string.upload_photos);
        mBinding.toolbarAddPhoto.tvToolbarGeneralRight.setText(R.string.skip_done_text);
        mBinding.toolbarAddPhoto.tvToolbarGeneralRight.setVisibility(View.VISIBLE);
        mBinding.toolbarAddPhoto.tvToolbarGeneralRight.setOnClickListener(this);
        mBinding.imageOnePlaceholder.setOnClickListener(this);
        mBinding.imageTwoPlaceholder.setOnClickListener(this);
        mBinding.imageThreePlaceholder.setOnClickListener(this);
        mBinding.layoutBuyerFooter.textStepFour.setEnabled(true);
        LocalBroadcastManager.getInstance(this).registerReceiver(mReciever, new IntentFilter(ItemListedActivity.BROAD_BUY_LISTED_COMPLETE));


        mTitle = getIntent().getStringExtra(Constants.EXTRA_TITLE);
        mDescription = getIntent().getStringExtra(Constants.EXTRA_DESCRIPTION);
        mCategory = getIntent().getStringExtra(Constants.EXTRA_CATEGORY);

        if(mProductDetail!=null)
        {
            if(mProductDetail.getBuyerProductImages()!=null && !mProductDetail.getBuyerProductImages().isEmpty())
            {
                for(BuyerProductImageDto buyerProductImageDto: mProductDetail.getBuyerProductImages()){
                    if(buyerProductImageDto.getPrimaryImage()==1)
                    {
                        Picasso.with(this).load(buyerProductImageDto.getImageName()).into(mBinding.imageOne);
                        isPrimaryImageRemoved = false;
                        primaryId = buyerProductImageDto.getId();
                        mBinding.imageOnePlaceholder.setVisibility(View.INVISIBLE);
                        mBinding.textPrimary.setVisibility(View.VISIBLE);
                        mBinding.imageRemoveOne.setVisibility(View.VISIBLE);
                        mBinding.imageRemoveOne.setOnClickListener(AddPhotosActivity.this);
                    }
                    else
                    {
                        if(isFirstImageRemoved)
                        {
                            Picasso.with(this).load(buyerProductImageDto.getImageName()).into(mBinding.imageTwo);
                            isFirstImageRemoved = false;
                            firstId = buyerProductImageDto.getId();
                            mBinding.imageTwoPlaceholder.setVisibility(View.INVISIBLE);
                            mBinding.imageRemoveTwo.setVisibility(View.VISIBLE);
                            mBinding.imageRemoveTwo.setOnClickListener(AddPhotosActivity.this);
                        }
                        else if(isSecondImageRemoved)
                        {
                            Picasso.with(this).load(buyerProductImageDto.getImageName()).into(mBinding.imageThree);
                            isSecondImageRemoved = false;
                            secondId = buyerProductImageDto.getId();
                            mBinding.imageThreePlaceholder.setVisibility(View.INVISIBLE);
                            mBinding.imageRemoveThree.setVisibility(View.VISIBLE);
                            mBinding.imageRemoveThree.setOnClickListener(AddPhotosActivity.this);
                        }

                    }
                }
            }
        }
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
                mBinding.imageRemoveOne.setOnClickListener(AddPhotosActivity.this);
            }
            else if(requestedFor==2)
            {
                mFilePathOne = path;
                isFirstImageRemoved = false;
                mBinding.imageTwo.setImageBitmap(BitmapFactory.decodeFile(path));
                mBinding.imageTwoPlaceholder.setVisibility(View.INVISIBLE);
                mBinding.imageRemoveTwo.setVisibility(View.VISIBLE);
                mBinding.imageRemoveTwo.setOnClickListener(AddPhotosActivity.this);
            }
            else if(requestedFor==3)
            {
                mFilePathTwo = path;
                isSecondImageRemoved = false;
                mBinding.imageThree.setImageBitmap(BitmapFactory.decodeFile(path));
                mBinding.imageThreePlaceholder.setVisibility(View.INVISIBLE);
                mBinding.imageRemoveThree.setVisibility(View.VISIBLE);
                mBinding.imageRemoveThree.setOnClickListener(AddPhotosActivity.this);
            }
            requestedFor=0;
        }

        @Override
        public void fail(String message) {
            showSnackBarFromBottom(message, mBinding.mainContainer, true);
            if(requestedFor==1)
            {
                mFilePathPrimary = "";
                isPrimaryImageRemoved = true;
            }
            else if(requestedFor==2)
            {
                mFilePathOne = "";
                isFirstImageRemoved = true;
            }
            else if(requestedFor==3)
            {
                mFilePathTwo = "";
                isSecondImageRemoved = true;
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
            case R.id.tv_toolbar_general_right:
                if(mProductDetail!=null)
                {
                    editBuyerProduct();
                }
                else
                {
                    addBuyerProduct();

                }
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
                if(mProductDetail!=null)
                {
                    if(primaryId>0)
                    {
                        mDeletedIds = mDeletedIds+primaryId+",";
                    }
                }
                mFilePathPrimary = "";
                isPrimaryImageRemoved = true;
                mBinding.imageOne.setImageBitmap(null);
                mBinding.textPrimary.setVisibility(View.INVISIBLE);
                mBinding.imageOnePlaceholder.setVisibility(View.VISIBLE);
                mBinding.imageRemoveOne.setVisibility(View.INVISIBLE);
                break;
            case R.id.image_remove_two:
                if(mProductDetail!=null)
                {
                    if(firstId>0)
                    {
                        mDeletedIds = mDeletedIds+firstId+",";
                    }
                }
                mFilePathOne = "";
                isSecondImageRemoved = true;
                mBinding.imageTwo.setImageBitmap(null);
                mBinding.imageTwoPlaceholder.setVisibility(View.VISIBLE);
                mBinding.imageRemoveTwo.setVisibility(View.INVISIBLE);
                break;
            case R.id.image_remove_three:
                if(mProductDetail!=null)
                {
                    if(secondId>0)
                    {
                        mDeletedIds = mDeletedIds+secondId+",";
                    }
                }
                mFilePathTwo = "";
                isSecondImageRemoved = true;
                mBinding.imageThree.setImageBitmap(null);
                mBinding.imageThreePlaceholder.setVisibility(View.VISIBLE);
                mBinding.imageRemoveThree.setVisibility(View.INVISIBLE);
                break;
        }

    }

    private void addBuyerProduct() {
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
        RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), mTitle);
        RequestBody ageBody = RequestBody.create(MediaType.parse("text/plain"), mDescription);
        RequestBody cityBody = RequestBody.create(MediaType.parse("text/plain"), mCategory);

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("title", nameBody);
        map.put("description", ageBody);
        map.put("category", cityBody);

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
            observable = apiService.addBuyerProduct(map);
        }
        else
        {
            observable = apiService.addBuyerProduct(map, body1, body2, body3);
        }

        apiService.addBuyerProduct(map, body1,body2,body3).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DefaultApiObserver<BaseResponse>(this) {

                    @Override
                    public void onResponse(BaseResponse response) {
                        hideProgressBar();
                        if (response.isSuccess()) {
                            Intent intent = new Intent(AddPhotosActivity.this, ItemListedActivity.class);
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

    private void editBuyerProduct() {
        showProgressBar();

        //File creating from selected URL
        File file1 = null;
        File file2 = null;
        File file3 = null;
        if (!StringUtils.isNullOrEmpty(mFilePathPrimary)) {
            file1 = new File(mFilePathPrimary);
        }

        if (!StringUtils.isNullOrEmpty(mFilePathOne)) {
            file2 = new File(mFilePathOne);
        }

        if (!StringUtils.isNullOrEmpty(mFilePathTwo)) {
            file3 = new File(mFilePathTwo);
        }


        //Creating Profile Details
        RequestBody idBody = RequestBody.create(MediaType.parse("text/plain"), ""+mProductDetail.getId());

        RequestBody deletedIdBody = RequestBody.create(MediaType.parse("text/plain"), StringUtils.isNullOrEmpty(mDeletedIds)?mDeletedIds:mDeletedIds.substring(0,mDeletedIds.length()-1));
        RequestBody titleBody = RequestBody.create(MediaType.parse("text/plain"), mTitle);
        RequestBody descBody = RequestBody.create(MediaType.parse("text/plain"), mDescription);
        RequestBody categoryBody = RequestBody.create(MediaType.parse("text/plain"), mCategory);

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("id", idBody);
        map.put("deletedImageIds", deletedIdBody);
        map.put("title", titleBody);
        map.put("description", descBody);
        map.put("category", categoryBody);

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
            observable = apiService.addBuyerProduct(map);
        }
        else
        {
            observable = apiService.addBuyerProduct(map, body1, body2, body3);
        }

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DefaultApiObserver<BaseResponse>(this) {

                    @Override
                    public void onResponse(BaseResponse response) {
                        hideProgressBar();
                        if (response.isSuccess()) {
                            Intent intent = new Intent(AddPhotosActivity.this, ItemListedActivity.class);
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
        Intent intent = new Intent(AddPhotosActivity.this, ItemListedActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }
}

