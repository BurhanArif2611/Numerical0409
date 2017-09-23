package com.revauc.revolutionbuy.ui;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.RevBuyApp;
import com.revauc.revolutionbuy.network.BaseResponse;
import com.revauc.revolutionbuy.network.RequestController;
//import com.revauc.revolutionbuy.network.request.auth.FBLoginRequest;
//import com.revauc.revolutionbuy.network.response.auth.LoginResponse;
//import com.revauc.revolutionbuy.network.response.auth.LogoutResponse;
//import com.revauc.revolutionbuy.network.response.auth.UserDto;
import com.revauc.revolutionbuy.network.retrofit.AuthWebServices;
import com.revauc.revolutionbuy.network.retrofit.DefaultApiObserver;
import com.revauc.revolutionbuy.ui.auth.EnterAppActivity;
import com.revauc.revolutionbuy.util.Alert;
import com.revauc.revolutionbuy.util.Constants;
import com.revauc.revolutionbuy.util.LogUtils;
import com.revauc.revolutionbuy.util.PreferenceUtil;
import com.revauc.revolutionbuy.util.Utils;

import java.util.UUID;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by gautam.bisht on 11/11/16.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private final String TAG = "BASE_ACTIVITY";
    protected boolean mAlive;
    private boolean mActive;
    private Dialog mLoadingDialog;
    private Toast mToast;


    private BaseFragment getFragment(Constants.FRAGMENTS fragmentId) {
        BaseFragment fragment = null;
        switch (fragmentId) {
            case FEED_FRAGMENTS:
                //fragment = new FeedFragment();
                break;
        }
        return fragment;
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        mLoadingDialog = new Dialog(this, R.style.TransDialog);
        mLoadingDialog.setContentView(R.layout.view_progress_dialog);
//        getKeyhash();
    }

    public void showSnackBar(String message) {
        Alert.showSnackBar(findViewById(android.R.id.content), message);
    }

    public void showSnackBar(String message, String buttonText, View.OnClickListener listener) {
        Alert.showSnackBar(findViewById(android.R.id.content), message, buttonText, listener);
    }



    public void showDualActionSnackBar(String title, String text, String positiveText, String negativeText, View.OnClickListener positiveListener, View.OnClickListener negativeListener) {
//        LocationSnackBar customSnackbar = LocationSnackBar.make((ViewGroup) findViewById(android.R.id.content));
//        customSnackbar.getView().setPadding(0, 0, 0, 0);
//        customSnackbar.setTitle(title);
//        customSnackbar.setText(text);
//        customSnackbar.setPositiveAction(positiveText, positiveListener);
//        customSnackbar.setNegativeAction(negativeText, negativeListener);
//        customSnackbar.show();
    }


    public boolean isActive() {
        return mActive;
    }

    private boolean isAlive() {
        return mAlive;
    }

    @Override
    protected void onDestroy() {

        hideHud();
        mAlive = false;
        Alert.dismissSnackBar();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mActive = true;
        //getKeyhash();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mActive = true;
    }

    @Override
    protected void onStop() {
        mActive = false;
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        RevBuyApp.activityResumed();
        mAlive = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        RevBuyApp.activityPaused();
    }

    public void showProgressBar() {
        showProgressBar(null, null, null, 0);
    }

    public void hideProgressBar() {
        try {
            if (mLoadingDialog != null && mLoadingDialog.isShowing())
                mLoadingDialog.dismiss();
            mLoadingDialog = null;
        } catch (Exception x) {
            if (x.getMessage() != null)
                LogUtils.LOGE(TAG, x.getMessage());
        }
    }

    public void showProgressBar(final String title, final String msg, final View view, int delayTime) {
        if (mLoadingDialog != null) {
            hideHud();
        }
        if (isAlive()) {
            if (delayTime > 0) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        processToShowDialog(title, msg, view);
                    }
                }, delayTime);
            } else {
                processToShowDialog(title, msg, view);
            }
        }
    }

    private void processToShowDialog(String title, String msg, View view) {
        try {

            if (mLoadingDialog == null) {
                mLoadingDialog = new Dialog(this, R.style.TransDialog);
                mLoadingDialog.setContentView(R.layout.view_progress_dialog);
            }
            mLoadingDialog.setCancelable(false);
            mLoadingDialog.show();
        } catch (Exception e) {
            if (e.getMessage() != null)
                LogUtils.LOGE(TAG, e.getMessage());
        }
    }

    public void hideHud() {
        try {
            if (mLoadingDialog != null && mLoadingDialog.isShowing())
                mLoadingDialog.dismiss();
            mLoadingDialog = null;
        } catch (Exception x) {
            LogUtils.LOGE(TAG, x.getMessage());
        }
    }


    //Show toast message and cancel if any previous toast is displaying.

    public void showToast(String message) {
        if (mToast != null)
            mToast.cancel();
        mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        mToast.show();
    }

    public void hideKeyboard() {
        try {
            hideKeyboard(getCurrentFocus());
        } catch (Exception e) {
            if (e.getMessage() != null)
                LogUtils.LOGE(TAG, e.getMessage());
        }
    }

    private void hideKeyboard(View view) {
        try {
            if (view != null) {
                view.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            if (e.getMessage() != null)
                LogUtils.LOGE(TAG, e.getMessage());
        }
    }

    public void showSnakBarFromTop(String msg, boolean isError) {
        Utils.showSnakbarFromTop(getApplicationContext(), getWindow().getDecorView().getRootView(), msg, isError);
    }

    public void showSnackBarFromBottom(String msg,boolean isError) {
        Utils.showSnackbar(getApplicationContext(), getWindow().getDecorView().getRootView(), msg, isError);
    }

    Fragment pushFragment(Constants.FRAGMENTS fragmentId, Bundle args, int containerViewId, boolean addToBackStack, boolean shouldAdd, ANIMATION_TYPE animationType) {
        try {
            BaseFragment fragment = getFragment(fragmentId);
            if (fragment == null) return null;
            if (args != null)
                fragment.setArguments(args);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            switch (animationType) {
                case DEFAULT:
                case SLIDE:
                    ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                    break;
                case SLIDE_LEFT:
                    ft.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                    break;
                case FADE:
                    ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
                    break;
                case NONE:
                    break;
            }
            if (shouldAdd)
                ft.add(containerViewId, fragment, fragment.getFragmentName());
            else
                ft.replace(containerViewId, fragment, fragment.getFragmentName());
            if (addToBackStack)
                ft.addToBackStack(fragment.getFragmentName());
            if (shouldAdd)
                ft.commit();
            else
                ft.commitAllowingStateLoss();
            return fragment;
        } catch (Exception x) {
            if (x.getMessage() != null)
                LogUtils.LOGE(TAG, x.getMessage());
        }
        return null;
    }


    public Fragment pushFragment(Constants.FRAGMENTS fragmentId, Bundle args) {
        try {
            BaseFragment fragment = getFragment(fragmentId);
            if (fragment == null) return null;
            if (args != null)
                fragment.setArguments(args);

            return fragment;
        } catch (Exception x) {
            if (x.getMessage() != null)
                LogUtils.LOGE(TAG, x.getMessage());
        }
        return null;
    }


//    public void getKeyhash() {
//
//        try
//
//        {
//            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                LogUtils.LOGE("MY KEY HASH:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//            LogUtils.LOGE(TAG, e.getMessage());
//        } catch (NoSuchAlgorithmException e) {
//            LogUtils.LOGE(TAG, e.getMessage());
//        }
//    }


    public enum ANIMATION_TYPE {
        SLIDE, FADE, DEFAULT, NONE, SLIDE_LEFT
    }

    public void logoutUser() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        PreferenceUtil.reset();
        Intent intent = new Intent(this, EnterAppActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }


//    public void loginSignUpWithFacebook(final String email, String username, final String facebookToken, String latLong) {
//        showProgressBar();
//        AuthWebServices apiService = RequestController.createRetrofitRequest(true);
//        final FBLoginRequest request = new FBLoginRequest();
//        request.setEmail(email);
//        request.setAccessToken(facebookToken);
//        request.setDevicetype(Constants.DEVICE_TYPE_ANDROID);
//        request.setDeviceID(UUID.randomUUID().toString());
//        request.setDeviceToken(PreferenceUtil.getFCMToken());
//        request.setLatLongStr(latLong);
//        request.setUsername(username);
//        apiService.loginUsingFacebook(request).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<LoginResponse>(this) {
//
//            @Override
//            public void onResponse(LoginResponse response) {
//                hideProgressBar();
//                if (response.isSuccess()) {
//                    PreferenceUtil.setLoggedIn(true);
//                    PreferenceUtil.setFbLogin(true);
//                    UserDto userData = response.getResponse().getUserDto();
//                    PreferenceUtil.setUserProfile(userData);
//                    PreferenceUtil.setInviteToJoin(response.getResponse().getInviteToJoin());
//                    PreferenceUtil.setAuthToken(response.getResponse().getAccessToken());
////                    Intent intent = new Intent(BaseActivity.this, DashboardActivity.class);
////                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
////                    startActivity(intent);
//                }
//            }
//
//            @Override
//            public void onError(Throwable call, BaseResponse baseResponse) {
//                hideProgressBar();
//                if (baseResponse != null) {
//                    String errorMessage = baseResponse.getMessage();
//                    int errorCode = baseResponse.getStatusCode();
//                    if (errorCode == Constants.ERRORCODE_EMAIL_REQUIRED || errorCode == Constants.ERRORCODE_USERNAME_REQUIRED) {
////                        Intent intent = new Intent(BaseActivity.this, FacebookAccountActivity.class);
////                        intent.putExtra(Constants.EXTRA_FACEBOOK_EMAIL, email);
////                        intent.putExtra(Constants.EXTRA_ERROR_MESSAGE, errorMessage);
////                        intent.putExtra(Constants.EXTRA_FACEBOOK_TOKEN, facebookToken);
////                        startActivity(intent);
//                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
//                    } else {
//                        showSnakBarFromTop(errorMessage,true);
//                    }
//                }
//            }
//        });
//    }

    public void logoutUserApi() {
        showProgressBar();
        AuthWebServices apiService = RequestController.createRetrofitRequest(false);
        apiService.logout().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<BaseResponse>(this) {

            @Override
            public void onResponse(BaseResponse response) {
                hideProgressBar();
                if (response.isSuccess()) {
                logoutUser();
                }
            }

            @Override
            public void onError(Throwable call, BaseResponse baseResponse) {
                hideProgressBar();
                if (baseResponse != null) {
                    String errorMessage = baseResponse.getMessage();
                    int errorCode = baseResponse.getStatusCode();
                    if (errorMessage != null) {
                        showSnakBarFromTop(errorMessage,true);
                    }
                }
            }
        });
    }


}
