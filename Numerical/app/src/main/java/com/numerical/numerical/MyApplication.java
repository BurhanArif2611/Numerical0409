package com.numerical.numerical;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.numerical.numerical.database.UserProfileHelper;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;




public class MyApplication extends MultiDexApplication {
    public static final String TAG = MyApplication.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private static MyApplication mInstance;



    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
   /* @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
*/
    @Retention(RetentionPolicy.SOURCE)
    @IntDef(
            {GRANTED, DENIED, BLOCKED})
    public @interface PermissionStatus {
    }

    public static final int GRANTED = 0;
    public static final int DENIED = 1;
    public static final int BLOCKED = 2;

    /*@AppController.PermissionStatus
    public static int getPermissionStatus(Activity activity, String androidPermissionName) {
        if (ContextCompat.checkSelfPermission(activity, androidPermissionName) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, androidPermissionName)) {
                return BLOCKED;
            }
            return DENIED;
        }
        return GRANTED;
    }*/



    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        new UserProfileHelper(this);
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))//enable logging when app is in debug mode
                .twitterAuthConfig(new TwitterAuthConfig(getResources().getString(R.string.CONSUMER_KEY), getResources().getString(R.string.CONSUMER_SECRET)))//pass the created app Consumer KEY and Secret also called API Key and Secret
                .debug(true)//enable debug mode
                .build();

        //finally initialize twitter with created configs
        Twitter.initialize(config);
    }
    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        req.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(req);
    }
    public <T> void addToRequestQueue1(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

}

