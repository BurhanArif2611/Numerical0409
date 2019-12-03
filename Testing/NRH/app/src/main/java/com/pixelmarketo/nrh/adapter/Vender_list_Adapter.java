package com.pixelmarketo.nrh.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pixelmarketo.nrh.R;
import com.pixelmarketo.nrh.VenderDashboardActivity;
import com.pixelmarketo.nrh.Vender_RegistrationActivity;
import com.pixelmarketo.nrh.activity.user.HistoryServiceActivity;
import com.pixelmarketo.nrh.models.VenderList_models.Result;
import com.pixelmarketo.nrh.utility.AppConfig;
import com.pixelmarketo.nrh.utility.ErrorMessage;
import com.pixelmarketo.nrh.utility.LoadInterface;
import com.pixelmarketo.nrh.utility.NetworkUtil;


import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Vender_list_Adapter extends RecyclerView.Adapter<Vender_list_Adapter.MyViewHolder> {
    View view;
    Context activity;
    List<Result> arrayList;
    String Rejected_id = "";

    public Vender_list_Adapter(Context policyActivity, List<Result> arrayList, String Rejected_id) {
        this.activity = policyActivity;
        this.arrayList = arrayList;
        this.Rejected_id = Rejected_id;

    }

    @NonNull
    @Override
    public Vender_list_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vender_list_adapter, parent, false);
        return new Vender_list_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vender_list_Adapter.MyViewHolder holder, final int position) {
        final Result userInfo = arrayList.get(position);
        holder.vender_name_tv.setText(userInfo.getEventName() + " (" + userInfo.getServices() + " )");
        holder.bid_amount_tv.setText(" ₹ " + userInfo.getBidAmount());
        holder.featured_tv.setText(userInfo.getTitle());
        holder.provider_name_tv.setText(userInfo.getFullname());
        if (userInfo.getRating().equals("")) {
        } else {
            holder.rating_bar.setRating(Float.parseFloat(userInfo.getRating()));
        }

        if (userInfo.getPaymentStatus().equals("0")) {
            if (Rejected_id.equals(userInfo.getRequestId())) {
                holder.accepted_buttons_layout.setVisibility(View.GONE);
                holder.wait_for_venter.setVisibility(View.GONE);
            } else {
            }
            if (userInfo.getAcceptRejectBid().equals("0")) {
                holder.accepted_buttons_layout.setVisibility(View.VISIBLE);
                holder.phone_call_whatsup_layout.setVisibility(View.GONE);
                holder.wait_for_venter.setVisibility(View.GONE);
            } else {
                holder.accepted_buttons_layout.setVisibility(View.GONE);
                holder.phone_call_whatsup_layout.setVisibility(View.GONE);
                holder.wait_for_venter.setVisibility(View.VISIBLE);
            }
        } else if (userInfo.getPaymentStatus().equals("1")) {
            holder.accepted_buttons_layout.setVisibility(View.GONE);
            holder.wait_for_venter.setVisibility(View.GONE);
            holder.phone_call_whatsup_layout.setVisibility(View.VISIBLE);
        }
        Glide.with(activity).load(arrayList.get(position).getImage()).into(holder.paper_img);

        holder.accept_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtil.isNetworkAvailable(activity)) {
                    final Dialog materialDialog = ErrorMessage.initProgressDialog(activity);
                    LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
                    Call<ResponseBody> call = apiService.approve_bid_by_user(userInfo.getId());
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            ErrorMessage.E("Response" + response.code());
                            if (response.isSuccessful()) {
                                materialDialog.dismiss();
                                JSONObject object = null;
                                try {
                                    object = new JSONObject(response.body().string());
                                    ErrorMessage.E("comes in cond" + object.toString());
                                    if (object.getString("status").equals("200")) {
                                        ErrorMessage.E("comes in if cond" + object.toString());
                                        ErrorMessage.T(activity, object.getString("message"));
                                        holder.accepted_buttons_layout.setVisibility(View.GONE);
                                        holder.wait_for_venter.setVisibility(View.VISIBLE);
                                        ((HistoryServiceActivity) activity).Accepted_bid(userInfo.getServiceId(), userInfo.getRequestId());
                                    } else {
                                        ErrorMessage.E("comes in else");
                                        ErrorMessage.T(activity, object.getString("message"));
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    materialDialog.dismiss();
                                    ErrorMessage.E("Exceptions" + e);
                                }
                            } else {
                                materialDialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            t.printStackTrace();
                            ErrorMessage.E("Falure login" + t);
                            materialDialog.dismiss();

                        }
                    });
                } else {
                    ErrorMessage.T(activity, "No Internet");
                }
            }
        });
        holder.phone_call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!userInfo.getContact().equals("")) {
                    try {
                        Intent callSupportIntent = new Intent(Intent.ACTION_VIEW);
                        callSupportIntent.setData(Uri.parse("tel:" + userInfo.getContact()));
                        activity.startActivity(callSupportIntent);
                    } catch (Exception e) {
                    }
                } else {
                    ErrorMessage.T(activity, "Contact Number not Found!");
                }
            }
        });
        holder.whatsup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!userInfo.getContact().equals("")) {
                    try {
                        String toNumber = "+91 " + userInfo.getContact();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + toNumber));
                        activity.startActivity(intent);
                    } catch (Exception e) {
                        ErrorMessage.T(activity, " Please Install a What's Up ");
                        Log.e("Exaception e", "" + e);
                    }
                } else {
                    ErrorMessage.T(activity, "Contact Number not Found!");
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView vender_name_tv, vender_address_tv, bid_amount_tv, featured_tv, decline_tv, accept_tv, wait_for_venter,provider_name_tv;
        ImageView paper_img, verified_img;
        RatingBar rating_bar;
        LinearLayout accepted_buttons_layout, phone_call_whatsup_layout;
        ImageButton phone_call_btn, whatsup_btn;

        public MyViewHolder(View itemView) {
            super(itemView);
            vender_name_tv = itemView.findViewById(R.id.vender_name_tv);
            vender_address_tv = itemView.findViewById(R.id.vender_address_tv);
            bid_amount_tv = itemView.findViewById(R.id.bid_amount_tv);
            featured_tv = itemView.findViewById(R.id.featured_tv);
            decline_tv = itemView.findViewById(R.id.decline_tv);
            accept_tv = itemView.findViewById(R.id.accept_tv);
            paper_img = itemView.findViewById(R.id.paper_img);
            rating_bar = itemView.findViewById(R.id.rating_bar);
            phone_call_btn = itemView.findViewById(R.id.phone_call_btn);
            whatsup_btn = itemView.findViewById(R.id.whatsup_btn);
            wait_for_venter = itemView.findViewById(R.id.wait_for_venter);
            provider_name_tv = itemView.findViewById(R.id.provider_name_tv);
            accepted_buttons_layout = itemView.findViewById(R.id.accepted_buttons_layout);
            phone_call_whatsup_layout = itemView.findViewById(R.id.phone_call_whatsup_layout);
        }
    }
}
