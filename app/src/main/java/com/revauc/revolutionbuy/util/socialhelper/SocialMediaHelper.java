package com.revauc.revolutionbuy.util.socialhelper;

import android.content.Intent;

import com.revauc.revolutionbuy.ui.BaseActivity;

import static com.revauc.revolutionbuy.util.LogUtils.makeLogTag;


/**
 * Created by User on 6/22/2015.
 */
public class SocialMediaHelper {
    private static final String TAG = makeLogTag(SocialMediaHelper.class);
    BaseActivity mActivity = null;
    private SocialType mLoginType = SocialType.DEFAULT;
    private SocialAuthListener mProfileListener = null;
    private SocialFacebookHelper mFacebookHelper;

    public SocialMediaHelper(BaseActivity activity, SocialAuthListener listener) {
        this.mActivity = activity;
        this.mProfileListener = listener;
    }

    public static void logoutSession() {
        SocialFacebookHelper.logout();
    }


    public void clear() {
        mActivity = null;
        mFacebookHelper = null;
    }

    public void setSocialType(SocialType socialType) {
        mLoginType = socialType;
    }

    public void initProcess() {
        switch (mLoginType) {
            case FACEBOOK:
                if (mFacebookHelper == null)
                    mFacebookHelper = new SocialFacebookHelper(mActivity, mProfileListener);
                else
                    mFacebookHelper.setActivity(mActivity, mProfileListener);

                mFacebookHelper.facebookLogin();
                break;
            case GOOGLE:
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (mLoginType) {
            case FACEBOOK:
                mFacebookHelper.getCallbackManager().onActivityResult(requestCode, resultCode, data);
                break;
            case GOOGLE:
                switch (requestCode) {
                }
                break;
        }
    }

}
