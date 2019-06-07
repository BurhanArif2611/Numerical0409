package com.numerical.numerical.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eftimoff.viewpagertransformers.RotateUpTransformer;
import com.eftimoff.viewpagertransformers.ZoomInTransformer;
import com.eftimoff.viewpagertransformers.ZoomOutSlideTransformer;
import com.numerical.numerical.Models.LatestFeed.Example;
import com.numerical.numerical.R;
import com.numerical.numerical.Utility.ErrorMessage;
import com.numerical.numerical.activity.LatestFeedDetailActivity;
import com.numerical.numerical.activity.StandardViewActivity;
import com.numerical.numerical.adapters.Standeredview_Adapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StandredViewFragment extends Fragment {
    View view;
    @BindView(R.id.pager)
    ViewPager pager;
    Unbinder unbinder;
    private Example example;
    TextView current_position_tv;
    private static int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_standred_view, container, false);
        ((LatestFeedDetailActivity) getActivity()).launchFragmentTitle("Standerd");
        unbinder = ButterKnife.bind(this, view);
        current_position_tv=(TextView)view.findViewById(R.id.current_position_tv);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            try {
                example = (Example) getArguments().getSerializable("ItemData");
                Standeredview_Adapter riskAnalysisAdapter = new Standeredview_Adapter(getActivity(), example.getNumerons());
                pager.setAdapter(riskAnalysisAdapter);
                pager.setOffscreenPageLimit(example.getNumerons().size());
                //pager.setPageTransformer(false, new FadePageTransformer());ZoomOutSlideTransformer
                 pager.setPageTransformer(true, new ZoomOutSlideTransformer());
                 position=pager.getCurrentItem()+1;
                 ErrorMessage.E("selected oncrate"+position);
                 current_position_tv.setText(""+position+"/"+example.getNumerons().size());
            } catch (Exception e) {
            }
            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver, new IntentFilter("Standerd"));

        }
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                 position=i+1;
                ErrorMessage.E("selected onPageSelected"+position);
                current_position_tv.setText(""+position+"/"+example.getNumerons().size());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            Log.e("receiverOnStandard", "position" + position);
            Intent intent1 = new Intent("StanderdView");
            Bundle bundle=new Bundle();
            bundle.putString("message", example.getId());
            bundle.putString("position", String.valueOf(position));
            intent1.putExtras(bundle);
            LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent1);
        }
    };

}
