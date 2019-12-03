package com.pixelmarketo.nrh.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.pixelmarketo.nrh.BaseActivity;
import com.pixelmarketo.nrh.R;
import com.pixelmarketo.nrh.adapter.vender.Vender_history_adapter;
import com.pixelmarketo.nrh.database.UserProfileHelper;
import com.pixelmarketo.nrh.models.Vender_history_models.Example;
import com.pixelmarketo.nrh.utility.AppConfig;
import com.pixelmarketo.nrh.utility.ErrorMessage;
import com.pixelmarketo.nrh.utility.LoadInterface;
import com.pixelmarketo.nrh.utility.NetworkUtil;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Vender_HistoryActivity extends BaseActivity {

    @BindView(R.id.history_OrderRv)
    RecyclerView historyOrderRv;
    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.tool_barLayout)
    RelativeLayout toolBarLayout;

    @Override
    protected int getContentResId() {
        return R.layout.activity_vender__history;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("");
        ButterKnife.bind(this);
        titleTxt.setText("History ");
         getAllServices();
    }

    private void getAllServices() {
        if (NetworkUtil.isNetworkAvailable(Vender_HistoryActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(Vender_HistoryActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.approve_bid_list(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUid());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            materialDialog.dismiss();
                            Gson gson = new Gson();
                            JSONObject object = null;
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("getAllServices  response" + object.toString());
                            if (object.getString("status").equals("200")) {
                                String Allresponse = object.toString();
                                Example example = gson.fromJson(Allresponse, Example.class);

                                if (example.getResult().size() > 0) {
                                    Vender_history_adapter myOrderAdapter = new Vender_history_adapter(Vender_HistoryActivity.this, example.getResult());
                                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(Vender_HistoryActivity.this);
                                    historyOrderRv.setLayoutManager(mLayoutManager);
                                    historyOrderRv.setItemAnimator(new DefaultItemAnimator());
                                    historyOrderRv.setAdapter(myOrderAdapter);
                                    myOrderAdapter.notifyDataSetChanged();
                                }

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
            ErrorMessage.T(Vender_HistoryActivity.this, "Internet Not Found! ");
        }

    }
}
