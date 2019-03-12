package com.revauc.revolutionbuy.network.retrofit;

import android.app.Activity;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.network.BaseResponse;
import com.revauc.revolutionbuy.ui.BaseActivity;
import com.revauc.revolutionbuy.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.ConnectException;
import java.net.UnknownHostException;

import io.reactivex.observers.DefaultObserver;
import okhttp3.ResponseBody;

/**
 * ant on 18/04/17.
 */

public abstract class DefaultApiObserver<T> extends DefaultObserver<T> {

    private final WeakReference<Activity> ref;


    public DefaultApiObserver(Activity activity) {
        ref = new WeakReference<>(activity);
    }

    public abstract void onResponse(T response);

    public abstract void onError(Throwable call, BaseResponse baseResponse);

    @Override

    public void onNext(T value) {
        onResponse(value);
    }

    @Override
    public void onError(Throwable e) {


        if (e instanceof ConnectException || e instanceof UnknownHostException) {
            if (ref.get() instanceof BaseActivity) {
                ((BaseActivity) ref.get()).showSnakBarFromTop(ref.get().getString(R.string.no_internet), true);
            }
            onError(e, new BaseResponse());
            return;
        }

        if (e instanceof HttpException) {

            if (((HttpException) e).code() == 404 || (((HttpException) e).code() == 500)) {
                ((BaseActivity) ref.get()).hideProgressBar();
                ((BaseActivity) ref.get()).showSnakBarFromTop(ref.get().getString(R.string.server_error), true);
                onError(e, new BaseResponse());
                return;
            }

            if (((HttpException) e).code() == 401) {
                ((BaseActivity) ref.get()).showToast(ref.get().getString(R.string.authentication_req));
                ((BaseActivity) ref.get()).logoutUser();
                return;
            }
            BaseResponse errorParser = null;
            ResponseBody body = ((HttpException) e).response().errorBody();
            Gson gson = new Gson();
            TypeAdapter<BaseResponse> adapter = gson.getAdapter
                    (BaseResponse
                            .class);
            try {
                errorParser =
                        adapter.fromJson(body.string());
                LogUtils.LOGI("retro", "Error:" + errorParser.getMessage());

            } catch (IOException t) {
                e.printStackTrace();
            }
            onError(e, errorParser);
        }

    }

    @Override
    public void onComplete() {

    }


}



