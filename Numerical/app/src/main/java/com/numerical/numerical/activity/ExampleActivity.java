package com.numerical.numerical.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.numerical.numerical.R;
import com.numerical.numerical.Utility.ErrorMessage;
import com.numerical.numerical.adapters.Example_Adapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExampleActivity extends AppCompatActivity {

    @BindView(R.id.pager)
    ViewPager pager;
    ArrayList<String> stringArrayList = new ArrayList<>();
    @BindView(R.id.add_more_btn)
    FloatingActionButton addMoreBtn;
    LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        ButterKnife.bind(this);
        stringArrayList.add("");
         dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        Example_Adapter riskAnalysisAdapter = new Example_Adapter(ExampleActivity.this, stringArrayList);
        pager.setAdapter(riskAnalysisAdapter);
        pager.setOffscreenPageLimit(stringArrayList.size());
        layouts = new int[]{stringArrayList.size()};
        addBottomDots(0);
        //pager.addOnPageChangeListener(viewPagerPageChangeListener);

    }

    @OnClick(R.id.add_more_btn)
    public void onClick() {
        stringArrayList.add("");
        Example_Adapter riskAnalysisAdapter = new Example_Adapter(ExampleActivity.this, stringArrayList);
        pager.setAdapter(riskAnalysisAdapter);
        pager.setOffscreenPageLimit(stringArrayList.size());
        layouts = new int[]{stringArrayList.size()};
        addBottomDots(stringArrayList.size()-1);
    }
    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];
        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }
   /* ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                btnNext.setText(getString(R.string.start));
                btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };*/
}
