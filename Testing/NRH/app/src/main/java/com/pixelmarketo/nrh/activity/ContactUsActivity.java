package com.pixelmarketo.nrh.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.pixelmarketo.nrh.BaseActivity;
import com.pixelmarketo.nrh.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactUsActivity extends BaseActivity {

    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.firstContactBtn)
    TextView firstContactBtn;
    @BindView(R.id.vikasVyasContact)
    TextView vikasVyasContact;

    @Override
    protected int getContentResId() {
        return R.layout.activity_contact_us;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("");
        ButterKnife.bind(this);
        titleTxt.setText("Contact Us");
    }

    @OnClick({R.id.firstContactBtn, R.id.vikasVyasContact})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.firstContactBtn:
                try {
                    Intent callIntent = new Intent(Intent.ACTION_VIEW);
                    callIntent.setData(Uri.parse("tel:" + "+91 9770606839"));
                    startActivity(callIntent);
                }catch (Exception e){}
                break;
            case R.id.vikasVyasContact:
                try {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/html");
                    intent.putExtra(Intent.EXTRA_EMAIL, "nrseventhelper@gmail.com");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "NRS Support ");
                    startActivity(Intent.createChooser(intent, "Send Email"));
                } catch (Exception e) {
                }
                break;
        }
    }
}
