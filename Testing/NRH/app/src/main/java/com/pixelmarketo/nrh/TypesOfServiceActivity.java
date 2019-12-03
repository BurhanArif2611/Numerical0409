package com.pixelmarketo.nrh;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pixelmarketo.nrh.adapter.Service_Adapter;
import com.pixelmarketo.nrh.adapter.SubService_Adapter;
import com.pixelmarketo.nrh.models.Service_Models.Result;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TypesOfServiceActivity extends BaseActivity {
    Result result;
    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.sliderImage)
    ImageView sliderImage;
    @BindView(R.id.item_img)
    ImageView itemImg;
    @BindView(R.id.types_of_service_rc)
    RecyclerView typesOfServiceRc;

    @Override
    protected int getContentResId() {
        return R.layout.activity_types_of_service;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("");
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            result = (Result) bundle.getSerializable("AllData");
            titleTxt.setText(result.getServiceName());
            Glide.with(TypesOfServiceActivity.this).load(result.getServiceImage()).into(itemImg);
            Glide.with(TypesOfServiceActivity.this).load(result.getBanners_image()).into(sliderImage);
            if (result.getSubservice().size() > 0) {
                SubService_Adapter myOrderAdapter = new SubService_Adapter(TypesOfServiceActivity.this, result.getSubservice(),result);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(TypesOfServiceActivity.this);
                typesOfServiceRc.setLayoutManager(mLayoutManager);
                typesOfServiceRc.setItemAnimator(new DefaultItemAnimator());
                typesOfServiceRc.setAdapter(myOrderAdapter);
                myOrderAdapter.notifyDataSetChanged();
            }
        }
    }

}
