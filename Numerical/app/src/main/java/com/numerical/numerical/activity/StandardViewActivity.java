package com.numerical.numerical.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.numerical.numerical.Models.LatestFeed.Example;
import com.numerical.numerical.R;
import com.numerical.numerical.adapters.Standeredview_Adapter;


import butterknife.BindView;
import butterknife.ButterKnife;

public class StandardViewActivity extends AppCompatActivity {

    @BindView(R.id.pager)
    ViewPager pager;
    private Example example;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_view);
        ButterKnife.bind(this);
        example = (Example) getIntent().getSerializableExtra("ItemData");

        Standeredview_Adapter riskAnalysisAdapter = new Standeredview_Adapter(StandardViewActivity.this, example.getNumerons());
        pager.setAdapter(riskAnalysisAdapter);
        pager.setOffscreenPageLimit(example.getNumerons().size());
        pager.setPageTransformer(false, new FadePageTransformer());
    }
    private class FadePageTransformer implements ViewPager.PageTransformer {
        public void transformPage(final View view, final float position) {
            view.setAlpha(3 - Math.abs(position));


//            iew.set(android.R.anim.slide_out_right,null);
            if (position < 0) {
//                view.animate().x(R.anim.left_to_right).y(0).setDuration(500).start();
                view.setScrollX((int)((float)(view.getWidth()) * position));

            } else if (position > 0) {

                /*Animation animation= AnimationUtils.loadAnimation(RiskAnalysisActivity.this,R.anim.right_to_left );
                view.startAnimation(animation);*/
//                view.animate().x(R.anim.left_to_right).y(0).setDuration(500).start();
                view.setScrollX(-(int) ((float) (view.getWidth()) * -position));
            } else {
                view.setScrollX(0);
            }
           /* Handler h = new Handler();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                }
            }, 500);
*/
        }
    }
}
