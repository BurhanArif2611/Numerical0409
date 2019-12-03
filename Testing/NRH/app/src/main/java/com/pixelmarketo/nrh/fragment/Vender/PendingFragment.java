package com.pixelmarketo.nrh.fragment.Vender;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.pixelmarketo.nrh.Firebase.Config;
import com.pixelmarketo.nrh.R;
import com.pixelmarketo.nrh.UserDashboardActivity;
import com.pixelmarketo.nrh.VenderDashboardActivity;
import com.pixelmarketo.nrh.activity.BidRequestActivity;
import com.pixelmarketo.nrh.adapter.Service_Adapter;
import com.pixelmarketo.nrh.adapter.Vender_services_adapter;
import com.pixelmarketo.nrh.adapter.vender.NewService_adapter;
import com.pixelmarketo.nrh.database.UserProfileHelper;

import com.pixelmarketo.nrh.models.Service_Models.Result;
import com.pixelmarketo.nrh.models.vender.service.Example;
import com.pixelmarketo.nrh.utility.AppConfig;
import com.pixelmarketo.nrh.utility.ErrorMessage;
import com.pixelmarketo.nrh.utility.LoadInterface;
import com.pixelmarketo.nrh.utility.NetworkUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PendingFragment extends Fragment {
    View view;
    @BindView(R.id.pending_bid_list_rtv)
    RecyclerView pendingBidListRtv;

    @BindView(R.id.select_subservice_spinner)
    Spinner select_subservice_spinner;
    @BindView(R.id.select_services_tv)
    LinearLayout select_services_tv;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    Unbinder unbinder;
    String Check = "", SubService = "";
    Result result;
    ArrayList<String> stringArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pending, container, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            result = (Result) bundle.getSerializable("result");
            Check = bundle.getString("Check");
            ErrorMessage.E("Pending"+result.getSubservice().size());
            if (result.getSubservice().size() > 0) {
                stringArrayList.clear();
                for (int i = 0; i < result.getSubservice().size(); i++) {
                    ErrorMessage.E("Pending"+result.getSubservice().get(i).getSubServiceName());
                    stringArrayList.add(result.getSubservice().get(i).getSubServiceName());
                }
                ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, stringArrayList);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //Setting the ArrayAdapter data on the Spinner
                select_subservice_spinner.setAdapter(aa);
                select_services_tv.setVisibility(View.GONE);
            } else {
                select_services_tv.setVisibility(View.GONE);
            }
        }
        select_subservice_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SubService = result.getSubservice().get(position).getId();
                getPendingBidList(result.getServiceId(), Check);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        swiperefresh.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark, android.R.color.holo_orange_dark, android.R.color.holo_blue_dark);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPendingBidList(result.getServiceId(), Check);
            }
        });
        getPendingBidList(result.getServiceId(), Check);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getPendingBidList(String serviceId, String check) {
        if (NetworkUtil.isNetworkAvailable(getActivity())) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(getActivity());
            ErrorMessage.E("request_by_vendor_id>>" + UserProfileHelper.getInstance().getUserProfileModel().get(0).getUid() + ">>" + serviceId + ">>" + check+">>"+SubService);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.request_by_vendor_id(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUid(), serviceId, check, SubService);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            materialDialog.dismiss();
                            swiperefresh.setRefreshing(false);
                            Gson gson = new Gson();
                            JSONObject object = null;
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("getAllOrders Pending response" + object.toString());
                            if (object.getString("status").equals("200")) {
                                String Allresponse = object.toString();
                                Example example = gson.fromJson(Allresponse, Example.class);
                                if (example.getResult().size() > 0) {
                                    pendingBidListRtv.setVisibility(View.VISIBLE);
                                    Collections.reverse(example.getResult());
                                    NewService_adapter myOrderAdapter = new NewService_adapter(getActivity(), example.getResult(),"Pending");
                                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                                    pendingBidListRtv.setLayoutManager(mLayoutManager);
                                    pendingBidListRtv.setItemAnimator(new DefaultItemAnimator());
                                    pendingBidListRtv.setAdapter(myOrderAdapter);
                                    myOrderAdapter.notifyDataSetChanged();
                                }else {
                                    pendingBidListRtv.setVisibility(View.GONE);
                                    ErrorMessage.T(getActivity(), "No Data Found!");
                                }

                            } else {
                                ErrorMessage.T(getActivity(), object.getString("message"));
                            }
                        } catch (Exception e) {
                            swiperefresh.setRefreshing(false);
                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("Exception" + e.toString());
                        }
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    materialDialog.dismiss();
                    swiperefresh.setRefreshing(false);
                }
            });


        } else {
            swiperefresh.setRefreshing(false);
            ErrorMessage.T(getActivity(), "Internet Not Found! ");
        }

    }

}
