package com.numerical.numerical.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.numerical.numerical.R;
import com.numerical.numerical.Utility.ErrorMessage;
import com.numerical.numerical.database.UserProfileHelper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //ErrorMessage.I_clear(SplashActivity.this, DashBoardActivity.class, null);
                ErrorMessage.I_clear(SplashActivity.this, ExampleActivity.class, null);
               /* if (UserProfileHelper.getInstance().getUserProfileModel().size() > 0) {
                    ErrorMessage.I_clear(SplashActivity.this, DashBoardActivity.class, null);
                } else {
                    ErrorMessage.I_clear(SplashActivity.this, LoginActivity.class, null);
                }*/
                /*if (Build.VERSION.SDK_INT >= 23) {
                    // Marshmallow+
                    permissioncheck();


                } else {
                    // Pre-Marshmallow

                    if (UserProfileHelper.getInstance().getUserProfileModel().size() > 0) {
                        ErrorMessage.I_clear(Splash.this, MapsActivity.class, null);

                    } else {
                        LaunchApp();
                    }
                }*/
            }
        }, 6000);
    }
}
