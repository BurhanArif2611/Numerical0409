package com.pixelmarketo.nrh.activity.user;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.pixelmarketo.nrh.BaseActivity;
import com.pixelmarketo.nrh.R;
import com.pixelmarketo.nrh.adapter.Vender_list_Adapter;
import com.pixelmarketo.nrh.adapter.vender.NewService_adapter;
import com.pixelmarketo.nrh.database.UserProfileHelper;
import com.pixelmarketo.nrh.models.vender.service.Example;
import com.pixelmarketo.nrh.models.vender.service.Result;
import com.pixelmarketo.nrh.utility.AppConfig;
import com.pixelmarketo.nrh.utility.ErrorMessage;
import com.pixelmarketo.nrh.utility.LoadInterface;
import com.pixelmarketo.nrh.utility.NetworkUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryServiceActivity extends BaseActivity {

    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.vender_service_rc)
    RecyclerView venderServiceRc;
    ArrayList<String> stringArrayList = new ArrayList<>();
    @BindView(R.id.select_subservice_spinner)
    Spinner selectSubserviceSpinner;
    Example example;
    public String Service="",Rejected_id="";

    @Override
    protected int getContentResId() {
        return R.layout.activity_history_service;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("History");
        ButterKnife.bind(this);
        getPendingBidList();

    }

    private void getPendingBidList() {
        if (NetworkUtil.isNetworkAvailable(HistoryServiceActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(HistoryServiceActivity.this);
            ErrorMessage.E("request_by_vendor_id>>" + UserProfileHelper.getInstance().getUserProfileModel().get(0).getUid());
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.service_bid_user(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUid());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            materialDialog.dismiss();
                            Gson gson = new Gson();
                            JSONObject object = null;
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("getAllOrders  response" + object.toString());
                            if (object.getString("status").equals("200")) {
                                String Allresponse = object.toString();
                                 example = gson.fromJson(Allresponse, Example.class);
                                if (example.getResult().size() > 0) {
                                    for (int i = 0; i < example.getResult().size(); i++) {
                                        stringArrayList.add(example.getResult().get(i).getService());
                                    }
                                    ArrayAdapter aa = new ArrayAdapter(HistoryServiceActivity.this, android.R.layout.simple_spinner_item, stringArrayList);
                                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    //Setting the ArrayAdapter data on the Spinner
                                    selectSubserviceSpinner.setAdapter(aa);
                                    selectSubserviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            Service = example.getResult().get(position).getId();
                                            getVenderList(example.getResult().get(position).getId());
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });
                                }
                            } else {
                                ErrorMessage.T(HistoryServiceActivity.this, object.getString("message"));
                            }
                        } catch (Exception e) {

                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("Exception" + e.toString());
                        }
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    materialDialog.dismiss();

                }
            });


        } else {

            ErrorMessage.T(HistoryServiceActivity.this, "Internet Not Found! ");
        }
    }
    public void Accepted_bid(String service_id,String rejected_id){
        Rejected_id=rejected_id;
        getVenderList(service_id);
    }
    private void getVenderList(String service_id) {
        if (NetworkUtil.isNetworkAvailable(HistoryServiceActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(HistoryServiceActivity.this);
            ErrorMessage.E("request_by_vendor_id>>" + UserProfileHelper.getInstance().getUserProfileModel().get(0).getUid()+">>"+service_id);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.service_bid(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUid(),service_id);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            materialDialog.dismiss();
                            Gson gson = new Gson();
                            JSONObject object = null;
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("getVenderList  response" + object.toString());
                            if (object.getString("status").equals("200")) {
                                String Allresponse = object.toString();
                                com.pixelmarketo.nrh.models.VenderList_models.Example example = gson.fromJson(Allresponse, com.pixelmarketo.nrh.models.VenderList_models.Example.class);
                                if (example.getResult().size() > 0) {
                                    venderServiceRc.setVisibility(View.VISIBLE);
                                    Collections.reverse(example.getResult());
                                    Vender_list_Adapter myOrderAdapter = new Vender_list_Adapter(HistoryServiceActivity.this, example.getResult(),Rejected_id);
                                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(HistoryServiceActivity.this, RecyclerView.VERTICAL, false);
                                    venderServiceRc.setLayoutManager(mLayoutManager);
                                    venderServiceRc.setItemAnimator(new DefaultItemAnimator());
                                    venderServiceRc.setAdapter(myOrderAdapter);
                                    myOrderAdapter.notifyDataSetChanged();
                                }else {
                                    venderServiceRc.setVisibility(View.GONE);
                                    ErrorMessage.T(HistoryServiceActivity.this, "No Data Found!");
                                }
                            } else {
                                ErrorMessage.T(HistoryServiceActivity.this, object.getString("message"));
                            }
                        } catch (Exception e) {

                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("Exception" + e.toString());
                        }
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    materialDialog.dismiss();

                }
            });


        } else {

            ErrorMessage.T(HistoryServiceActivity.this, "Internet Not Found! ");
        }
    }

}
