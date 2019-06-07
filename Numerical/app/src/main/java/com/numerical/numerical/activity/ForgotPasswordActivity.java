package com.numerical.numerical.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.numerical.numerical.R;
import com.numerical.numerical.Utility.ErrorMessage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPasswordActivity extends AppCompatActivity {

    @BindView(R.id.submit_tv)
    TextView submitTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.submit_tv)
    public void onClick() {
        ErrorMessage.I(ForgotPasswordActivity.this, NewPasswordActivity.class,null);
    }
}
