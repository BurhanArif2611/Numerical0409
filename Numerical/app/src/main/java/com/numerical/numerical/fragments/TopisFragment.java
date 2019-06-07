package com.numerical.numerical.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.numerical.numerical.R;
import com.numerical.numerical.Utility.ApiClient;
import com.numerical.numerical.Utility.Const;
import com.numerical.numerical.Utility.ErrorMessage;
import com.numerical.numerical.Utility.NetworkUtil;
import com.numerical.numerical.activity.DashBoardActivity;
import com.numerical.numerical.adapters.Topics_Adapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TopisFragment extends Fragment {
    View view;
    @BindView(R.id.topics_list_rv)
    RecyclerView topicsListRv;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_topis, container, false);
        unbinder = ButterKnife.bind(this, view);
        ((DashBoardActivity) getActivity()).launchFragmentTitle("Topics");
        //((DashBoardActivity) getActivity()).GetCurrentPosition("");
        GetTopics();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    private void GetTopics() {
        if (NetworkUtil.isNetworkAvailable(getActivity())) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(getActivity());
            Call<ResponseBody> call = ApiClient.getLoadInterface().latest(Const.ENDPOINT.Topics);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            materialDialog.dismiss();
                            JSONArray jsonArray = new JSONArray(response.body().string());
                            System.out.println("===response data Summery:" + jsonArray.toString());
                            Topics_Adapter side_rv_adapter = new Topics_Adapter(getActivity(), jsonArray);
                            LinearLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);
                            topicsListRv.setLayoutManager(linearLayoutManager);
                            topicsListRv.setItemAnimator(new DefaultItemAnimator());
                            topicsListRv.setNestedScrollingEnabled(false);
                            topicsListRv.setAdapter(side_rv_adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            ErrorMessage.E("JSONException" + e.toString());
                            ErrorMessage.T(getActivity(), "Server Error");
                            materialDialog.dismiss();
                        } catch (IOException e) {
                            e.printStackTrace();
                            ErrorMessage.E("IOException" + e.toString());
                            ErrorMessage.T(getActivity(), "Server Error");
                            materialDialog.dismiss();
                        }


                    } else {
                        ErrorMessage.T(getActivity(), "Response not successful");
                        materialDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    ErrorMessage.T(getActivity(), "Response Fail");
                    System.out.println("============update profile fail  :" + t.toString());
                    materialDialog.dismiss();
                }
            });

        } else {
            ErrorMessage.T(getActivity(), this.getString(R.string.no_internet));

        }

    }
}
