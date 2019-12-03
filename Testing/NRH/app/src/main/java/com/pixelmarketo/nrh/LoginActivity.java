package com.pixelmarketo.nrh;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.pixelmarketo.nrh.database.UserProfileHelper;
import com.pixelmarketo.nrh.database.UserProfileModel;
import com.pixelmarketo.nrh.utility.AppConfig;
import com.pixelmarketo.nrh.utility.ErrorMessage;
import com.pixelmarketo.nrh.utility.LoadInterface;
import com.pixelmarketo.nrh.utility.NetworkUtil;
import com.pixelmarketo.nrh.utility.SavedData;
import com.pixelmarketo.nrh.utility.UserAccount;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.loggin_btn)
    Button logginBtn;
    @BindView(R.id.register_btn)
    Button registerBtn;
    @BindView(R.id.email_et)
    EditText emailEt;
    @BindView(R.id.forgot_password_txt)
    TextView forgotPasswordTxt;
    @BindView(R.id.password_et)
    EditText passwordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.loggin_btn, R.id.register_btn, R.id.email_et, R.id.forgot_password_txt})
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
                ErrorMessage.I(LoginActivity.this, RegisterActivity.class, null);
                break;
            case R.id.email_et:
                break;
            case R.id.forgot_password_txt:
                forgotPassword_PopUP();
                break;
        }
    }

    public void forgotPassword_PopUP() {
        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.forgot_password_popup);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
       /* final EditText email_et = (EditText) dialog.findViewById(R.id.email_et);
        final Button submit_btn = (Button) dialog.findViewById(R.id.submit_btn);*/

        /*submit_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                UserProfileHelper.getInstance().delete();
                ErrorMessage.I_clear(MainActivity.this, MainActivity.class, null);
            }
        });*/

        dialog.show();
    }



    private void LoginOnServer() {
        if (NetworkUtil.isNetworkAvailable(LoginActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(LoginActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.login(emailEt.getText().toString(),passwordEt.getText().toString(), SavedData.getFCM_ID());
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
                                ErrorMessage.T(LoginActivity.this, object.getString("message"));
                                JSONObject jsonObject1 = object.getJSONObject("result");
                                UserProfileModel userProfileModel = new UserProfileModel();
                                //userProfileModel.setId(jsonObject1.getString("user_id"));
                                userProfileModel.setDisplayName(jsonObject1.getString("fullname"));
                                userProfileModel.setUid(jsonObject1.getString("token"));
                                userProfileModel.setProvider("User");
                                userProfileModel.setProfile_pic(jsonObject1.getString("profile"));
                                userProfileModel.setUserPhone(jsonObject1.getString("contact"));
                                userProfileModel.setUserType("User");
                                UserProfileHelper.getInstance().insertUserProfileModel(userProfileModel);
                                ErrorMessage.I(LoginActivity.this, UserDashboardActivity.class, null);
                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(LoginActivity.this, object.getString("message"));

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
            ErrorMessage.T(LoginActivity.this, "No Internet");
        }
    }

}
