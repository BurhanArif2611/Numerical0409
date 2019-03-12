package com.revauc.revolutionbuy.ui.auth;


import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

import com.revauc.revolutionbuy.BuildConfig;
import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ActivityTermsBinding;
import com.revauc.revolutionbuy.ui.BaseActivity;


/**
 * Screen shows Terms of Service.
 */
public class StripeWebActivity extends BaseActivity implements View.OnClickListener {
    private ActivityTermsBinding mBinding;
    String STRIPE_CONNECT_URL = BuildConfig.STRIPE_BASE_URL + BuildConfig.STRIPE_CLIENT_ID + "&redirect_uri=" + BuildConfig.BASE_URL + "stripe-connect";


    public class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
            showProgressBar();
        }

//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//            view.loadUrl(request.getUrl().toString());
//            return super.shouldOverrideUrlLoading(view, request);
//
//        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            hideProgressBar();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_terms);
        mBinding.toolbarTerms.txvToolbarGeneralCenter.setText(R.string.stripe_account);
        mBinding.toolbarTerms.ivToolBarLeft.setImageResource(R.drawable.ic_back_blue);
        mBinding.toolbarTerms.ivToolBarLeft.setOnClickListener(this);
        mBinding.webviewTnc.getSettings().setJavaScriptEnabled(true);
        mBinding.webviewTnc.setWebViewClient(new WebViewClient());
        Log.e("@@@", "loadurl:" + STRIPE_CONNECT_URL);
        mBinding.webviewTnc.loadUrl(STRIPE_CONNECT_URL);
//        mBinding.webviewTnc.post(new Runnable() {
//            @Override
//            public void run() {
//
//                mBinding.webviewTnc.setWebChromeClient(new WebChromeClient() {
//                    @Override
//                    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
//                        Log.d("WebView", consoleMessage.message());
//                        return true;
//                    }
//
//                });
//
//            }
//        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_tool_bar_left:
                onBackPressed();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
