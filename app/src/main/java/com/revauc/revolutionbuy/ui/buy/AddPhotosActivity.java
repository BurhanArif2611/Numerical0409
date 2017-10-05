package com.revauc.revolutionbuy.ui.buy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ActivityAddPhotosBinding;
import com.revauc.revolutionbuy.ui.BaseActivity;
import com.revauc.revolutionbuy.ui.buy.adapter.ItemListedActivity;
import com.revauc.revolutionbuy.util.ImagePickerUtils;


public class AddPhotosActivity extends BaseActivity implements View.OnClickListener {


    private ActivityAddPhotosBinding mBinding;
    private String mFilePathPrimary, mFilePathOne, mFilePathTwo;
    private boolean isPrimaryImageRemoved, isFirstImageRemoved, isSecondImageRemoved;
    private int requestedFor;
    private final BroadcastReceiver mReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_photos);
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
            case R.id.tv_toolbar_general_right:
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

    private void validateDetails() {
        Intent intent = new Intent(AddPhotosActivity.this, ItemListedActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }
}

