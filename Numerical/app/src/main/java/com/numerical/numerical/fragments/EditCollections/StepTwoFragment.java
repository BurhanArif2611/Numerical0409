package com.numerical.numerical.fragments.EditCollections;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.numerical.numerical.Models.Topics.Example;
import com.numerical.numerical.R;
import com.numerical.numerical.Utility.ApiClient;
import com.numerical.numerical.Utility.Const;
import com.numerical.numerical.Utility.ErrorMessage;
import com.numerical.numerical.Utility.NetworkUtil;
import com.numerical.numerical.activity.PregerenceActivity;
import com.numerical.numerical.adapters.Preference_topic_Adapter;
import com.numerical.numerical.fragments.NewNumeronsFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StepTwoFragment extends Fragment {
    View view;
    @BindView(R.id.next_btn)
    TextView nextBtn;
    Unbinder unbinder;
    @BindView(R.id.select_topics_spinner)
    Spinner selectTopicsSpinner;
    ArrayList<String> stringArrayList=new ArrayList<>();
    private FragmentTransaction mFragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_step_two, container, false);
        unbinder = ButterKnife.bind(this, view);
        GetTopics();
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.next_btn)
    public void onClick() {
        new NewNumeronsFragment().CallStepThird();
        StepThirdFragment newGamefragment = new StepThirdFragment();
        mFragmentTransaction = getFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.stepsfragmentsLayout, newGamefragment);
        mFragmentTransaction.commit();
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

                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                stringArrayList.add(jsonObject.getString("topic"));
                            }
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, stringArrayList);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            selectTopicsSpinner.setAdapter(dataAdapter);

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
