package com.pixelmarketo.nrh.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pixelmarketo.nrh.BaseActivity;
import com.pixelmarketo.nrh.R;
import com.pixelmarketo.nrh.database.UserProfileHelper;
import com.pixelmarketo.nrh.models.vender.service.Result;
import com.pixelmarketo.nrh.utility.AppConfig;
import com.pixelmarketo.nrh.utility.ErrorMessage;
import com.pixelmarketo.nrh.utility.LoadInterface;
import com.pixelmarketo.nrh.utility.NetworkUtil;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailActivity extends BaseActivity implements PaymentResultListener {

    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.event_name_tv)
    TextView eventNameTv;
    @BindView(R.id.event_start_date_tv)
    TextView eventStartDateTv;
    @BindView(R.id.event_end_date_tv)
    TextView eventEndDateTv;
    @BindView(R.id.bid_tv)
    TextView bidTv;
    @BindView(R.id.event_address_tv)
    TextView eventAddressTv;
    @BindView(R.id.according_service_title_tv)
    TextView accordingServiceTitleTv;
    @BindView(R.id.according_service_title_value_tv)
    TextView accordingServiceTitleValueTv;
    @BindView(R.id.pay_now_btn)
    Button payNowBtn;
    @BindView(R.id.service_img)
    CircleImageView serviceImg;
    Result result;
    double pay_amount = 0;
    @BindView(R.id.event_date_tv)
    TextView eventDateTv;
    @BindView(R.id.end_date_layout)
    LinearLayout endDateLayout;
    @BindView(R.id.number_of_days_title_tv)
    TextView numberOfDaysTitleTv;
    @BindView(R.id.number_of_days_value_tv)
    TextView numberOfDaysValueTv;
    @BindView(R.id.number_of_days_layout)
    LinearLayout numberOfDaysLayout;
    @BindView(R.id.led_wall_value_tv)
    TextView ledWallValueTv;
    @BindView(R.id.led_wall_layout)
    LinearLayout ledWallLayout;
    @BindView(R.id.video_cd_hourse_tv)
    TextView videoCdHourseTv;
    @BindView(R.id.video_cd_hourse_layout)
    LinearLayout videoCdHourseLayout;

    @Override
    protected int getContentResId() {
        return R.layout.activity_event_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("");
        ButterKnife.bind(this);
        Checkout.preload(getApplicationContext());
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            result = (Result) bundle.getSerializable("AllData");
            titleTxt.setText(result.getService() + " Detail");
            eventNameTv.setText(result.getEventName());
            eventStartDateTv.setText(result.getToDate());
            eventEndDateTv.setText(result.getFromDate());
            bidTv.setText(result.getBid());
            eventAddressTv.setText(result.getCity() + " (" + result.getTehsil() + ")");

            pay_amount = Double.parseDouble(result.getBid()) * 3 / 100;
            payNowBtn.setText("Pay ₹" + pay_amount);

            if (result.getService().toLowerCase().contains("bands")) {
                eventDateTv.setText("Event Date");
                eventStartDateTv.setText(result.getEventDate());
                endDateLayout.setVisibility(View.GONE);
                //holder.requirement_rcv.setVisibility(View.GONE);
            } else if (result.getService().toLowerCase().contains("dj")) {

            } else if (result.getService().toLowerCase().contains("dhol")) {
                eventDateTv.setText("Event Date");
                eventStartDateTv.setText(result.getEventDate());
                endDateLayout.setVisibility(View.GONE);
                // holder.requirement_rcv.setVisibility(View.GONE);
                numberOfDaysTitleTv.setText("No. of Dhol");
                numberOfDaysValueTv.setText(result.getNoOfDhol());
                numberOfDaysLayout.setVisibility(View.GONE);

            } else if (result.getService().toLowerCase().contains("event planner")) {
                eventDateTv.setText("Event Date");
                eventStartDateTv.setText(result.getEventDate());
                endDateLayout.setVisibility(View.GONE);
                // holder.requirement_rcv.setVisibility(View.GONE);
            } else if (result.getService().toLowerCase().contains("water cane")) {
                eventDateTv.setText("Event Date");
                eventStartDateTv.setText(result.getEventDate());
                endDateLayout.setVisibility(View.GONE);
                //holder.requirement_rcv.setVisibility(View.GONE);
                numberOfDaysTitleTv.setText("No. Water Cane");
                numberOfDaysValueTv.setText(result.getNoOfWatercan());
                numberOfDaysLayout.setVisibility(View.GONE);
            } else if (result.getService().toLowerCase().contains("singer/dancer")) {
                eventDateTv.setText("Event Date");
                eventStartDateTv.setText(result.getEventDate());
                endDateLayout.setVisibility(View.GONE);
                // holder.requirement_rcv.setVisibility(View.GONE);
                numberOfDaysTitleTv.setText("Singer/Dancer");
                numberOfDaysValueTv.setText(result.getSingerDancer());
                numberOfDaysLayout.setVisibility(View.GONE);
            } else if (result.getService().toLowerCase().contains("waiter")) {
                eventDateTv.setText("Event Date");
                eventStartDateTv.setText(result.getFromDate());
                endDateLayout.setVisibility(View.GONE);
                //holder.requirement_rcv.setVisibility(View.GONE);
                numberOfDaysTitleTv.setText("No.of Guest");
                numberOfDaysValueTv.setText(result.getNoOfMember());
                numberOfDaysLayout.setVisibility(View.VISIBLE);
                accordingServiceTitleValueTv.setText(result.getNoOfItem());
                accordingServiceTitleTv.setText("No. of Food Item");
            } else if (result.getService().toLowerCase().contains("halwai")) {
                eventDateTv.setText("Event Date");
                eventStartDateTv.setText(result.getFromDate());
                endDateLayout.setVisibility(View.GONE);
                // holder.requirement_rcv.setVisibility(View.VISIBLE);
                numberOfDaysTitleTv.setText("No.of Guest");
                numberOfDaysValueTv.setText(result.getNoOfMember());
                numberOfDaysLayout.setVisibility(View.VISIBLE);
                accordingServiceTitleValueTv.setText(result.getNoOfItem());
                accordingServiceTitleTv.setText("No. of Food Item");

            } else if (result.getService().toLowerCase().contains("caters")) {
                eventDateTv.setText("Event Date");
                eventStartDateTv.setText(result.getEventDate());
                endDateLayout.setVisibility(View.GONE);
                //  holder.requirement_rcv.setVisibility(View.GONE);
                numberOfDaysTitleTv.setText("No. of Guest");
                numberOfDaysValueTv.setText(result.getNoOfMember());
                numberOfDaysLayout.setVisibility(View.VISIBLE);
                accordingServiceTitleValueTv.setText(result.getNoOfItem());
                accordingServiceTitleTv.setText("No. of Food Item");

            } else if (result.getService().toLowerCase().contains("baggi/horse")) {
                eventDateTv.setText("Event Date");
                eventStartDateTv.setText(result.getEventDate());
                endDateLayout.setVisibility(View.GONE);
                // holder.requirement_rcv.setVisibility(View.GONE);
                numberOfDaysTitleTv.setText("No. of Baggi");
                numberOfDaysValueTv.setText(result.getNoOfBaggiHorse());
                numberOfDaysLayout.setVisibility(View.VISIBLE);
                accordingServiceTitleValueTv.setText(result.getNoOfDays());
                accordingServiceTitleTv.setText("No. of Days");

            } else if (result.getService().toLowerCase().contains("video grapher")) {
                eventDateTv.setText("Event Date");
                eventStartDateTv.setText(result.getEventDate());
                endDateLayout.setVisibility(View.GONE);
                // holder.event_end_date_tv.setText(result.getToDate());
                //  holder.requirement_rcv.setVisibility(View.GONE);
                numberOfDaysTitleTv.setText("Drone_crean");
                numberOfDaysValueTv.setText(result.getDrone_crean());
                numberOfDaysLayout.setVisibility(View.VISIBLE);
                accordingServiceTitleValueTv.setText(result.getPre_vid_shutting());

                accordingServiceTitleTv.setText("Pre_Video_Shutting");
                ledWallLayout.setVisibility(View.VISIBLE);
                ledWallValueTv.setText(result.getLed_wall());
               videoCdHourseLayout.setVisibility(View.VISIBLE);
                if (result.getVid_cd_hours().equals("")){
                    videoCdHourseTv.setText("NO");
                }
                else {
                    videoCdHourseTv.setText(result.getVid_cd_hours());}
            } else if (result.getService().toLowerCase().contains("traveller")) {
                eventDateTv.setText("Event Date");
                eventStartDateTv.setText(result.getEventDate());
                endDateLayout.setVisibility(View.VISIBLE);
                //holder.event_end_date_tv.setText(result.getToDate());
                //  holder.requirement_rcv.setVisibility(View.GONE);
                numberOfDaysTitleTv.setText("PickUp Time");
                numberOfDaysValueTv.setText(result.getPickUpTime());
                numberOfDaysLayout.setVisibility(View.VISIBLE);
                accordingServiceTitleValueTv.setText(result.getVehical_type());
                accordingServiceTitleTv.setText("Vehicle Type");
            } else if (result.getService().toLowerCase().contains("cantens")) {
                eventDateTv.setText("Event Date");
                eventStartDateTv.setText(result.getEventDate());
                endDateLayout.setVisibility(View.GONE);
                numberOfDaysTitleTv.setText("Types of Canten");
                numberOfDaysValueTv.setText(result.getCantens());
                numberOfDaysLayout.setVisibility(View.VISIBLE);
                accordingServiceTitleValueTv.setText(result.getNoOfMember());
                accordingServiceTitleTv.setText("No. of Member's");
            }else if (result.getService().toLowerCase().contains("decoration")) {
                eventDateTv.setText("Event Date");
                eventStartDateTv.setText(result.getEventDate());
                endDateLayout.setVisibility(View.GONE);
                numberOfDaysLayout.setVisibility(View.GONE);

                accordingServiceTitleValueTv.setText(result.getDecoretionType());
                accordingServiceTitleTv.setText("Decoration Type");
            }


        }

    }

    @OnClick(R.id.pay_now_btn)
    public void onClick() {
        Confirmation_PopUP(new DecimalFormat("#").format(pay_amount));
    }

    public void startPayment(double Charge) {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        ErrorMessage.E("Charge startPayment" + Charge);
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "NRS");
            options.put("description", "Event C");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", getResources().getDrawable(R.drawable.ic_launcher));
            options.put("currency", "INR");
            options.put("amount", Charge);

            JSONObject preFill = new JSONObject();
            preFill.put("email", "test@razorpay.com");
            preFill.put("contact", UserProfileHelper.getInstance().getUserProfileModel().get(0).getUserPhone());

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void Confirmation_PopUP(String service_charge) {
        Dialog dialog = new Dialog(EventDetailActivity.this);
        dialog.setContentView(R.layout.payment_confirmation_popup);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Button submit_btn = (Button) dialog.findViewById(R.id.submit_payment_btn);
        TextView content_tv = (TextView) dialog.findViewById(R.id.content_tv);
        content_tv.setText("Pay The 3% Initial Payment & Access Full Details. ");
        submit_btn.setText("Pay ₹ " + service_charge);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startPayment(Double.parseDouble(service_charge + "00"));
            }
        });
        dialog.show();
    }

    private void Done_Payment(String transcation) {
        if (NetworkUtil.isNetworkAvailable(EventDetailActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(EventDetailActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            ErrorMessage.E("Done_Payment request" + result.getId() + " >> " + String.valueOf(pay_amount));
            Call<ResponseBody> call = apiService.payment_service_admin(result.getId(), transcation, "Online", String.valueOf(pay_amount));
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            materialDialog.dismiss();
                            JSONObject object = new JSONObject(response.body().string());
                            ErrorMessage.E("getAllOrders  response" + object.toString());
                            if (object.getString("status").equals("200")) {
                                ErrorMessage.T(EventDetailActivity.this, object.getString("message"));
                                /*Confirmation_PopUP();*/
                            } else {
                                ErrorMessage.T(EventDetailActivity.this, object.getString("message"));
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
            ErrorMessage.T(EventDetailActivity.this, "Internet Not Found! ");
        }

    }

    @Override
    public void onPaymentSuccess(String s) {
        Done_Payment(s);
    }

    @Override
    public void onPaymentError(int i, String s) {

    }
}
