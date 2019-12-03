package com.pixelmarketo.nrh.activity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.pixelmarketo.nrh.BaseActivity;
import com.pixelmarketo.nrh.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HelpActivity extends BaseActivity {

    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.about_us_tv)
    TextView aboutUsTv;
String About_us="<!DOCTYPE html>\n" + "<html>\n" + "<head>\n" + "<title></title>\n" + "</head>\n" + "<body>\n" + "\n" + "<h2>About Us</h2>\n" + "<p><strong>Our vision</strong></p>\n" + "<ol>\n" + "<li>Save time</li>\n" + "<li>Save money</li>\n" + "<li>Anywhere access</li>\n" + "<li>Anytime availability</li>\n" + "<li>Anytime job creation</li>\n" + "</ol>\n" + "<p><strong>Our Aim</strong></p>\n" + "<ol>\n" + "<li>We want to provide you better service at a reasonable price .</li>\n" + "<li>We provide better service in low cost</li>\n" + "<li>We provide you all the service facility in one place</li>\n" + "<li>You can compare the rate of all services through our platform .</li>\n" + "<li>You can book the entire service of your event in one place through our app. That too reasonably priced .</li>\n" + "</ol>\n" + "<p><strong>About us</strong></p>\n" + "<p>NRS event helper</p>\n" + "<p>Your partner in every event.</p>\n" + "<p>We are an online platform facility that helps you throughout the event all the service will get all the things that you need for an event in our app. Every serviceman will get you there at a reasonable price.</p>\n" + "\n" + "</body>\n" + "</html>";
    String Terms_Condition="\n" + "<!DOCTYPE html>\n" + "<html>\n" + "<head>\n" + "<title></title>\n" + "</head>\n" + "<body>\n" + "\n" + "<h2>Terms conditions</h2>\n" + "<p><strong>user</strong></p>\n" + "<ol>\n" + "<li>The service will not be final until the serviceman talks to you .</li>\n" + "<li>Our task is only to introduce you to the serviceman</li>\n" + "<li>Unless you give advance to the serviceman , your service book will not be there .</li>\n" + "<li>You should not book any service to check its price , just check its price .</li>\n" + "<li>Once you have explained the tasks and increase the work, then its charge will have to be given separately .</li>\n" + "</ol>\n" + "<p><strong>Terms conditions</strong></p>\n" + "<p><strong>For service man</strong></p>\n" + "<ol>\n" + "<li>The serviceman will have to pay three percent as per the deal only then the full address will be given to the serviceman for the order.</li>\n" + "<li>If for some reason the user does not get the deal. Three percent will be returned from the serviceman.</li>\n" + "<li>Book your work by looking at your level.</li>\n" + "<li>We will not be responsible for getting money from the user.</li>\n" + "</ol>\n" + "</body>\n" + "</html>";
@Override
    protected int getContentResId() {
        return R.layout.activity_help;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("");
        ButterKnife.bind(this);
        Bundle bundle=getIntent().getExtras();
        if (bundle !=null){
            if (bundle.getString("Check").equals("AboutUs")){
                titleTxt.setText("Help");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    aboutUsTv.setText(Html.fromHtml(About_us, Html.FROM_HTML_MODE_COMPACT));
                } else {
                    aboutUsTv.setText(Html.fromHtml(About_us));
                }
            }else if (bundle.getString("Check").equals("terms_condition")){
                titleTxt.setText("Terms & Condition");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    aboutUsTv.setText(Html.fromHtml(Terms_Condition, Html.FROM_HTML_MODE_COMPACT));
                } else {
                    aboutUsTv.setText(Html.fromHtml(Terms_Condition));
                }
            }
        }

    }
}
