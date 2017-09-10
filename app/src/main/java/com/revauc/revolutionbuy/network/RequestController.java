package com.revauc.revolutionbuy.network;


import com.revauc.revolutionbuy.BuildConfig;
import com.revauc.revolutionbuy.network.retrofit.AuthWebServices;
import com.revauc.revolutionbuy.util.LogUtils;
import com.revauc.revolutionbuy.util.PreferenceUtil;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * how to use
 * private AuthWebServices apiService = RxAndroidApiService.createRetrofitRequest("");
 * SocialLoginRequest requestLogin = new SocialLoginRequest();
 * apiService.socialRequest(requestLogin).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<LoginResponse>(this) {
 *
 * @Override public void onResponse(LoginResponse response) {
 * Log.e(TAG, "onResponse: " );
 * }
 * @Override public void onError(Throwable call, BaseResponse baseResponse) {
 * Log.e(TAG, "onError: "+call );
 * }
 * });
 */
public final class RequestController {

    private RequestController() {
    }

    private static HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();


    /**
     * The Retrofit class generates an implementation of the @AuthWebServices interface.
     *
     * @return @AuthWebServices - which Retrofit turns your HTTP API into a Java interface..
     */
    public static AuthWebServices createRetrofitRequest(boolean isAuth) {
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.readTimeout(1, TimeUnit.MINUTES).connectTimeout(1, TimeUnit.MINUTES);
        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(JacksonConverterFactory.create())
                        .baseUrl(BuildConfig.BASE_URL);
        client.addInterceptor(interceptor);
        if (!isAuth) {
            LogUtils.LOGD("TOKEN", "TOKEN: " + PreferenceUtil.getAuthToken());
            client.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Request newReq =
                            request
                                    .newBuilder()
                                    .addHeader("Token", PreferenceUtil.getAuthToken())
                                    .build();
                    return chain.proceed(newReq);
                }
            });
        }
        builder.client(client.build());
        return builder.build().create(AuthWebServices.class);
    }

    public static AuthWebServices createRetrofitRequestForGet(final boolean isAuth) {
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        final OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.readTimeout(1, TimeUnit.MINUTES).connectTimeout(1, TimeUnit.MINUTES);
        final Retrofit.Builder builder =
                new Retrofit.Builder()
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(JacksonConverterFactory.create())
                        .baseUrl(BuildConfig.BASE_URL);
        client.addInterceptor(interceptor);

        LogUtils.LOGD("TOKEN", "TOKEN: " + PreferenceUtil.getAuthToken());
        client.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request newReq;
                if (!isAuth) {
                   newReq =
                            request
                                    .newBuilder()
                                    .addHeader("Token", PreferenceUtil.getAuthToken()).addHeader("Content-Type", "application/json")
                                    .build();
                }
                else
                {
                    newReq =
                            request
                                    .newBuilder()
                                    .addHeader("Content-Type", "application/json")
                                    .build();
                }

                return chain.proceed(newReq);
            }
        });
        builder.client(client.build());
        return builder.build().create(AuthWebServices.class);
    }
}
