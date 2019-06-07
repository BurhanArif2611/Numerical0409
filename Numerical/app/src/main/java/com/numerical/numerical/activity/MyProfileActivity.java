package com.numerical.numerical.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.numerical.numerical.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyProfileActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;

    @Override
    protected int getContentResId() {
        return R.layout.activity_my_profile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("MY PROFILE");
        ButterKnife.bind(this);
        toolbar.setTitleTextAppearance(this, R.style.RobotoBoldTextAppearance);
    }
}
