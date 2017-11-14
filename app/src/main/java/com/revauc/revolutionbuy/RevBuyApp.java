package com.revauc.revolutionbuy;

import android.app.Application;
import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.orhanobut.hawk.Hawk;


public class RevBuyApp extends Application {


    public boolean isRefreshNeeded() {
        return isRefreshNeeded;
    }

    public void setRefreshNeeded(boolean refreshNeeded) {
        isRefreshNeeded = refreshNeeded;
    }

    private static Context mAppContext;

    public static Context getAppContext() {
        return mAppContext;
    }

    public RevBuyApp getInstance() {
        return this;
    }

    private boolean isRefreshNeeded;
    private String refreshNeededType;

    public String getRefreshNeededType() {
        return refreshNeededType;
    }

    public void setRefreshNeededType(String refreshNeededType) {
        this.refreshNeededType = refreshNeededType;
    }

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    private static boolean activityVisible;


    @Override
    public void onCreate() {
        super.onCreate();
//        Fabric.with(this, new Crashlytics());

        mAppContext = this.getApplicationContext();
//        AppEventsLogger.activateApp(this);
//        if (BuildConfig.DEBUG) {
//            LeakCanary.install(this);
//            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                    .detectDiskReads()
//                    .detectDiskWrites()
//                    .detectNetwork()   // or .detectAll() for all detectable problems
//                    .penaltyLog()
//                    .build());
//            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//                    .detectLeakedSqlLiteObjects()
//                    .detectLeakedClosableObjects()
//                    .detectActivityLeaks()
//                    .penaltyLog()
//                    //.penaltyDeath()
//                    .build());
//        }


        // Shared preference initialize
        Hawk.init(mAppContext).build();

        /*int buildVersion = PreferenceUtils.getBuildVersion();
        if (BuildConfig.VERSION_CODE > buildVersion) {
            //PreferenceUtils.saveAppConfiguration(null);
        }*/

        //Install CustomActivityOnCrash
//        if (!BuildConfig.DEBUG) {
//            CustomActivityOnCrash.setLaunchErrorActivityWhenInBackground(false);
//            // TODO: 10/11/16 Change with your Crash Screen
//            //CustomActivityOnCrash.setErrorActivityClass(SomethingWentWrongActivity.class);
//            CustomActivityOnCrash.install(this);
//
//        }

        //Intialize Realm
//        initRealm();

        // Push Notification initialize
         FirebaseApp.initializeApp(mAppContext);
    }


//    private void initRealm() {
//        try {
//            Realm.init(this);
//            RealmManager.initializeRealmConfig(this);
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }


}
