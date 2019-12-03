package com.pixelmarketo.nrh.adapter.vender;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pixelmarketo.nrh.LoginActivity;
import com.pixelmarketo.nrh.R;
import com.pixelmarketo.nrh.ServiceForms.Baggi_HourseActivity;
import com.pixelmarketo.nrh.ServiceForms.Caters;
import com.pixelmarketo.nrh.ServiceForms.TentActivity;
import com.pixelmarketo.nrh.ServiceForms.VideoGrapherActivity;
import com.pixelmarketo.nrh.TypesOfServiceActivity;

import com.pixelmarketo.nrh.UserDashboardActivity;
import com.pixelmarketo.nrh.VenderDashboardActivity;
import com.pixelmarketo.nrh.activity.EventDetailActivity;
import com.pixelmarketo.nrh.adapter.ServiceImage_Adapter;
import com.pixelmarketo.nrh.adapter.UploadImage_Adapter;
import com.pixelmarketo.nrh.adapter.Vender_services_adapter;
import com.pixelmarketo.nrh.database.UserProfileHelper;
import com.pixelmarketo.nrh.database.UserProfileModel;
import com.pixelmarketo.nrh.models.SelectImage;
import com.pixelmarketo.nrh.models.vender.service.Result;
import com.pixelmarketo.nrh.utility.AppConfig;
import com.pixelmarketo.nrh.utility.ErrorMessage;
import com.pixelmarketo.nrh.utility.LoadInterface;
import com.pixelmarketo.nrh.utility.NetworkUtil;
import com.pixelmarketo.nrh.utility.SavedData;
import com.pixelmarketo.nrh.utility.UserAccount;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewService_adapter extends RecyclerView.Adapter<NewService_adapter.MyViewHolder> {
    View view;
    Context activity;
    List<Result> arrayList;
    String Check = "";

    public NewService_adapter(Context policyActivity, List<Result> arrayList, String Check) {
        this.activity = policyActivity;
        this.arrayList = arrayList;
        this.Check = Check;
    }

    @NonNull
    @Override
    public NewService_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_service_request_item, parent, false);
        return new NewService_adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewService_adapter.MyViewHolder holder, final int position) {
        final Result userInfo = arrayList.get(position);
        holder.event_name_tv.setText(userInfo.getEventName());
        holder.event_start_date_tv.setText(userInfo.getToDate());
        holder.event_end_date_tv.setText(userInfo.getFromDate());
        holder.service_type_tv.setText(userInfo.getType());
        holder.event_address_tv.setText(userInfo.getCity() + "," + userInfo.getTehsil());
        if (userInfo.getImage().size() > 0) {
            ServiceImage_Adapter assignedTaskAdapter = new ServiceImage_Adapter(activity, userInfo.getImage(), userInfo);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity.getApplicationContext(), RecyclerView.HORIZONTAL, false);
            holder.requirement_rcv.setLayoutManager(mLayoutManager);
            holder.requirement_rcv.setItemAnimator(new DefaultItemAnimator());
            holder.requirement_rcv.setAdapter(assignedTaskAdapter);
        }
        if (Check.equals("Pending")) {
            if (userInfo.getType().equals("")) {
                holder.category_layout.setVisibility(View.GONE);
            } else {
                holder.category_layout.setVisibility(View.VISIBLE);
            }
        }
        if (!userInfo.getBid().equals("")) {
            holder.bid_layout.setVisibility(View.VISIBLE);
            holder.bid_tv.setText(userInfo.getBid());
        } else {
            holder.bid_layout.setVisibility(View.GONE);
        }
        if (userInfo.getService().toLowerCase().contains("bands")) {
            holder.event_date_tv.setText("Event Date");
            holder.event_start_date_tv.setText(userInfo.getEventDate());
            holder.end_date_layout.setVisibility(View.GONE);
            holder.requirement_rcv.setVisibility(View.GONE);
        } else if (userInfo.getService().toLowerCase().contains("dj")) {
            holder.event_date_tv.setText("Event Date");
            holder.event_start_date_tv.setText(userInfo.getEventDate());
            holder.end_date_layout.setVisibility(View.GONE);
            holder.requirement_rcv.setVisibility(View.GONE);
            holder.number_of_days_layout.setVisibility(View.GONE);
            holder.according_service_layout.setVisibility(View.GONE);

        } else if (userInfo.getService().toLowerCase().contains("dhol")) {
            holder.event_date_tv.setText("Event Date");
            holder.event_start_date_tv.setText(userInfo.getEventDate());
            holder.end_date_layout.setVisibility(View.GONE);
            holder.requirement_rcv.setVisibility(View.GONE);
            holder.according_service_title_tv.setText("No. of Dhol");
            holder.according_service_title_value_tv.setText(userInfo.getNoOfDhol());
            holder.number_of_days_layout.setVisibility(View.GONE);

        } else if (userInfo.getService().toLowerCase().contains("event planner")) {
            holder.event_date_tv.setText("Event Date");
            holder.event_start_date_tv.setText(userInfo.getEventDate());
            holder.end_date_layout.setVisibility(View.GONE);
            holder.requirement_rcv.setVisibility(View.GONE);
            holder.number_of_days_layout.setVisibility(View.GONE);
            holder.according_service_layout.setVisibility(View.GONE);
        } else if (userInfo.getService().toLowerCase().contains("water cane")) {
            holder.event_date_tv.setText("Event Date");
            holder.event_start_date_tv.setText(userInfo.getEventDate());
            holder.end_date_layout.setVisibility(View.GONE);
            holder.requirement_rcv.setVisibility(View.GONE);
            holder.according_service_title_tv.setText("No. Water Cane");
            holder.according_service_title_value_tv.setText(userInfo.getNoOfWatercan());
            holder.number_of_days_layout.setVisibility(View.GONE);
        } else if (userInfo.getService().toLowerCase().contains("singer/dancer")) {
            holder.event_date_tv.setText("Event Date");
            holder.event_start_date_tv.setText(userInfo.getEventDate());
            holder.end_date_layout.setVisibility(View.GONE);
            holder.requirement_rcv.setVisibility(View.GONE);
            holder.according_service_title_tv.setText("Singer/Dancer");
            holder.according_service_title_value_tv.setText(userInfo.getSingerDancer());
            holder.number_of_days_layout.setVisibility(View.GONE);
        } else if (userInfo.getService().toLowerCase().contains("waiter")) {
            holder.event_date_tv.setText("Event Date");
            holder.event_start_date_tv.setText(userInfo.getEventDate());
            holder.end_date_layout.setVisibility(View.GONE);
            holder.requirement_rcv.setVisibility(View.GONE);
            holder.according_service_title_tv.setText("No.of Guest");
            holder.according_service_title_value_tv.setText(userInfo.getNoOfMember());
            holder.number_of_days_layout.setVisibility(View.VISIBLE);
            holder.number_of_days_value_tv.setText(userInfo.getNoOfItem());
            holder.number_of_days_title_tv.setText("No. of Food Item");
        } else if (userInfo.getService().toLowerCase().contains("halwai")) {
            holder.event_date_tv.setText("Event Date");
            holder.event_start_date_tv.setText(userInfo.getFromDate());
            holder.end_date_layout.setVisibility(View.GONE);
            holder.requirement_rcv.setVisibility(View.VISIBLE);
            holder.according_service_title_tv.setText("No.of Guest");
            holder.according_service_title_value_tv.setText(userInfo.getNoOfMember());
            holder.number_of_days_layout.setVisibility(View.VISIBLE);
            holder.number_of_days_value_tv.setText(userInfo.getNoOfItem());
            holder.number_of_days_title_tv.setText("No. of Food Item");

        } else if (userInfo.getService().toLowerCase().contains("caters")) {
            holder.event_date_tv.setText("Event Date");

            holder.end_date_layout.setVisibility(View.GONE);
            if (userInfo.getImage().size() > 0) {
                holder.requirement_rcv.setVisibility(View.VISIBLE);
                holder.event_start_date_tv.setText(userInfo.getFromDate());
            } else {
                holder.requirement_rcv.setVisibility(View.GONE);
                holder.event_start_date_tv.setText(userInfo.getEventDate());
            }
            holder.according_service_title_tv.setText("No. of Guest");
            holder.according_service_title_value_tv.setText(userInfo.getNoOfMember());
            holder.number_of_days_layout.setVisibility(View.VISIBLE);
            holder.number_of_days_value_tv.setText(userInfo.getNoOfItem());
            holder.number_of_days_title_tv.setText("No. of Food Item");

        } else if (userInfo.getService().toLowerCase().contains("baggi/horse")) {
            holder.event_date_tv.setText("Event Date");
            holder.event_start_date_tv.setText(userInfo.getEventDate());
            holder.end_date_layout.setVisibility(View.GONE);
            holder.requirement_rcv.setVisibility(View.GONE);
            if (!userInfo.getNoOfBaggiHorse().equals("")){
            holder.according_service_title_tv.setText("No. of Baggi");
            holder.according_service_title_value_tv.setText(userInfo.getNoOfBaggiHorse());}
            else {
                holder.according_service_title_tv.setText("No. of Horse");
                holder.according_service_title_value_tv.setText(userInfo.getNo_horse());
            }
            holder.number_of_days_layout.setVisibility(View.VISIBLE);
            holder.number_of_days_value_tv.setText(userInfo.getNoOfDays());
            holder.number_of_days_title_tv.setText("No. of Days");

        } else if (userInfo.getService().toLowerCase().contains("video grapher")) {
            holder.event_date_tv.setText("Event Date");
            holder.event_start_date_tv.setText(userInfo.getEventDate());
            holder.end_date_layout.setVisibility(View.GONE);
            holder.event_end_date_tv.setText(userInfo.getToDate());
            holder.requirement_rcv.setVisibility(View.GONE);
            holder.according_service_title_tv.setText("Drone_crean");
            holder.according_service_title_value_tv.setText(userInfo.getDrone_crean());
            holder.number_of_days_layout.setVisibility(View.VISIBLE);
            holder.number_of_days_value_tv.setText(userInfo.getPre_vid_shutting());

            holder.number_of_days_title_tv.setText("Pre_Video_Shutting");
            holder.category_layout.setVisibility(View.GONE);
            holder.led_wall_layout.setVisibility(View.VISIBLE);
            holder.led_wall_value_tv.setText(userInfo.getLed_wall());
            holder.video_cd_hourse_layout.setVisibility(View.VISIBLE);
            if (userInfo.getVid_cd_hours().equals("")) {
                holder.video_cd_hourse_tv.setText("NO");
            } else {
                holder.video_cd_hourse_tv.setText(userInfo.getVid_cd_hours());
            }
        } else if (userInfo.getService().toLowerCase().contains("traveller")) {
            holder.event_date_tv.setText("Depature Date");
            holder.event_end_date_title_tv.setText("Arriving Date");
            holder.event_address_title_tv.setText("Pick Up Time");

            holder.event_address_tv.setText(userInfo.getPickUpTime());
            holder.event_start_date_tv.setText(userInfo.getEventDate());
            holder.end_date_layout.setVisibility(View.VISIBLE);
            holder.event_end_date_tv.setText(userInfo.getToDate());
            holder.requirement_rcv.setVisibility(View.GONE);
            holder.according_service_title_tv.setText("From Address");
            holder.according_service_title_value_tv.setText(userInfo.getFromPlace());
            holder.number_of_days_layout.setVisibility(View.VISIBLE);
            holder.number_of_days_value_tv.setText(userInfo.getToPlace());
            holder.number_of_days_title_tv.setText("To Address");
            holder.led_wall_layout.setVisibility(View.VISIBLE);
            holder.led_wall_value_tv.setText(userInfo.getVehical_type());
            holder.led_wall_title_tv.setText("Vehicle Type");
        } else if (userInfo.getService().toLowerCase().contains("cantens")) {
            holder.event_date_tv.setText("Event Date");
            holder.event_start_date_tv.setText(userInfo.getEventDate());
            holder.end_date_layout.setVisibility(View.GONE);
            holder.event_end_date_tv.setText(userInfo.getToDate());
            holder.requirement_rcv.setVisibility(View.GONE);
            holder.according_service_title_tv.setText("Types of Canten");
            holder.according_service_title_value_tv.setText(userInfo.getCantens());
            holder.number_of_days_layout.setVisibility(View.VISIBLE);
            holder.number_of_days_value_tv.setText(userInfo.getNoOfMember());
            holder.number_of_days_title_tv.setText("No. of Member's");
        } else if (userInfo.getService().toLowerCase().contains("decoration")) {
            holder.event_date_tv.setText("Event Date");
            holder.event_start_date_tv.setText(userInfo.getEventDate());
            holder.end_date_layout.setVisibility(View.GONE);
            holder.category_layout.setVisibility(View.GONE);
            holder.event_end_date_tv.setText(userInfo.getToDate());
            holder.requirement_rcv.setVisibility(View.GONE);
            holder.according_service_title_tv.setText("Decoration Type");
            holder.according_service_title_value_tv.setText(userInfo.getDecoretionType());
            holder.number_of_days_layout.setVisibility(View.GONE);
        }
        if (Check.equals("Approved")) {
            holder.view_detail_tv.setVisibility(View.VISIBLE);
        } else {
            holder.view_detail_tv.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Check.equals("Pending")) {
                    if (userInfo.getBid().equals("")) {
                        Dialog dialog = new Dialog(activity);
                        dialog.setContentView(R.layout.reply_new_service_budget_popup);
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                        EditText amount_etv = dialog.findViewById(R.id.amount_etv);
                        EditText comment_etv = dialog.findViewById(R.id.comment_etv);
                        ImageView cancel_img = dialog.findViewById(R.id.cancel_img);
                        Button submit_btn = dialog.findViewById(R.id.submit_btn);
                        Button skip_btn = dialog.findViewById(R.id.skip_btn);
                        cancel_img.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                dialog.dismiss();
                                return false;
                            }
                        });
                        skip_btn.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                dialog.dismiss();
                                return false;
                            }
                        });
                        submit_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (UserAccount.isEmpty(amount_etv)) {
                                    if (NetworkUtil.isNetworkAvailable(activity)) {
                                        final Dialog materialDialog = ErrorMessage.initProgressDialog(activity);
                                        LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
                                        Call<ResponseBody> call = apiService.update_bid_by_vendorid(userInfo.getId(), amount_etv.getText().toString(), comment_etv.getText().toString());
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
                                                            ((VenderDashboardActivity) activity).selectPage();
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
                                } else {
                                    UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
                                    UserAccount.EditTextPointer.requestFocus();
                                }
                            }

                        });
                        dialog.show();
                    }
                } else if (Check.equals("Approved")) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("AllData", userInfo);
                    ErrorMessage.I(activity, EventDetailActivity.class, bundle);

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView event_name_tv, event_start_date_tv, event_end_date_tv, according_service_title_tv, according_service_title_value_tv, event_address_tv, bid_tv, view_detail_tv, service_type_tv, event_date_tv, number_of_days_title_tv, number_of_days_value_tv, video_cd_hourse_tv,
                led_wall_value_tv,event_end_date_title_tv,event_address_title_tv,led_wall_title_tv;
        ImageView item_img;
        LinearLayout bid_layout, category_layout, end_date_layout, number_of_days_layout, according_service_layout, led_wall_layout, video_cd_hourse_layout;
        RecyclerView requirement_rcv;

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
            view_detail_tv = itemView.findViewById(R.id.view_detail_tv);
            requirement_rcv = itemView.findViewById(R.id.requirement_rcv);
            service_type_tv = itemView.findViewById(R.id.service_type_tv);
            category_layout = itemView.findViewById(R.id.category_layout);
            end_date_layout = itemView.findViewById(R.id.end_date_layout);
            event_date_tv = itemView.findViewById(R.id.event_date_tv);
            number_of_days_layout = itemView.findViewById(R.id.number_of_days_layout);
            number_of_days_title_tv = itemView.findViewById(R.id.number_of_days_title_tv);
            number_of_days_value_tv = itemView.findViewById(R.id.number_of_days_value_tv);
            according_service_layout = itemView.findViewById(R.id.according_service_layout);
            video_cd_hourse_layout = itemView.findViewById(R.id.video_cd_hourse_layout);
            led_wall_layout = itemView.findViewById(R.id.led_wall_layout);
            led_wall_value_tv = itemView.findViewById(R.id.led_wall_value_tv);
            video_cd_hourse_tv = itemView.findViewById(R.id.video_cd_hourse_tv);
            event_end_date_title_tv = itemView.findViewById(R.id.event_end_date_title_tv);
            event_address_title_tv = itemView.findViewById(R.id.event_address_title_tv);
            led_wall_title_tv = itemView.findViewById(R.id.led_wall_title_tv);
            // item_img = itemView.findViewById(R.id.item_img);
        }
    }
}
