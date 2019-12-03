package com.pixelmarketo.nrh;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.pixelmarketo.nrh.utility.ErrorMessage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServiceTypeActivity extends AppCompatActivity {

    @BindView(R.id.service_man_card)
    CardView serviceManCard;
    @BindView(R.id.user_card)
    CardView userCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_type);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.service_man_card, R.id.user_card})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.service_man_card:
                ErrorMessage.I_clear(ServiceTypeActivity.this, VenderLoginActivity.class, null);
                break;
            case R.id.user_card:
                ErrorMessage.I_clear(ServiceTypeActivity.this, LoginActivity.class, null);
                break;
        }
    }
}
