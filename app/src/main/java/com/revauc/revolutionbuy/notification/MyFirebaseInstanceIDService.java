

package com.revauc.revolutionbuy.notification;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.revauc.revolutionbuy.ui.walkthrough.WalkThroughActivity;
import com.revauc.revolutionbuy.util.LogUtils;
import com.revauc.revolutionbuy.util.PreferenceUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        LogUtils.LOGD("FCM idservice", refreshedToken);

        if(PreferenceUtil.getUserProfile()!=null){
            PreferenceUtil.setFCMToken(refreshedToken);
//            sendUpdatedTokenToServer();
        }else {
            PreferenceUtil.setFCMToken(refreshedToken);
//            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(WalkThroughActivity.BROAD_FCM_TOKEN));
//            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(EnterAppActivity.BROAD_FCM_TOKEN));
        }

    }
    // [END refresh_token]


//    private void sendUpdatedTokenToServer() {
//
//        AuthWebServices apiService = RequestController.createRetrofitRequest(false);
//        UpdateDeviceTokenRequest request = new UpdateDeviceTokenRequest(FirebaseInstanceId.getInstance().getToken());
//        apiService.updateDeviceToken(request).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultObserver<BaseResponse>() {
//
//            @Override
//            public void onNext(BaseResponse value) {
//                LogUtils.LOGD("FCM idservice"," updated");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
//    }



}
