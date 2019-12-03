package com.pixelmarketo.nrh.activity.user;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.pixelmarketo.nrh.BaseActivity;
import com.pixelmarketo.nrh.R;
import com.pixelmarketo.nrh.database.UserProfileHelper;
import com.pixelmarketo.nrh.models.Service_Models.Result;
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

public class CantensActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener{

    @BindView(R.id.event_name_etv)
    EditText eventNameEtv;
    @BindView(R.id.start_date_etv)
    EditText startDateEtv;
    @BindView(R.id.start_date_inputlayout)
    TextInputLayout startDateInputlayout;
    @BindView(R.id.select_type_layout)
    LinearLayout selectTypeLayout;
    @BindView(R.id.no_of_members_etv)
    EditText noOfMembersEtv;
    @BindView(R.id.no_of_dhol_inputlayout)
    TextInputLayout noOfDholInputlayout;
    @BindView(R.id.city_village_etv)
    EditText cityVillageEtv;
    @BindView(R.id.city_village_inputlayout)
    TextInputLayout cityVillageInputlayout;
    @BindView(R.id.tehsil_etv)
    EditText tehsilEtv;
    @BindView(R.id.tehsil_inputlayout)
    TextInputLayout tehsilInputlayout;
    @BindView(R.id.submit_btn)
    Button submitBtn;
    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.panipuri_checkbox)
    CheckBox panipuriCheckbox;
    @BindView(R.id.cold_drink_checkbox)
    CheckBox coldDrinkCheckbox;
    @BindView(R.id.fruits_checkbox)
    CheckBox fruitsCheckbox;
    @BindView(R.id.kulfi_checkbox)
    CheckBox kulfiCheckbox;
    @BindView(R.id.end_date_etv)
    EditText endDateEtv;
    @BindView(R.id.end_date_inputlayout)
    TextInputLayout endDateInputlayout;
    @BindView(R.id.vehicle_type_etv)
    EditText vehicleTypeEtv;
    @BindView(R.id.vehicle_inputlayout)
    TextInputLayout vehicleInputlayout;

    private Result result;
    String Types_of_Contens = "";
