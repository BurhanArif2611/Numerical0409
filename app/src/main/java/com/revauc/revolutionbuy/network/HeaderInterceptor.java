package com.revauc.revolutionbuy.network;

import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ant on 25/01/17.
 */

public class HeaderInterceptor implements Interceptor {

    private boolean isAuth;

    public HeaderInterceptor(boolean accessToken) {
        isAuth = accessToken;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder requestBuilder;
        if (isAuth) {

            //String accessToken = PreferenceUtil.getAccessToken();
            String accessToken  ="93fd7d124b02c16e66001cee8773a6cc";
            if (!TextUtils.isEmpty(accessToken)) {
                requestBuilder = original.newBuilder()
                        .header("accessToken", accessToken)
                        .method(original.method(), original.body());

            } else {
                requestBuilder = original.newBuilder().method(original.method(), original.body());
            }
        } else {
            requestBuilder = original.newBuilder().method(original.method(), original.body());
        }
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}

