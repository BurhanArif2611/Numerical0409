package com.pixelmarketo.nrh.activity.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.pixelmarketo.nrh.Firebase.Config;
import com.pixelmarketo.nrh.R;
import com.pixelmarketo.nrh.utility.ErrorMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class VenderlistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venderlist);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString("Check").equals("New_Bid_Request")) {
                try {
                    JSONObject jsonObject = new JSONObject(bundle.getString("message"));
                    jsonObject.getString("service");
                    jsonObject.getString("service_id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (bundle.getString("Check").equals("UserDashboardPage")) {
                bundle.getString("service");
                bundle.getString("service_id");
            }
        }
        LocalBroadcastManager.getInstance(this).
                registerReceiver(New_Bid_Request, new IntentFilter(Config.New_Bid_Request));
    }

    private BroadcastReceiver New_Bid_Request = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                try {
                    ErrorMessage.E("New Request" + intent.getStringExtra("message"));
                    JSONObject jsonObject = new JSONObject(intent.getStringExtra("message"));
                    Bundle bundle = new Bundle();


                } catch (Exception e) {
                    e.printStackTrace();
                    ErrorMessage.E("Exception iner" + e.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
                ErrorMessage.E("Exception outer" + e.toString());
            }


        }
    };

}