String Check="";
    @Override
    protected int getContentResId() {
        return R.layout.activity_cantens;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("");
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            result = (Result) bundle.getSerializable("AllData");
            titleTxt.setText(result.getServiceName());
            if (result.getServiceName().toLowerCase().contains("traveller")) {
                selectTypeLayout.setVisibility(View.GONE);
                endDateInputlayout.setVisibility(View.VISIBLE);
                vehicleInputlayout.setVisibility(View.VISIBLE);
                startDateEtv.setHint("Depature Date");
                endDateEtv.setHint("Arriving Date");

                noOfMembersEtv.setHint("From Address");
                noOfMembersEtv.setInputType(InputType.TYPE_CLASS_TEXT);
                cityVillageEtv.setHint("To Address");
                tehsilEtv.setHint("PickUp Time");
                tehsilEtv.setTextAppearance(this, R.style.InvisibleFocusHolder);
                vehicleTypeEtv.setHint("Vehicle Type");
                tehsilEtv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SetTime();
                    }
                });
            } else if (result.getServiceName().toLowerCase().contains("cantens")) {
                startDateEtv.setHint("Event Date");
                noOfMembersEtv.setHint("No. of Member's");
                cityVillageEtv.setHint("City/Village");
                tehsilEtv.setHint("Tehsil");
            }
        }
        panipuriCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (Types_of_Contens.equals("")) {
                        Types_of_Contens = "Panipuri";
                    } else {
                        Types_of_Contens = Types_of_Contens + "," + "Panipuri";
                    }
                }
            }
        });
        coldDrinkCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (Types_of_Contens.equals("")) {
                        Types_of_Contens = "ColdDrink";
                    } else {
                        Types_of_Contens = Types_of_Contens + "," + "ColdDrink";
                    }
                }
            }
        });
        fruitsCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (Types_of_Contens.equals("")) {
                        Types_of_Contens = "Fruits";
                    } else {
                        Types_of_Contens = Types_of_Contens + "," + "Fruits";
                    }
                }
            }
        });
        kulfiCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (Types_of_Contens.equals("")) {
                        Types_of_Contens = "Kulfi";
                    } else {
                        Types_of_Contens = Types_of_Contens + "," + "Kulfi";
                    }
                }
            }
        });
        startDateEtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check="Depature";
                datepicker();
            }
        });  endDateEtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check="End";
                datepicker();
            }
        });
    }

    @OnClick(R.id.submit_btn)
    public void onClick() {
        if (UserAccount.isEmpty(eventNameEtv, startDateEtv, noOfMembersEtv, cityVillageEtv, tehsilEtv)) {
            submitReportApi_band();
        } else {
            UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
            UserAccount.EditTextPointer.requestFocus();
        }
    }

    private void submitReportApi_band() {
        if (NetworkUtil.isNetworkAvailable(CantensActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(CantensActivity.this);
            Call<ResponseBody> call = AppConfig.getLoadInterface().service_request_WithoutImage(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUid(), result.getServiceId(), "", eventNameEtv.getText().toString(), startDateEtv.getText().toString(), cityVillageEtv.getText().toString(), tehsilEtv.getText().toString(), "", "", "", noOfMembersEtv.getText().toString(), "", Types_of_Contens,noOfMembersEtv.getText().toString(),cityVillageEtv.getText().toString(),tehsilEtv.getText().toString(),vehicleTypeEtv.getText().toString(),"","",endDateEtv.getText().toString(),"");
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
                                ErrorMessage.T(CantensActivity.this, jsonObject.getString("message"));
                                //ErrorMessage.I_clear(getApplicationContext(), TechDashboardActivity.class, null);
                                finish();
                                //getProfileCall();
                            } else {
                                ErrorMessage.T(CantensActivity.this, jsonObject.getString("message"));
                                materialDialog.dismiss();


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ErrorMessage.T(CantensActivity.this, "Server Error");
                            ErrorMessage.E("JsonExc" + e.toString());
                            materialDialog.dismiss();

                        } catch (IOException e) {
                            e.printStackTrace();
                            ErrorMessage.T(CantensActivity.this, "Server Error");
                            ErrorMessage.E("IOExc" + e.toString());
                            materialDialog.dismiss();

                        }
                    } else {
                        ErrorMessage.T(CantensActivity.this, "Response not successful");
                        materialDialog.dismiss();

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    ErrorMessage.T(CantensActivity.this, "Response Fail");
                    System.out.println("============update profile fail  :" + t.toString());
                    materialDialog.dismiss();
                }
            });

        } else {
            ErrorMessage.T(CantensActivity.this, "No internet Connection");
        }
    }
    @Override
    public void onDateSet(DatePickerDialog view, int eventYear, int monthOfYear, int dayOfMonth) {
        if (Check.equals("Depature")) {
            if (monthOfYear + 1 < 10 && dayOfMonth > 10) startDateEtv.setText((eventYear + "-0" + (monthOfYear + 1) + "-" + dayOfMonth));
            else if (dayOfMonth < 10 && monthOfYear + 1 >= 10) startDateEtv.setText((eventYear + "-" + (monthOfYear + 1) + "-0" + dayOfMonth));
            else if (monthOfYear + 1 < 10 && dayOfMonth < 10) startDateEtv.setText((eventYear + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth));
            else startDateEtv.setText((eventYear + "-" + (monthOfYear + 1) + "-" + dayOfMonth));
        }else if (Check.equals("End")) {
            if (monthOfYear + 1 < 10 && dayOfMonth > 10) endDateEtv.setText((eventYear + "-0" + (monthOfYear + 1) + "-" + dayOfMonth));
            else if (dayOfMonth < 10 && monthOfYear + 1 >= 10) endDateEtv.setText((eventYear + "-" + (monthOfYear + 1) + "-0" + dayOfMonth));
            else if (monthOfYear + 1 < 10 && dayOfMonth < 10) endDateEtv.setText((eventYear + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth));
            else endDateEtv.setText((eventYear + "-" + (monthOfYear + 1) + "-" + dayOfMonth));
        }

    }

    private void datepicker() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setAccentColor("#0b346a");
        datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);
        datePickerDialog.setMinDate(calendar);
        Log.e("setMinDate", " == " + calendar.toString());
        datePickerDialog.show(getFragmentManager(), "Date Picker");
    }
    public void SetTime() {
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(CantensActivity.this, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String am_pm = "";

                Calendar datetime = Calendar.getInstance();
                datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                datetime.set(Calendar.MINUTE, minute);

                if (datetime.get(Calendar.AM_PM) == Calendar.AM) am_pm = "AM";
                else if (datetime.get(Calendar.AM_PM) == Calendar.PM) am_pm = "PM";

                String strHrsToShow = (datetime.get(Calendar.HOUR) == 0) ? "12" : Integer.toString(datetime.get(Calendar.HOUR));


                tehsilEtv.setText(strHrsToShow + ":" + datetime.get(Calendar.MINUTE) + " " + am_pm);



            }
        }, mHour, mMinute, false);

        timePickerDialog.show();
    }
}
