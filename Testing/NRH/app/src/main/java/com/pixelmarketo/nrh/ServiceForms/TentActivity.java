package com.pixelmarketo.nrh.ServiceForms;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.pixelmarketo.nrh.BaseActivity;
import com.pixelmarketo.nrh.R;
import com.pixelmarketo.nrh.adapter.UploadImage_Adapter;
import com.pixelmarketo.nrh.database.UserProfileHelper;
import com.pixelmarketo.nrh.models.SelectImage;
import com.pixelmarketo.nrh.models.Service_Models.Result;
import com.pixelmarketo.nrh.models.Service_Models.Subservice;
import com.pixelmarketo.nrh.utility.AppConfig;
import com.pixelmarketo.nrh.utility.ErrorMessage;
import com.pixelmarketo.nrh.utility.NetworkUtil;
import com.pixelmarketo.nrh.utility.UserAccount;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class TentActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.event_name_etv)
    EditText eventNameEtv;
    @BindView(R.id.start_date_etv)
    EditText startDateEtv;
    @BindView(R.id.start_date_inputlayout)
    TextInputLayout startDateInputlayout;
    @BindView(R.id.end_date_etv)
    EditText endDateEtv;
    @BindView(R.id.end_date_inputlayout)
    TextInputLayout endDateInputlayout;
    @BindView(R.id.city_village_etv)
    EditText cityVillageEtv;
    @BindView(R.id.city_village_inputlayout)
    TextInputLayout cityVillageInputlayout;
    @BindView(R.id.tehsil_etv)
    EditText tehsilEtv;
    @BindView(R.id.tehsil_inputlayout)
    TextInputLayout tehsilInputlayout;
    @BindView(R.id.select_image_title)
    TextView selectImageTitle;
    @BindView(R.id.image_Rcv)
    RecyclerView imageRcv;
    @BindView(R.id.select_image_layout)
    LinearLayout selectImageLayout;
    @BindView(R.id.submit_btn)
    Button submitBtn;
    @BindView(R.id.image_layout)
    LinearLayout imageLayout;
    @BindView(R.id.no_of_dhol_etv)
    EditText noOfDholEtv;
    @BindView(R.id.no_of_dhol_inputlayout)
    TextInputLayout noOfDholInputlayout;
    @BindView(R.id.radioButton)
    CheckBox radioButton;
    @BindView(R.id.radioButton2)
    CheckBox radioButton2;
    @BindView(R.id.radioGroup)
    LinearLayout radioGroup;
    @BindView(R.id.select_type_layout)
    LinearLayout selectTypeLayout;
    @BindView(R.id.no_of_food_etv)
    EditText noOfFoodEtv;
    @BindView(R.id.no_of_food_inputlayout)
    TextInputLayout noOfFoodInputlayout;
    @BindView(R.id.flower_checkbox)
    CheckBox flowerCheckbox;
    @BindView(R.id.statue_checkbox)
    CheckBox statueCheckbox;
    @BindView(R.id.car_checkbox)
    CheckBox carCheckbox;
    @BindView(R.id.decoration_type_layout)
    LinearLayout decorationTypeLayout;
    private Calendar calendar;
    String DatePickerCall = "";
    private String selectedFilePath;
    public static final int PERMISSION_REQUEST_CODE = 1111;
    private static final int REQUEST = 1337;
    public static int SELECT_FROM_GALLERY = 2;
    public static int CAMERA_PIC_REQUEST = 0;
    Uri mImageCaptureUri;
    private static final int PICK_FILE_REQUEST = 1;
    ArrayList<SelectImage> stringArrayList = new ArrayList<>();
    Result result;
    Subservice subservice;
    private String subservice_id = "";
    private String no_of_dhol = "", no_of_water_cane = "", select_singer_type = "", no_of_guest = "", no_of_food_item = "";
    String Decoration_type = "";

    @Override
    protected int getContentResId() {
        return R.layout.activity_tent;
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
            if (result.getSubservice().size() > 0) {
                subservice = (Subservice) bundle.getSerializable("SubService");
                subservice_id = subservice.getId();
            }
            if (result.getServiceName().toLowerCase().contains("bands")) {
                imageLayout.setVisibility(View.GONE);
                endDateInputlayout.setVisibility(View.GONE);
                startDateEtv.setHint("Event Date");
            } else if (result.getServiceName().toLowerCase().contains("dj")) {
                startDateEtv.setHint("Event Date");
                imageLayout.setVisibility(View.GONE);
                endDateInputlayout.setVisibility(View.GONE);
            } else if (result.getServiceName().toLowerCase().contains("tent")) {
                startDateEtv.setHint("Starting Date");
            } else if (result.getServiceName().toLowerCase().contains("decoration")) {
                startDateEtv.setHint("Event Date");
                imageLayout.setVisibility(View.GONE);
                endDateInputlayout.setVisibility(View.GONE);
                noOfDholInputlayout.setVisibility(View.GONE);
                decorationTypeLayout.setVisibility(View.VISIBLE);
            } else if (result.getServiceName().toLowerCase().contains("lights")) {
                startDateEtv.setHint("Starting Date");
            } else if (result.getServiceName().toLowerCase().contains("dhol")) {
                imageLayout.setVisibility(View.GONE);
                endDateInputlayout.setVisibility(View.GONE);
                noOfDholInputlayout.setVisibility(View.VISIBLE);
                startDateEtv.setHint("Event Date");
                noOfDholEtv.setHint("No.of Dhol");
            } else if (result.getServiceName().toLowerCase().contains("event planner")) {
                imageLayout.setVisibility(View.GONE);
                endDateInputlayout.setVisibility(View.GONE);
                noOfDholInputlayout.setVisibility(View.GONE);
                startDateEtv.setHint("Event Date");
            } else if (result.getServiceName().toLowerCase().contains("water cane")) {
                imageLayout.setVisibility(View.GONE);
                endDateInputlayout.setVisibility(View.GONE);
                noOfDholInputlayout.setVisibility(View.VISIBLE);
                startDateEtv.setHint("Event Date");
                noOfDholEtv.setHint("No. of water cane");
            } else if (result.getServiceName().toLowerCase().contains("singer/dancer")) {
                imageLayout.setVisibility(View.GONE);
                endDateInputlayout.setVisibility(View.GONE);
                noOfDholInputlayout.setVisibility(View.GONE);
                selectTypeLayout.setVisibility(View.VISIBLE);
                startDateEtv.setHint("Event Date");
            } else if (result.getServiceName().toLowerCase().contains("waiter")) {
                imageLayout.setVisibility(View.GONE);
                endDateInputlayout.setVisibility(View.GONE);
                noOfDholInputlayout.setVisibility(View.VISIBLE);
                selectTypeLayout.setVisibility(View.GONE);
                noOfFoodInputlayout.setVisibility(View.VISIBLE);
                startDateEtv.setHint("Event Date");
                noOfDholEtv.setHint("No.of Guest");
            } else if (result.getServiceName().toLowerCase().contains("halwai")) {
                imageLayout.setVisibility(View.VISIBLE);
                endDateInputlayout.setVisibility(View.GONE);
                noOfDholInputlayout.setVisibility(View.VISIBLE);
                selectTypeLayout.setVisibility(View.GONE);
                noOfFoodInputlayout.setVisibility(View.VISIBLE);
                startDateEtv.setHint("Event Date");
                noOfDholEtv.setHint("No.of Guest");
            }
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(startDateEtv.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);

        InputMethodManager imm1 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm1.hideSoftInputFromWindow(endDateEtv.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);

        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (select_singer_type.equals("")){
                        select_singer_type="Singer";
                    }else {
                        select_singer_type=select_singer_type+"Singer";
                    }
                }else {
                    if (select_singer_type.equals("")){
                        select_singer_type="";
                    }else {
                        select_singer_type="Dancer";
                    }
                }
            }
        });radioButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (select_singer_type.equals("")){
                        select_singer_type="Dancer";
                    }else {
                        select_singer_type=select_singer_type+"Dancer";
                    }
                }else {
                    if (select_singer_type.equals("")){
                        select_singer_type="";
                    }else {
                        select_singer_type="Singer";
                    }
                }
            }
        });
        flowerCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (Decoration_type.equals("")) {
                        Decoration_type = "Flower";
                    }else {
                        Decoration_type=Decoration_type+"Flower";
                    }
                }
            }
        });
        statueCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (Decoration_type.equals("")) {
                        Decoration_type = "Statue";
                    }else {
                        Decoration_type=Decoration_type+"Statue";
                    }
                }
            }
        }); carCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (Decoration_type.equals("")) {
                        Decoration_type = "Car";
                    }else {
                        Decoration_type=Decoration_type+"Car";
                    }
                }
            }
        });
    }

    @OnClick({R.id.start_date_etv, R.id.end_date_etv, R.id.submit_btn, R.id.select_image_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_date_etv:
                DatePickerCall = "Start";
                datepicker();
                break;
            case R.id.end_date_etv:
                DatePickerCall = "End";
                datepicker();
                break;
            case R.id.submit_btn:
                if (result.getServiceName().toLowerCase().contains("tent")) {
                    if (UserAccount.isEmpty(eventNameEtv, startDateEtv, endDateEtv, cityVillageEtv, tehsilEtv)) {
                        if (stringArrayList.size() > 0) {
                            submitReportApi();
                        } else {
                            ErrorMessage.T(TentActivity.this, "Select Images First !");
                        }
                    } else {
                        UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
                        UserAccount.EditTextPointer.requestFocus();
                    }
                } else if (result.getServiceName().toLowerCase().contains("decoration")) {
                    if (UserAccount.isEmpty(eventNameEtv, startDateEtv, cityVillageEtv, tehsilEtv)) {
                        if (!Decoration_type.equals("")) {
                            submitReportApi_band();
                        } else {
                            ErrorMessage.T(TentActivity.this, "Select Decoration Type !");
                        }
                    } else {
                        UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
                        UserAccount.EditTextPointer.requestFocus();
                    }
                } else if (result.getServiceName().toLowerCase().contains("lights")) {
                    if (UserAccount.isEmpty(eventNameEtv, startDateEtv, endDateEtv, cityVillageEtv, tehsilEtv)) {
                        if (stringArrayList.size() > 0) {
                            submitReportApi();
                        } else {
                            ErrorMessage.T(TentActivity.this, "Select Images First !");
                        }
                    } else {
                        UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
                        UserAccount.EditTextPointer.requestFocus();
                    }
                } else if (result.getServiceName().toLowerCase().contains("lights")) {
                    if (UserAccount.isEmpty(eventNameEtv, startDateEtv, endDateEtv, cityVillageEtv, tehsilEtv)) {
                        if (stringArrayList.size() > 0) {
                            submitReportApi();
                        } else {
                            ErrorMessage.T(TentActivity.this, "Select Images First !");
                        }
                    } else {
                        UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
                        UserAccount.EditTextPointer.requestFocus();
                    }
                } else if (result.getServiceName().toLowerCase().contains("band")) {
                    if (UserAccount.isEmpty(eventNameEtv, startDateEtv, cityVillageEtv, tehsilEtv)) {
                        submitReportApi_band();
                    } else {
                        UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
                        UserAccount.EditTextPointer.requestFocus();
                    }
                } else if (result.getServiceName().toLowerCase().contains("dj")) {
                    if (UserAccount.isEmpty(eventNameEtv, startDateEtv, cityVillageEtv, tehsilEtv)) {
                        submitReportApi_band();
                    } else {
                        UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
                        UserAccount.EditTextPointer.requestFocus();
                    }
                } else if (result.getServiceName().toLowerCase().contains("dhol")) {
                    if (UserAccount.isEmpty(eventNameEtv, startDateEtv, noOfDholEtv, cityVillageEtv, tehsilEtv)) {
                        no_of_dhol = noOfDholEtv.getText().toString();
                        submitReportApi_band();
                    } else {
                        UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
                        UserAccount.EditTextPointer.requestFocus();
                    }
                } else if (result.getServiceName().toLowerCase().contains("event planner")) {
                    if (UserAccount.isEmpty(eventNameEtv, startDateEtv, cityVillageEtv, tehsilEtv)) {
                        submitReportApi_band();
                    } else {
                        UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
                        UserAccount.EditTextPointer.requestFocus();
                    }
                } else if (result.getServiceName().toLowerCase().contains("water cane")) {
                    if (UserAccount.isEmpty(eventNameEtv, startDateEtv, noOfDholEtv, cityVillageEtv, tehsilEtv)) {
                        no_of_water_cane = noOfDholEtv.getText().toString();
                        submitReportApi_band();
                    } else {
                        UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
                        UserAccount.EditTextPointer.requestFocus();
                    }
                } else if (result.getServiceName().toLowerCase().contains("singer/dancer")) {
                    if (UserAccount.isEmpty(eventNameEtv, startDateEtv, cityVillageEtv, tehsilEtv)) {
                        submitReportApi_band();
                    } else {
                        UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
                        UserAccount.EditTextPointer.requestFocus();
                    }
                } else if (result.getServiceName().toLowerCase().contains("waiter")) {
                    if (UserAccount.isEmpty(eventNameEtv, startDateEtv, noOfDholEtv, noOfFoodEtv, cityVillageEtv, tehsilEtv)) {
                        no_of_guest = noOfDholEtv.getText().toString();
                        no_of_food_item = noOfFoodEtv.getText().toString();
                        submitReportApi_band();
                    } else {
                        UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
                        UserAccount.EditTextPointer.requestFocus();
                    }
                } else if (result.getServiceName().toLowerCase().contains("halwai")) {
                    if (UserAccount.isEmpty(eventNameEtv, startDateEtv, noOfDholEtv, noOfFoodEtv, cityVillageEtv, tehsilEtv)) {
                        no_of_guest = noOfDholEtv.getText().toString();
                        no_of_food_item = noOfFoodEtv.getText().toString();
                        if (stringArrayList.size() > 0) {
                            submitReportApi();
                        } else {
                            ErrorMessage.T(TentActivity.this, "Select Images First !");
                        }
                    } else {
                        UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
                        UserAccount.EditTextPointer.requestFocus();
                    }
                }

                break;
            case R.id.select_image_layout:
                selectImage();
                break;
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int eventYear, int monthOfYear, int dayOfMonth) {
        if (DatePickerCall.equals("Start")) {
            if (monthOfYear + 1 < 10 && dayOfMonth > 10)
                startDateEtv.setText((eventYear + "-0" + (monthOfYear + 1) + "-" + dayOfMonth));
            else if (dayOfMonth < 10 && monthOfYear + 1 >= 10)
                startDateEtv.setText((eventYear + "-" + (monthOfYear + 1) + "-0" + dayOfMonth));
            else if (monthOfYear + 1 < 10 && dayOfMonth < 10)
                startDateEtv.setText((eventYear + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth));
            else startDateEtv.setText((eventYear + "-" + (monthOfYear + 1) + "-" + dayOfMonth));
        } else if (DatePickerCall.equals("End")) {
            if (monthOfYear + 1 < 10 && dayOfMonth > 10)
                endDateEtv.setText((eventYear + "-0" + (monthOfYear + 1) + "-" + dayOfMonth));
            else if (dayOfMonth < 10 && monthOfYear + 1 >= 10)
                endDateEtv.setText((eventYear + "-" + (monthOfYear + 1) + "-0" + dayOfMonth));
            else if (monthOfYear + 1 < 10 && dayOfMonth < 10)
                endDateEtv.setText((eventYear + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth));
            else endDateEtv.setText((eventYear + "-" + (monthOfYear + 1) + "-" + dayOfMonth));
        }

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
        MultipartBody.Part[] surveyImagesParts = new MultipartBody.Part[stringArrayList.size()];
        ErrorMessage.E("Tokan" + UserProfileHelper.getInstance().getUserProfileModel().get(0).getUid());
        for (int index = 0; index < stringArrayList.size(); index++) {
            File file = new File(stringArrayList.get(index).getImage_path());
            RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), file);
            surveyImagesParts[index] = MultipartBody.Part.createFormData("images[]", file.getName(), surveyBody);
        }
        if (NetworkUtil.isNetworkAvailable(TentActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(TentActivity.this);
            ErrorMessage.E("request" + UserProfileHelper.getInstance().getUserProfileModel().get(0).getUid() + ">>" + result.getServiceId() + ">>" + subservice_id + ">>" + eventNameEtv.getText().toString() + ">>" + startDateEtv.getText().toString() + ">>" + endDateEtv.getText().toString() + ">>" + cityVillageEtv.getText().toString() + ">>" + tehsilEtv.getText().toString() + ">>" + no_of_guest + ">>" + no_of_food_item + ">>" + surveyImagesParts);
            Call<ResponseBody> call = AppConfig.getLoadInterface().service_request(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUid(), result.getServiceId(), subservice_id, eventNameEtv.getText().toString(), startDateEtv.getText().toString(), endDateEtv.getText().toString(), cityVillageEtv.getText().toString(), tehsilEtv.getText().toString(), no_of_guest, no_of_food_item, surveyImagesParts);
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
                                ErrorMessage.T(TentActivity.this, jsonObject.getString("message"));
                                //ErrorMessage.I_clear(getApplicationContext(), TechDashboardActivity.class, null);
                                finish();
                                //getProfileCall();
                            } else {
                                ErrorMessage.T(TentActivity.this, jsonObject.getString("message"));
                                materialDialog.dismiss();


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ErrorMessage.T(TentActivity.this, "Server Error");
                            ErrorMessage.E("JsonExc" + e.toString());
                            materialDialog.dismiss();

                        } catch (IOException e) {
                            e.printStackTrace();
                            ErrorMessage.T(TentActivity.this, "Server Error");
                            ErrorMessage.E("IOExc" + e.toString());
                            materialDialog.dismiss();

                        }
                    } else {
                        ErrorMessage.T(TentActivity.this, "Response not successful");
                        materialDialog.dismiss();

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    ErrorMessage.T(TentActivity.this, "Response Fail");
                    System.out.println("============update profile fail  :" + t.toString());
                    materialDialog.dismiss();
                }
            });

        } else {
            ErrorMessage.T(TentActivity.this, "No internet Connection");
        }
    }

    private void submitReportApi_band() {
        if (NetworkUtil.isNetworkAvailable(TentActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(TentActivity.this);
            Call<ResponseBody> call = AppConfig.getLoadInterface().service_request_WithoutImage(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUid(), result.getServiceId(), subservice_id, eventNameEtv.getText().toString(), startDateEtv.getText().toString(), cityVillageEtv.getText().toString(), tehsilEtv.getText().toString(), no_of_dhol, no_of_water_cane, select_singer_type, no_of_guest, no_of_food_item,Decoration_type);
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
                                ErrorMessage.T(TentActivity.this, jsonObject.getString("message"));
                                //ErrorMessage.I_clear(getApplicationContext(), TechDashboardActivity.class, null);
                                finish();
                                //getProfileCall();
                            } else {
                                ErrorMessage.T(TentActivity.this, jsonObject.getString("message"));
                                materialDialog.dismiss();


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ErrorMessage.T(TentActivity.this, "Server Error");
                            ErrorMessage.E("JsonExc" + e.toString());
                            materialDialog.dismiss();

                        } catch (IOException e) {
                            e.printStackTrace();
                            ErrorMessage.T(TentActivity.this, "Server Error");
                            ErrorMessage.E("IOExc" + e.toString());
                            materialDialog.dismiss();

                        }
                    } else {
                        ErrorMessage.T(TentActivity.this, "Response not successful");
                        materialDialog.dismiss();

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    ErrorMessage.T(TentActivity.this, "Response Fail");
                    System.out.println("============update profile fail  :" + t.toString());
                    materialDialog.dismiss();
                }
            });

        } else {
            ErrorMessage.T(TentActivity.this, "No internet Connection");
        }
    }

    /*  private void submitReportApi_dhol() {
          if (NetworkUtil.isNetworkAvailable(TentActivity.this)) {
              final Dialog materialDialog = ErrorMessage.initProgressDialog(TentActivity.this);
              Call<ResponseBody> call = AppConfig.getLoadInterface().service_request_WithoutImage(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUid(), result.getServiceId(), subservice_id, eventNameEtv.getText().toString(), startDateEtv.getText().toString(), cityVillageEtv.getText().toString(), tehsilEtv.getText().toString());
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
                                  ErrorMessage.T(TentActivity.this, jsonObject.getString("message"));
                                  //ErrorMessage.I_clear(getApplicationContext(), TechDashboardActivity.class, null);

                                  //getProfileCall();
                              } else {
                                  ErrorMessage.T(TentActivity.this, jsonObject.getString("message"));
                                  materialDialog.dismiss();


                              }
                          } catch (JSONException e) {
                              e.printStackTrace();
                              ErrorMessage.T(TentActivity.this, "Server Error");
                              ErrorMessage.E("JsonExc" + e.toString());
                              materialDialog.dismiss();

                          } catch (IOException e) {
                              e.printStackTrace();
                              ErrorMessage.T(TentActivity.this, "Server Error");
                              ErrorMessage.E("IOExc" + e.toString());
                              materialDialog.dismiss();

                          }
                      } else {
                          ErrorMessage.T(TentActivity.this, "Response not successful");
                          materialDialog.dismiss();

                      }
                  }

                  @Override
                  public void onFailure(Call<ResponseBody> call, Throwable t) {
                      ErrorMessage.T(TentActivity.this, "Response Fail");
                      System.out.println("============update profile fail  :" + t.toString());
                      materialDialog.dismiss();
                  }
              });

          } else {
              ErrorMessage.T(TentActivity.this, "No internet Connection");
          }
      }
  */
    private void selectImage() {
        final CharSequence[] options = {"From Camera", "From Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please choose an Image");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("From Camera")) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkCameraPermission()) openCameraIntent();
                        else requestPermission();
                    } else openCameraIntent();
                } else if (options[item].equals("From Gallery")) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkGalleryPermission()) galleryIntent();
                        else requestGalleryPermission();
                    } else galleryIntent();
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.create().show();
    }

    /* private void galleryIntent() {
         Intent intent = new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
         startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_FROM_GALLERY);
     }*/
    private void galleryIntent() {
        Intent intent = new Intent().setType("image/*").setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_FROM_GALLERY);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent, CAMERA_PIC_REQUEST);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(TentActivity.this, new String[]{CAMERA}, PERMISSION_REQUEST_CODE);
    }

    private void requestGalleryPermission() {
        ActivityCompat.requestPermissions(TentActivity.this, new String[]{READ_EXTERNAL_STORAGE}, REQUEST);
    }

    private boolean checkCameraPermission() {
        int result1 = ContextCompat.checkSelfPermission(TentActivity.this, CAMERA);
        return result1 == PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkGalleryPermission() {
        int result2 = ContextCompat.checkSelfPermission(TentActivity.this, READ_EXTERNAL_STORAGE);
        return result2 == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_PIC_REQUEST && resultCode == Activity.RESULT_OK) {

            //filePathTxtvw.setText(selectedFilePath);
            SelectImage selectImage = new SelectImage();
            selectImage.setImage_path(selectedFilePath);
            stringArrayList.add(selectImage);
            UploadImage_Adapter assignedTaskAdapter = new UploadImage_Adapter(TentActivity.this, stringArrayList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
            imageRcv.setLayoutManager(mLayoutManager);
            imageRcv.setItemAnimator(new DefaultItemAnimator());
            imageRcv.setAdapter(assignedTaskAdapter);
            ErrorMessage.E("selectedImagePath Camera" + selectedFilePath);
        } else if (requestCode == SELECT_FROM_GALLERY && resultCode == Activity.RESULT_OK && null != data) {
            try {
                if (data == null) {
                    //no data present
                    return;
                }
                Uri selectedFileUri = data.getData();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedFileUri);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("", "Selected File IOException" + e.toString());
                }
                selectedFilePath = getRealPathFromURIPath(selectedFileUri, TentActivity.this);
                Log.i("", "Selected File Path:" + selectedFilePath);

                if (selectedFilePath != null && !selectedFilePath.equals("")) {
                    //filePathTxtvw.setText(selectedFilePath);
                } else {
                    Toast.makeText(this, "Cannot upload file to server", Toast.LENGTH_SHORT).show();
                }
                SelectImage selectImage = new SelectImage();
                selectImage.setImage_path(selectedFilePath);
                stringArrayList.add(selectImage);
                UploadImage_Adapter assignedTaskAdapter = new UploadImage_Adapter(TentActivity.this, stringArrayList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
                imageRcv.setLayoutManager(mLayoutManager);
                imageRcv.setItemAnimator(new DefaultItemAnimator());
                imageRcv.setAdapter(assignedTaskAdapter);
            } catch (Exception e) {
            }
        }
    }


    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = null;
        int idx = 0;
        String s = "";
        try {
            cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
            if (cursor == null) {
                return contentURI.getPath();
            } else {
                cursor.moveToFirst();
                idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                s = cursor.getString(idx);
            }
        } catch (IllegalStateException e) {
            Log.e("Exception image", "selected " + e.toString());
        }

        return s;

    }


    private File storeCameraPhotoInSDCard(Bitmap bitmap, String currentDate) {
        File outputFile1 = new File(Environment.getExternalStorageDirectory(), "MOV Team");
        if (!outputFile1.exists()) {
            outputFile1.mkdir();
        }
        File outputFile = new File(outputFile1, "photo_" + currentDate + ".jpg");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputFile;
    }

    private Bitmap getImageFileFromSDCard(String filename) {
        Bitmap bitmap = null;
        File imageFile = new File(Environment.getExternalStorageDirectory() + filename);
        try {
            FileInputStream fis = new FileInputStream(imageFile);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private void openCameraIntent() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
            //Create a file to store the image
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(TentActivity.this, "com.pixelmarketo.nrh.provider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(pictureIntent, CAMERA_PIC_REQUEST);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */);
        selectedFilePath = image.getAbsolutePath();
        return image;
    }

    public String compressImage(String imageUri) {

        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }
        ErrorMessage.E("Image_Size" + inSampleSize);
        return inSampleSize;
    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "NRH/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }
}
