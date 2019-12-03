package com.pixelmarketo.nrh;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pixelmarketo.nrh.database.UserProfileHelper;
import com.pixelmarketo.nrh.database.UserProfileModel;
import com.pixelmarketo.nrh.utility.AppConfig;
import com.pixelmarketo.nrh.utility.ErrorMessage;
import com.pixelmarketo.nrh.utility.LoadInterface;
import com.pixelmarketo.nrh.utility.NetworkUtil;
import com.pixelmarketo.nrh.utility.SavedData;
import com.pixelmarketo.nrh.utility.UserAccount;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VenderLoginActivity extends AppCompatActivity implements PaymentResultListener {

    @BindView(R.id.email_et)
    EditText emailEt;
    @BindView(R.id.password_et)
    EditText passwordEt;
    @BindView(R.id.loggin_btn)
    Button logginBtn;
    @BindView(R.id.register_btn)
    Button registerBtn;
String tokan="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vender_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.loggin_btn, R.id.register_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loggin_btn:
                if (UserAccount.isEmpty(emailEt, passwordEt)) {
                    if (UserAccount.isPhoneNumberLength(emailEt)) {
                        LoginOnServer();
                    } else {
                        UserAccount.EditTextPointer.setError("Mobile Number Invalid !");
                        UserAccount.EditTextPointer.requestFocus();
                    }
                } else {
                    UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
                    UserAccount.EditTextPointer.requestFocus();
                }
                break;
            case R.id.register_btn:
                ErrorMessage.I(VenderLoginActivity.this, Vender_RegistrationActivity.class, null);
                break;
        }
    }
    private void LoginOnServer() {
        if (NetworkUtil.isNetworkAvailable(VenderLoginActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(VenderLoginActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.vendor_login(emailEt.getText().toString(),passwordEt.getText().toString(),SavedData.getFCM_ID());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("Response" + response.code());
                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        try {
                            materialDialog.dismiss();
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("comes in cond" + object.toString());
                            if (object.getString("status").equals("200")) {
                                ErrorMessage.E("comes in if cond" + object.toString());
                                ErrorMessage.T(VenderLoginActivity.this, object.getString("message"));
                                JSONObject jsonObject1 = object.getJSONObject("result");
                                UserProfileModel userProfileModel = new UserProfileModel();
                                //userProfileModel.setId(jsonObject1.getString("user_id"));
                                userProfileModel.setDisplayName(jsonObject1.getString("fullname"));
                                userProfileModel.setUid(jsonObject1.getString("token"));
                                userProfileModel.setProvider("User");
                                userProfileModel.setUserType("Vender");
                                userProfileModel.setProfile_pic("");
                                SavedData.savePayment_Status("1");
                                userProfileModel.setUserPhone(jsonObject1.getString("contact"));
                                UserProfileHelper.getInstance().insertUserProfileModel(userProfileModel);
                                ErrorMessage.I(VenderLoginActivity.this, VenderDashboardActivity.class, null);
                            } else if (object.getString("status").equals("300")){
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(VenderLoginActivity.this, object.getString("message"));
                                Confirmation_PopUP(object.getString("service_charge"),object.getString("token"));
                            }else {
                                ErrorMessage.T(VenderLoginActivity.this, object.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("JsonException" + e);
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
            ErrorMessage.T(VenderLoginActivity.this, "No Internet");
        }
    }
    public void Confirmation_PopUP(String serviceCharge, String token) {
        Dialog dialog = new Dialog(VenderLoginActivity.this);
        dialog.setContentView(R.layout.payment_confirmation_popup);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Button submit_btn = (Button) dialog.findViewById(R.id.submit_payment_btn);
        submit_btn.setText("Pay ₹ " + serviceCharge);
        tokan=token;
        submit_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (serviceCharge.contains("0") || serviceCharge.contains("00")) {
                    ConfirmPaymentOnServer(token," No Amount");
                } else {
                    startPayment(Double.parseDouble(serviceCharge + "00"));
                }
            }
        });
        dialog.show();
    }

    private void ConfirmPaymentOnServer(String token,String transcaion_id) {
        if (NetworkUtil.isNetworkAvailable(VenderLoginActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(VenderLoginActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.conformation_payment(token, transcaion_id, "Test123");
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("Response" + response.code());
                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        try {
                            materialDialog.dismiss();
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("comes in cond" + object.toString());
                            if (object.getString("status").equals("200")) {
                                SavedData.savePayment_Status("1");
                                ErrorMessage.E("comes in if cond" + object.toString());
                                ErrorMessage.T(VenderLoginActivity.this, object.getString("message"));
                                JSONObject jsonObject1 = object.getJSONObject("result");
                                UserProfileModel userProfileModel = new UserProfileModel();
                                //userProfileModel.setId(jsonObject1.getString("user_id"));
                                userProfileModel.setDisplayName(jsonObject1.getString("fullname"));
                                userProfileModel.setUid(jsonObject1.getString("token"));
                                userProfileModel.setProvider("User");
                                userProfileModel.setUserType("Vender");
                                userProfileModel.setUserPhone(jsonObject1.getString("contact"));
                                userProfileModel.setProvider(jsonObject1.getString("district"));
                                userProfileModel.setEmaiiId(jsonObject1.getString("state"));
                                userProfileModel.setProfile_pic(jsonObject1.getString("city"));
                                userProfileModel.setUserVenderCode(jsonObject1.getString("vendor_code"));
                                UserProfileHelper.getInstance().insertUserProfileModel(userProfileModel);
                                SavedData.savePayment_Status("1");
                                ErrorMessage.I(VenderLoginActivity.this, VenderDashboardActivity.class, null);
                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(VenderLoginActivity.this, object.getString("message"));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("JsonException" + e);
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
            ErrorMessage.T(VenderLoginActivity.this, "No Internet");
        }
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
            options.put("description", "Vender Subscription");
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

    @Override
    public void onPaymentSuccess(String s) {
        ConfirmPaymentOnServer(tokan,s);
    }

    @Override
    public void onPaymentError(int i, String s) {

    }
}
