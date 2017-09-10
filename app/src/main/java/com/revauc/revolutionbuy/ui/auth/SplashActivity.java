package com.revauc.revolutionbuy.ui.auth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import com.google.firebase.iid.FirebaseInstanceId;
import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.ui.BaseActivity;
import com.revauc.revolutionbuy.util.LogUtils;
import com.revauc.revolutionbuy.util.PreferenceUtil;
import com.revauc.revolutionbuy.widget.GifImageView;


public class SplashActivity extends BaseActivity {

    private boolean isSplashScreenVisible = true;
    public static final String BROAD_FCM_TOKEN = "com.revauc.revolutionbuy.ui.auth.SplashActivity.fcm";
    private final BroadcastReceiver tokenReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            hideProgressBar();
            initializeSplashHandler();

        }
    };


    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (isSplashScreenVisible) {
//                if (PreferenceUtil.isLoggedIn()) {
//                    startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
//                } else if (PreferenceUtil.shouldShowEnterApp()) {
//                    startActivity(new Intent(SplashActivity.this, EnterAppActivity.class));
//                } else {
//                    startActivity(new Intent(SplashActivity.this, WalkThroughActivity.class));
//                }
//                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        GifImageView gifImageView = (GifImageView) findViewById(R.id.image_splash);
//        gifImageView.setGifImageResource(R.drawable.splash_gif);

        String token = FirebaseInstanceId.getInstance().getToken();
        String fcmToken = PreferenceUtil.getFCMToken();
        LogUtils.LOGD("FCM instance token ", token);
        LogUtils.LOGD("FCM token", token);
        if (TextUtils.isEmpty(token) && TextUtils.isEmpty(fcmToken)) {
            LocalBroadcastManager.getInstance(this).registerReceiver(tokenReciever, new IntentFilter(BROAD_FCM_TOKEN));
            showProgressBar();
            return;
        } else if (!TextUtils.isEmpty(fcmToken) && !fcmToken.equalsIgnoreCase(token)) {
            //TODO Refresh Token
        }else if(!TextUtils.isEmpty(token)){
            PreferenceUtil.setFCMToken(token);
        }
        initializeSplashHandler();

    }

    @Override
    protected void onResume() {
        super.onResume();
        isSplashScreenVisible = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        isSplashScreenVisible = false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }

    /**
     * Runs handler for few seconds.
     */
    private void initializeSplashHandler() {
        int SPLASH_DISPLAY_LENGTH = 3000;
        new Handler().postDelayed(mRunnable, SPLASH_DISPLAY_LENGTH);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(tokenReciever);

    }

    @Override
    public String getActivityName() {
        return null;
    }
}
