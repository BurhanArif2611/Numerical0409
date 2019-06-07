package com.numerical.numerical.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.numerical.numerical.R;
import com.numerical.numerical.Utility.ApiClient;
import com.numerical.numerical.Utility.Const;
import com.numerical.numerical.Utility.ErrorMessage;
import com.numerical.numerical.Utility.NetworkUtil;
import com.numerical.numerical.adapters.Preference_topic_Adapter;
import com.numerical.numerical.adapters.Topics_Adapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PregerenceActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.topics_list_rv)
    RecyclerView topicsListRv;

    @Override
    protected int getContentResId() {
        return R.layout.activity_pregerence;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("PREFERENCES");
        ButterKnife.bind(this);
        toolbar.setTitleTextAppearance(this, R.style.RobotoBoldTextAppearance);
        GetTopics();
    }
    private void GetTopics() {
        if (NetworkUtil.isNetworkAvailable(PregerenceActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(PregerenceActivity.this);
            Call<ResponseBody> call = ApiClient.getLoadInterface().latest(Const.ENDPOINT.Topics);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            materialDialog.dismiss();
                            JSONArray jsonArray = new JSONArray(response.body().string());
                            System.out.println("===response data Summery:" + jsonArray.toString());
                            Preference_topic_Adapter side_rv_adapter = new Preference_topic_Adapter(PregerenceActivity.this, jsonArray);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PregerenceActivity.this);
                            topicsListRv.setLayoutManager(linearLayoutManager);
                            topicsListRv.setItemAnimator(new DefaultItemAnimator());
                            topicsListRv.setNestedScrollingEnabled(false);
                            topicsListRv.setAdapter(side_rv_adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            ErrorMessage.E("JSONException" + e.toString());
                            ErrorMessage.T(PregerenceActivity.this, "Server Error");
                            materialDialog.dismiss();
                        } catch (IOException e) {
                            e.printStackTrace();
                            ErrorMessage.E("IOException" + e.toString());
                            ErrorMessage.T(PregerenceActivity.this, "Server Error");
                            materialDialog.dismiss();
                        }


                    } else {
                        ErrorMessage.T(PregerenceActivity.this, "Response not successful");
                        materialDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    ErrorMessage.T(PregerenceActivity.this, "Response Fail");
                    System.out.println("============update profile fail  :" + t.toString());
                    materialDialog.dismiss();
                }
            });

        } else {
            ErrorMessage.T(PregerenceActivity.this, this.getString(R.string.no_internet));

        }

    }
}
