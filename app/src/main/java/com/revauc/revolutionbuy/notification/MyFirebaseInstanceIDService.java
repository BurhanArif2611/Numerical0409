/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.revauc.revolutionbuy.notification;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.revauc.revolutionbuy.network.BaseResponse;
import com.revauc.revolutionbuy.network.RequestController;
import com.revauc.revolutionbuy.network.request.UpdateDeviceTokenRequest;
import com.revauc.revolutionbuy.network.retrofit.AuthWebServices;
import com.revauc.revolutionbuy.ui.auth.SplashActivity;
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

        if(PreferenceUtil.isLoggedIn()){
            PreferenceUtil.setFCMToken(refreshedToken);
            sendUpdatedTokenToServer();
        }else {
            PreferenceUtil.setFCMToken(refreshedToken);
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(SplashActivity.BROAD_FCM_TOKEN));
//            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(EnterAppActivity.BROAD_FCM_TOKEN));
        }

    }
    // [END refresh_token]


    private void sendUpdatedTokenToServer() {

        AuthWebServices apiService = RequestController.createRetrofitRequest(false);
        UpdateDeviceTokenRequest request = new UpdateDeviceTokenRequest(FirebaseInstanceId.getInstance().getToken());
        apiService.updateToken(request).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultObserver<BaseResponse>() {

            @Override
            public void onNext(BaseResponse value) {
                LogUtils.LOGD("FCM idservice"," updated");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }



}
