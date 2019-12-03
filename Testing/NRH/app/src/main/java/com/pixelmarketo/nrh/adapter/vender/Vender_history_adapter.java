package com.pixelmarketo.nrh.adapter.vender;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pixelmarketo.nrh.R;
import com.pixelmarketo.nrh.VenderDashboardActivity;
import com.pixelmarketo.nrh.activity.EventDetailActivity;

import com.pixelmarketo.nrh.models.Vender_history_models.Result;
import com.pixelmarketo.nrh.utility.AppConfig;
import com.pixelmarketo.nrh.utility.ErrorMessage;
import com.pixelmarketo.nrh.utility.LoadInterface;
import com.pixelmarketo.nrh.utility.NetworkUtil;
import com.pixelmarketo.nrh.utility.UserAccount;

import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Vender_history_adapter extends RecyclerView.Adapter<Vender_history_adapter.MyViewHolder> {
    View view;
    Context activity;
    List<Result> arrayList;


    public Vender_history_adapter(Context policyActivity, List<Result> arrayList) {
        this.activity = policyActivity;
        this.arrayList = arrayList;

    }

    @NonNull
    @Override
    public Vender_history_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vender_history_adapter, parent, false);
        return new Vender_history_adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vender_history_adapter.MyViewHolder holder, final int position) {
        final Result userInfo = arrayList.get(position);
        holder.event_name_tv.setText(userInfo.getEventName());
        holder.event_start_date_tv.setText(userInfo.getToDate());
        holder.event_end_date_tv.setText(userInfo.getFromDate());
        holder.customer_name_tv.setText(userInfo.getFullname());
        holder.service_title_tv.setText(userInfo.getServices());
        holder.event_address_tv.setText(userInfo.getCity() + "," + userInfo.getTehsil());
        if (!userInfo.getBidAmount().equals("")) {
            holder.bid_layout.setVisibility(View.VISIBLE);
            holder.bid_tv.setText(userInfo.getBidAmount());
        } else {
            holder.bid_layout.setVisibility(View.GONE);
        }

        if (userInfo.getServices().toLowerCase().contains("bands")) {
            holder.according_service_title_tv.setVisibility(View.GONE);
            holder.according_service_title_value_tv.setVisibility(View.GONE);
        } else if (userInfo.getServices().toLowerCase().contains("dj")) {
            holder.according_service_title_tv.setVisibility(View.GONE);
            holder.according_service_title_value_tv.setVisibility(View.GONE);
        } else if (userInfo.getServices().toLowerCase().contains("tent")) {
            holder.according_service_title_tv.setVisibility(View.GONE);
            holder.according_service_title_value_tv.setVisibility(View.GONE);
        } else if (userInfo.getServices().toLowerCase().contains("dhol")) {
            holder.according_service_title_tv.setVisibility(View.VISIBLE);
            holder.according_service_title_value_tv.setVisibility(View.VISIBLE);
            holder.according_service_title_tv.setText("No.of Dhol");
            holder.according_service_title_value_tv.setText(userInfo.getNoOfDhol());
        } else if (userInfo.getServices().toLowerCase().contains("event planer")) {

        } else if (userInfo.getServices().toLowerCase().contains("water cane")) {
            holder.according_service_title_tv.setVisibility(View.VISIBLE);
            holder.according_service_title_value_tv.setVisibility(View.VISIBLE);
            holder.according_service_title_tv.setText("No.of Water cane");
            holder.according_service_title_value_tv.setText(userInfo.getNoOfWatercan());
        } else if (userInfo.getServices().toLowerCase().contains("singer/dancer")) {
            holder.according_service_title_tv.setVisibility(View.VISIBLE);
            holder.according_service_title_value_tv.setVisibility(View.VISIBLE);
            holder.according_service_title_tv.setText("Singer/Dancer");
            holder.according_service_title_value_tv.setText(userInfo.getSingerDancer());
        } else if (userInfo.getServices().toLowerCase().contains("waiter")) {

        } else if (userInfo.getServices().toLowerCase().contains("halwai")) {

        } else if (userInfo.getServices().toLowerCase().contains("caters")) {
            holder.according_service_title_tv.setVisibility(View.VISIBLE);
            holder.according_service_title_value_tv.setVisibility(View.VISIBLE);
            holder.according_service_title_tv.setText("No.of Guest");
            holder.according_service_title_value_tv.setText(userInfo.getNoOfMember());
        } else if (userInfo.getServices().toLowerCase().contains("baggi/horse")) {
            holder.according_service_title_tv.setVisibility(View.VISIBLE);
            holder.according_service_title_value_tv.setVisibility(View.VISIBLE);
            holder.according_service_title_tv.setText("No.of Baggi");
            holder.according_service_title_value_tv.setText(userInfo.getNoOfBaggiHorse());
        } else if (userInfo.getServices().toLowerCase().contains("video grapher")) {

        } else {
            holder.according_service_title_tv.setVisibility(View.GONE);
            holder.according_service_title_value_tv.setVisibility(View.GONE);
        }
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(activity);
                dialog.setContentView(R.layout.logout_popup);
                dialog.setCanceledOnTouchOutside(false);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Button submit_btn = (Button) dialog.findViewById(R.id.submit_btn);
                Button cancel_btn = (Button) dialog.findViewById(R.id.cancel_btn);
                TextView title_tv = (TextView) dialog.findViewById(R.id.title_tv);
                TextView content_tv = (TextView) dialog.findViewById(R.id.content_tv);
                title_tv.setText("Information");
                content_tv.setText("Are You Sure ? You Want to Delete !");
                cancel_btn.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        dialog.dismiss();
                        return false;
                    }
                });

                submit_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (NetworkUtil.isNetworkAvailable(activity)) {
                                final Dialog materialDialog = ErrorMessage.initProgressDialog(activity);
                                LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
                                Call<ResponseBody> call = apiService.delete_history(userInfo.getId());
                                call.enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        ErrorMessage.E("Response" + response.code());
                                        if (response.isSuccessful()) {
                                            dialog.dismiss();
                                            JSONObject object = null;
                                            try {
                                                materialDialog.dismiss();
                                                object = new JSONObject(response.body().string());
                                                ErrorMessage.E("comes in cond" + object.toString());
                                                if (object.getString("status").equals("200")) {
                                                    ErrorMessage.E("comes in if cond" + object.toString());
                                                    ErrorMessage.T(activity, object.getString("message"));
                                                    arrayList.remove(position);
                                                    notifyDataSetChanged();
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
                                            dialog.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        t.printStackTrace();
                                        ErrorMessage.E("Falure login" + t);

                                        materialDialog.dismiss();
                                        dialog.dismiss();
                                    }
                                });
                            } else {
                                ErrorMessage.T(activity, "No Internet");
                            }

                    }

                });
                dialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView event_name_tv, event_start_date_tv, event_end_date_tv, according_service_title_tv, according_service_title_value_tv, event_address_tv, bid_tv, customer_name_tv, service_title_tv;
        ImageView item_img;
        LinearLayout bid_layout;
        ImageButton phone_call_btn, whatsup_btn;

        public MyViewHolder(View itemView) {
            super(itemView);
            event_name_tv = itemView.findViewById(R.id.event_name_tv);
            event_start_date_tv = itemView.findViewById(R.id.event_start_date_tv);
            event_end_date_tv = itemView.findViewById(R.id.event_end_date_tv);
            according_service_title_tv = itemView.findViewById(R.id.according_service_title_tv);
            according_service_title_value_tv = itemView.findViewById(R.id.according_service_title_value_tv);
            event_address_tv = itemView.findViewById(R.id.event_address_tv);
            bid_tv = itemView.findViewById(R.id.bid_tv);
            bid_layout = itemView.findViewById(R.id.bid_layout);
            customer_name_tv = itemView.findViewById(R.id.customer_name_tv);
            whatsup_btn = itemView.findViewById(R.id.whatsup_btn);
            phone_call_btn = itemView.findViewById(R.id.phone_call_btn);
            service_title_tv = itemView.findViewById(R.id.service_title_tv);
            // item_img = itemView.findViewById(R.id.item_img);
        }
    }
}
