package com.pixelmarketo.nrh.ServiceForms;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.pixelmarketo.nrh.BaseActivity;
import com.pixelmarketo.nrh.R;
import com.pixelmarketo.nrh.database.UserProfileHelper;
import com.pixelmarketo.nrh.models.Service_Models.Result;
import com.pixelmarketo.nrh.models.Service_Models.Subservice;
import com.pixelmarketo.nrh.utility.AppConfig;
import com.pixelmarketo.nrh.utility.ErrorMessage;
import com.pixelmarketo.nrh.utility.NetworkUtil;
import com.pixelmarketo.nrh.utility.UserAccount;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoGrapherActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.tool_barLayout)
    RelativeLayout toolBarLayout;
    @BindView(R.id.event_name_etv)
    EditText eventNameEtv;
    @BindView(R.id.start_date_etv)
    EditText startDateEtv;
    @BindView(R.id.start_date_inputlayout)
    TextInputLayout startDateInputlayout;
    @BindView(R.id.city_village_etv)
    EditText cityVillageEtv;
    @BindView(R.id.city_village_inputlayout)
    TextInputLayout cityVillageInputlayout;
    @BindView(R.id.tehsil_etv)
    EditText tehsilEtv;
    @BindView(R.id.tehsil_inputlayout)
    TextInputLayout tehsilInputlayout;
    @BindView(R.id.drone_checkbox)
    CheckBox droneCheckbox;
    @BindView(R.id.crean_checkbox)
    CheckBox creanCheckbox;
    @BindView(R.id.non_of_these_checkbox)
    CheckBox nonOfTheseCheckbox;
    @BindView(R.id.radioButton)
    RadioButton radioButton;
    @BindView(R.id.radioButton2)
    RadioButton radioButton2;
    @BindView(R.id.prevideo_shutting_radioGroup)
    RadioGroup prevideoShuttingRadioGroup;
    @BindView(R.id.select_type_layout)
    LinearLayout selectTypeLayout;
    @BindView(R.id.led_wall_radiogroup)
    RadioGroup ledWallRadiogroup;
    @BindView(R.id.video_cd_hourse)
    EditText videoCdHourse;
    @BindView(R.id.submit_btn)
    Button submitBtn;
    private Calendar calendar;
    private Result result;
    String Drone_Crean = "", prevideoShutting = "", ledWall = "";

    @Override
    protected int getContentResId() {
        return R.layout.activity_video_grapher;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_video_grapher);
        setToolbarWithBackButton("Video Grapher");
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            result = (Result) bundle.getSerializable("AllData");
            titleTxt.setText(result.getServiceName());

        }
        startDateEtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepicker();
            }
        });
        droneCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (Drone_Crean.equals("")) {
                        Drone_Crean = "Drone";
                    } else {
                        Drone_Crean = Drone_Crean + "Drone";
                    }
                } else {
                    if (Drone_Crean.equals("Drone")) {
                        Drone_Crean = "";
                    }
                }
            }
        });
        creanCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (Drone_Crean.equals("")) {
                        Drone_Crean = "Crean";
                    } else {
                        Drone_Crean = Drone_Crean + "Crean";
                    }
                } else {
                    if (Drone_Crean.equals("Crean")) {
                        Drone_Crean = "";
                    }
                }
            }
        });
        nonOfTheseCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (Drone_Crean.equals("")) {
                        Drone_Crean = "Non of these";
                    } else {
                        Drone_Crean = Drone_Crean + "Non of these";
                    }
                } else {
                    if (Drone_Crean.equals("Non of these")) {
                        Drone_Crean = "";
                    }
                }
            }
        });
        prevideoShuttingRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int selectedId) {
                RadioButton genderradioButton = (RadioButton) findViewById(selectedId);
                prevideoShutting = genderradioButton.getText().toString();

            }
        });
        ledWallRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int selectedId) {
                RadioButton genderradioButton = (RadioButton) findViewById(selectedId);
                ledWall = genderradioButton.getText().toString();

            }
        });
    }

    @OnClick(R.id.submit_btn)
    public void onClick() {
        if (UserAccount.isEmpty(eventNameEtv, startDateEtv, cityVillageEtv, tehsilEtv, videoCdHourse)) {
            if (!prevideoShutting.equals("")) {
                if (!ledWall.equals("")) {
                    submitReportApi();
                } else {
                    ErrorMessage.T(VideoGrapherActivity.this, "Please Choose Led Wall Option");
                }
            } else {
                ErrorMessage.T(VideoGrapherActivity.this, "Please Choose Pre-Video Shutting");
            }
        } else {
            UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
            UserAccount.EditTextPointer.requestFocus();
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int eventYear, int monthOfYear, int dayOfMonth) {
        if (monthOfYear + 1 < 10 && dayOfMonth > 10)
            startDateEtv.setText((eventYear + "-0" + (monthOfYear + 1) + "-" + dayOfMonth));
        else if (dayOfMonth < 10 && monthOfYear + 1 >= 10)
            startDateEtv.setText((eventYear + "-" + (monthOfYear + 1) + "-0" + dayOfMonth));
        else if (monthOfYear + 1 < 10 && dayOfMonth < 10)
            startDateEtv.setText((eventYear + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth));
        else startDateEtv.setText((eventYear + "-" + (monthOfYear + 1) + "-" + dayOfMonth));


    }

    private void datepicker() {
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setAccentColor("#0b346a");
        datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);
        datePickerDialog.setMinDate(calendar);
        Log.e("setMinDate", " == " + calendar.toString());
        datePickerDialog.show(getFragmentManager(), "Date Picker");
    }

    private void submitReportApi() {
        if (NetworkUtil.isNetworkAvailable(VideoGrapherActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(VideoGrapherActivity.this);
            Call<ResponseBody> call = AppConfig.getLoadInterface().service_request_WithoutImage(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUid(), result.getServiceId(), "", eventNameEtv.getText().toString(), startDateEtv.getText().toString(), cityVillageEtv.getText().toString(), tehsilEtv.getText().toString(), "", "", "", "", "", "", "", "", "", "", "", "", Drone_Crean, prevideoShutting, ledWall, videoCdHourse.getText().toString());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        JSONObject jsonObject = null;
                        Gson gson = new Gson();
                        try {
                            jsonObject = new JSONObject(response.body().string());
                            ErrorMessage.E("response of profile" + jsonObject.toString());
                            if (jsonObject.getString("status").equals("200")) {
                                String responseData = jsonObject.toString();
                                materialDialog.dismiss();
                                System.out.println("===response data :" + responseData);
                                ErrorMessage.T(VideoGrapherActivity.this, jsonObject.getString("message"));
                                //ErrorMessage.I_clear(getApplicationContext(), TechDashboardActivity.class, null);
                                //getProfileCall();
                                VideoGrapherActivity.this.finish();
                            } else {
                                ErrorMessage.T(VideoGrapherActivity.this, jsonObject.getString("message"));
                                materialDialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ErrorMessage.T(VideoGrapherActivity.this, "Server Error");
                            ErrorMessage.E("JsonExc" + e.toString());
                            materialDialog.dismiss();

                        } catch (IOException e) {
                            e.printStackTrace();
                            ErrorMessage.T(VideoGrapherActivity.this, "Server Error");
                            ErrorMessage.E("IOExc" + e.toString());
                            materialDialog.dismiss();

                        }
                    } else {
                        ErrorMessage.T(VideoGrapherActivity.this, "Response not successful");
                        materialDialog.dismiss();

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    ErrorMessage.T(VideoGrapherActivity.this, "Response Fail");
                    System.out.println("============update profile fail  :" + t.toString());
                    materialDialog.dismiss();
                }
            });

        } else {
            ErrorMessage.T(VideoGrapherActivity.this, "No internet Connection");
        }
    }
}
