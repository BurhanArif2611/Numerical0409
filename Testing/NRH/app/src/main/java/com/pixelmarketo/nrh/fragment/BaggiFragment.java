package com.pixelmarketo.nrh.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.pixelmarketo.nrh.R;
import com.pixelmarketo.nrh.activity.user.CantensActivity;
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
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BaggiFragment extends Fragment implements DatePickerDialog.OnDateSetListener{
    View view;
    @BindView(R.id.event_name_etv)
    EditText eventNameEtv;
    @BindView(R.id.start_date_etv)
    EditText startDateEtv;
    @BindView(R.id.no_of_days_etv)
    EditText noOfDaysEtv;
    @BindView(R.id.no_of_baggi_etv)
    EditText noOfBaggiEtv;
    @BindView(R.id.city_village_etv)
    EditText cityVillageEtv;
    @BindView(R.id.tehsil_etv)
    EditText tehsilEtv;
    @BindView(R.id.submit_btn)
    Button submitBtn;
    Unbinder unbinder;
    private Result result;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_baggi, container, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            result = (Result) bundle.getSerializable("result");
        }
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserAccount.isEmpty(eventNameEtv, startDateEtv, noOfDaysEtv,noOfBaggiEtv, cityVillageEtv, tehsilEtv)) {
                    submitReportApi_band();
                } else {
                    UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
                    UserAccount.EditTextPointer.requestFocus();
                }
            }
        });
        startDateEtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepicker();
            }
        });
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    private void submitReportApi_band() {
        if (NetworkUtil.isNetworkAvailable(getActivity())) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(getActivity());
            Call<ResponseBody> call = AppConfig.getLoadInterface().service_request_WithoutImage(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUid(), result.getServiceId(), "", eventNameEtv.getText().toString(), startDateEtv.getText().toString(), cityVillageEtv.getText().toString(), tehsilEtv.getText().toString(), "", "", "", "", "", "","","","","",noOfBaggiEtv.getText().toString(),noOfDaysEtv.getText().toString(),"","");
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
                                ErrorMessage.T(getActivity(), jsonObject.getString("message"));
                                //ErrorMessage.I_clear(getApplicationContext(), TechDashboardActivity.class, null);
                                
                                //getProfileCall();
                            } else {
                                ErrorMessage.T(getActivity(), jsonObject.getString("message"));
                                materialDialog.dismiss();


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ErrorMessage.T(getActivity(), "Server Error");
                            ErrorMessage.E("JsonExc" + e.toString());
                            materialDialog.dismiss();

                        } catch (IOException e) {
                            e.printStackTrace();
                            ErrorMessage.T(getActivity(), "Server Error");
                            ErrorMessage.E("IOExc" + e.toString());
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
            ErrorMessage.T(getActivity(), "No internet Connection");
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
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setAccentColor("#0b346a");
        datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);
        datePickerDialog.setMinDate(calendar);
        Log.e("setMinDate", " == " + calendar.toString());
        datePickerDialog.show(getActivity().getFragmentManager(), "Date Picker");
    }
}
