package com.androidboy.directory.future.burhan;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.androidboy.directory.future.R;
import com.androidboy.directory.future.fragment.LiveChatFragment;
import com.androidboy.directory.future.util.Utilities;

public class ChatActivity extends BaseActivity {
    WebView chatwebview;
    ProgressDialog progressDialog;

    @Override
    protected int getContentResId() {
        return R.layout.activity_chat2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("Live Chat");
        chatwebview = (WebView)findViewById(R.id.chatwebview);
        progressDialog = new ProgressDialog(ChatActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        if (Utilities.isConnectingToInternet(ChatActivity.this)) {
            //chatwebview.loadUrl("https://tawk.to/chat/5a6046dd4b401e45400c2f3a/default/?$_tawk_popout=true");
            //chatwebview.loadUrl("https://tawk.to/chat/5b9f5dd0c9abba5796779a85/default");
            chatwebview.loadUrl("https://tawk.to/chat/5b5ecb27df040c3e9e0c1451/default");
            chatwebview.setWebViewClient(new myWebClient());
            WebSettings webSettings = chatwebview.getSettings();
            webSettings.setJavaScriptEnabled(true);
        }
        else {
            Toast.makeText(ChatActivity.this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();

        }

    }

    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            progressDialog.show();

            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            progressDialog.dismiss();

        }

    }
}
