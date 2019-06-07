package com.numerical.numerical.fragments.Search;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.numerical.numerical.R;
import com.numerical.numerical.Utility.ApiClient;
import com.numerical.numerical.Utility.Const;
import com.numerical.numerical.Utility.ErrorMessage;
import com.numerical.numerical.Utility.NetworkUtil;
import com.numerical.numerical.adapters.feed_Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoriesFragment extends Fragment {
    View view;

    public static RecyclerView vehicalRateListRv;
    Unbinder unbinder;
    private JSONArray jsonArray;
    public static StaggeredGridLayoutManager gridLayoutManager;
    private boolean loading = true;
    int pastVisiblesItems;
    int visibleItemCount;
    int totalItemCount;  int count = 0;
    ArrayList<String> stringArrayList = new ArrayList<>();
    private feed_Adapter side_rv_adapter;
    private String Name = "";
    private String message;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_categories, container, false);
        unbinder = ButterKnife.bind(this, view);
        vehicalRateListRv=(RecyclerView)view.findViewById(R.id.vehical_rate_list_rv);
        stringArrayList.clear();
        vehicalRateListRv.setVisibility(View.VISIBLE);
        gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver, new IntentFilter("Search"));

        vehicalRateListRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = gridLayoutManager.getChildCount();
                    totalItemCount = gridLayoutManager.getItemCount();
                    int[] firstVisibleItems = null;
                    firstVisibleItems = gridLayoutManager.findFirstVisibleItemPositions(firstVisibleItems);
                    ErrorMessage.E("firstVisibleItems firstVisibleItems"+firstVisibleItems.length);
                    if (firstVisibleItems != null && firstVisibleItems.length > 0) {
                        pastVisiblesItems = firstVisibleItems[0];
                    }

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            Log.e("...", "Last Item Wow !");
                            //Do pagination.. i.e. fetch new data
                            count++;
                            count = count + 9;
                            GetLatestFeedWithPagination(count,message);
                        }
                    }
                }
            }
        });
        return view;
    }
    private void GetLatestFeed(int Count,String Search) {
        ErrorMessage.E("Count" + Count);
        if (NetworkUtil.isNetworkAvailable(getActivity())) {
            Call<ResponseBody> call = null;
            final Dialog materialDialog = ErrorMessage.initProgressDialog(getActivity());
            call = ApiClient.getLoadInterface().latest(Const.ENDPOINT.Search +Search+ "/10/" + Count);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            materialDialog.dismiss();
                            jsonArray = new JSONArray(response.body().string());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                stringArrayList.add(jsonObject.toString());
                            }
                            side_rv_adapter = new feed_Adapter(getActivity(), jsonArray, stringArrayList, Name);
                            vehicalRateListRv.setLayoutManager(gridLayoutManager);
                            vehicalRateListRv.setItemAnimator(new DefaultItemAnimator());
                            vehicalRateListRv.setNestedScrollingEnabled(false);
                            vehicalRateListRv.setAdapter(side_rv_adapter);
                            side_rv_adapter.notifyDataSetChanged();
                            ErrorMessage.E("firstVisibleItems GetLatestFeed"+stringArrayList.size());
                            loading = true;
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
    private void GetLatestFeedWithPagination(int Count,String Search) {
        ErrorMessage.E("Count GetLatestFeedWithPagination" + Count);
        if (NetworkUtil.isNetworkAvailable(getActivity())) {
            // final Dialog materialDialog = ErrorMessage.initProgressDialog(getActivity());
            Call<ResponseBody> call = null;
            call = ApiClient.getLoadInterface().latest(Const.ENDPOINT.Search +Search+ "/10/" + Count);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            //  materialDialog.dismiss();
                            jsonArray = new JSONArray(response.body().string());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                stringArrayList.add(jsonObject.toString());
                                side_rv_adapter.notifyDataSetChanged();
                            }
                            ErrorMessage.E("firstVisibleItems"+stringArrayList.size());
                           /* feed_Adapter side_rv_adapter = new feed_Adapter(getActivity(), jsonArray, stringArrayList,Name);
                            gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                            vehicalRateListRv.setLayoutManager(gridLayoutManager);
                            vehicalRateListRv.setItemAnimator(new DefaultItemAnimator());
                            vehicalRateListRv.setNestedScrollingEnabled(false);
                            vehicalRateListRv.setAdapter(side_rv_adapter);
                            side_rv_adapter.notifyDataSetChanged();*/
                            loading = true;

                        } catch (JSONException e) {
                            e.printStackTrace();
                            ErrorMessage.E("JSONException" + e.toString());
                            ErrorMessage.T(getActivity(), "Server Error");
                            // materialDialog.dismiss();
                        } catch (IOException e) {
                            e.printStackTrace();
                            ErrorMessage.E("IOException" + e.toString());
                            ErrorMessage.T(getActivity(), "Server Error");
                            //materialDialog.dismiss();
                        }


                    } else {
                        ErrorMessage.T(getActivity(), "Response not successful");
                        // materialDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    ErrorMessage.T(getActivity(), "Response Fail");
                    System.out.println("============update profile fail  :" + t.toString());
                    //materialDialog.dismiss();
                }
            });

        } else {
            ErrorMessage.T(getActivity(), this.getString(R.string.no_internet));

        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            count = 0;  stringArrayList.clear();
            ErrorMessage.E("BroadcastReceiver " +intent.getStringExtra("message"));
            message = intent.getStringExtra("message");
            GetLatestFeed(count,message);

        }
    };
    @Override public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        try {
            ErrorMessage.E("Category fragment is visible");
            count = 0;  stringArrayList.clear();
            vehicalRateListRv.setVisibility(View.VISIBLE);
        }catch (Exception e){}

    }

}
