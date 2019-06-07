package com.numerical.numerical.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonObject;
import com.numerical.numerical.R;
import com.numerical.numerical.Utility.ApiClient;
import com.numerical.numerical.Utility.Const;
import com.numerical.numerical.Utility.ErrorMessage;
import com.numerical.numerical.Utility.NetworkUtil;
import com.numerical.numerical.Utility.UserAccount;
import com.numerical.numerical.adapters.feed_Adapter;
import com.numerical.numerical.database.UserProfileHelper;
import com.numerical.numerical.database.UserProfileModel;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.services.AccountService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.login_btn)
    TextView loginBtn;
    @BindView(R.id.signup_tv)
    TextView signupTv;
    @BindView(R.id.forgot_password_tv)
    TextView forgotPasswordTv;
    @BindView(R.id.twitterLogin)
    TwitterLoginButton twitterLogin;
    @BindView(R.id.twitter_btn)
    ImageButton twitterBtn;


    @BindView(R.id.login_button)
    LoginButton loginButton;
    @BindView(R.id.facebook_btn)
    ImageButton facebookBtn;
    @BindView(R.id.google_btn)
    ImageButton googleBtn;
    @BindView(R.id.sign_in_button)
    SignInButton signInButton;
    @BindView(R.id.email_id_et)
    EditText emailIdEt;
    @BindView(R.id.password_et)
    EditText passwordEt;
    private CallbackManager callbackManager;
    private static final int RC_SIGN_IN = 007;
    private GoogleApiClient mGoogleApiClient;

    private FirebaseAuth mAuth;
    private DatabaseReference mReference;
    String CheckLogin = "";
    private HashMap<String, String> userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        //Fabric.with(this, new Twitter(authConfig));
        mAuth = FirebaseAuth.getInstance();
        mReference = FirebaseDatabase.getInstance().getReference().child("Users");

        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                /*getUserDetails(loginResult);*/
                final String image = "https://graph.facebook.com/" + AccessToken.getCurrentAccessToken().getUserId() + "/picture?type=large&width=720&height=720";
                //__________Create a graph request
                GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            //_______put data into user data map
                            CheckLogin = "facebook";
                            checkLogin(object.get("name").toString(), object.get("email").toString(), "123");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                //__________add parameters for required data
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);

                //__________eqecute request
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        // Customizing G+ button
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());

        twitterLogin.setCallback(new Callback<TwitterSession>() {

            @Override
            public void success(Result<TwitterSession> result) {
                final TwitterSession session = result.data;

                ErrorMessage.E("result" + session.getUserName());
                ErrorMessage.E("result" + session.getUserId());
                ErrorMessage.E("result" + session.getAuthToken());

                TwitterAuthClient authClient = new TwitterAuthClient();
                authClient.requestEmail(session, new Callback<String>() {
                    @Override
                    public void success(Result<String> result) {
                        String data = result.data;
                        ErrorMessage.E("result" + data.toString());
                        // Do something with the result, which provides the email address
                        CheckLogin = "twitter";
                        checkLogin(session.getUserName(), data.toString(), "123");

                    }

                    @Override
                    public void failure(TwitterException exception) {
                        // Do something on failure
                    }
                });

            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
            }
        });

    }

    @OnClick({R.id.login_btn, R.id.signup_tv, R.id.forgot_password_tv, R.id.twitter_btn, R.id.facebook_btn, R.id.google_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                //ErrorMessage.I(LoginActivity.this, LoginActivity.class, null);
                if (UserAccount.isEmpty(emailIdEt, passwordEt)) {
                    if (UserAccount.isEmailValid(emailIdEt)) {
                        if (UserAccount.isPasswordValid(passwordEt)) {
                            CheckLogin = "Manual";
                            checkLogin("", emailIdEt.getText().toString().trim(), passwordEt.getText().toString().trim());
                        } else {
                            UserAccount.EditTextPointer.setError("Password greater then 6 Digit !");
                            UserAccount.EditTextPointer.requestFocus();
                        }
                    } else {
                        UserAccount.EditTextPointer.setError("Email-ID Invalid !");
                        UserAccount.EditTextPointer.requestFocus();
                    }
                } else {
                    UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
                    UserAccount.EditTextPointer.requestFocus();
                }
                break;
            case R.id.signup_tv:
                ErrorMessage.I(LoginActivity.this, SignUpActivity.class, null);
                break;
            case R.id.forgot_password_tv:
                ErrorMessage.I(LoginActivity.this, ForgotPasswordActivity.class, null);
                break;
            case R.id.twitter_btn:
                twitterLogin.performClick();
                break;
            case R.id.facebook_btn:
                loginButton.performClick();
                break;
            case R.id.google_btn:
                signIn();
                break;
        }
    }

    private void genrateKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.numerical.numerical", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {

            }
        });
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {

            }
        });
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.e("google", "handleSignInResult:" + result.toString());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            try {
                GoogleSignInAccount acct = result.getSignInAccount();
                CheckLogin = "google";
                checkLogin(acct.getDisplayName(), acct.getEmail(), "123");
            } catch (Exception e) {

            }
        } else {
            // Signed out, show unauthenticated UI.

        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RC_SIGN_IN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleSignInResult(result);
            } else {
                callbackManager.onActivityResult(requestCode, resultCode, data);
                twitterLogin.onActivityResult(requestCode, resultCode, data);
            }
        } catch (Exception e) {
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    private void checkLogin(final String DisplayName, final String emailID, final String Password) {
        final Dialog materialDialog = ErrorMessage.initProgressDialog(LoginActivity.this);
        ErrorMessage.E("Request" + DisplayName + "<>" + emailID + "<>" + Password);
        mAuth.signInWithEmailAndPassword(emailID, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Sucessful", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    /*ErrorMessage.E("Sucessful Email" + user.getEmail());*/
                    materialDialog.dismiss();
                    LoginWithServer(user.getEmail(), DisplayName, user.getUid());
                } else {
                    materialDialog.dismiss();
                    if (!CheckLogin.equals("Manual")) {
                        startRegister(emailID, Password, DisplayName);
                    }

                }
            }

        });

    }

    private void startRegister(final String EmailID, String Password, final String DisplayName) {
        Log.d("blog", "start regiaster");
        final Dialog materialDialog = ErrorMessage.initProgressDialog(LoginActivity.this);
        mAuth.createUserWithEmailAndPassword(EmailID.trim(), Password.trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("blog", "craeted user " + task.getException());
                if (task.isSuccessful()) {
                    String user_id = mAuth.getCurrentUser().getUid();
                    ErrorMessage.E("Firebase user_id" + user_id + "<>" + mAuth.getCurrentUser().getEmail() + "<>" + mAuth.getCurrentUser().getPhotoUrl());
                    DatabaseReference current_user_db = mReference.child(user_id);
                    current_user_db.child("name").setValue("Burhan");
                    current_user_db.child("image").setValue("default");
                    Toast.makeText(getApplicationContext(), "Register Successfully.", Toast.LENGTH_SHORT).show();
                    materialDialog.dismiss();
                    LoginWithServer(EmailID, DisplayName, user_id);
                       /* Intent newIntent = new Intent(RegisterActivity.this, MainActivity.class);
                        newIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(newIntent);*/
                } else {
                    materialDialog.dismiss();
                }
            }
        });

    }

    private void LoginWithServer(final String EmailID, String DisplayName, String Uid) {
        if (NetworkUtil.isNetworkAvailable(LoginActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(LoginActivity.this);
            Call<JsonObject> call = null;
            if (CheckLogin.equals("Manual")) {
                final JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("email", EmailID);
                ErrorMessage.E("Request LoginWithServer" + jsonObject.toString());
                call = ApiClient.getLoadInterface().user_login(jsonObject);
            } else {
                final JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("displayName", DisplayName);
                jsonObject.addProperty("email", EmailID);
                jsonObject.addProperty("provider", CheckLogin);
                jsonObject.addProperty("uid", Uid);
                call = ApiClient.getLoadInterface().Social_LOGIN_URL(jsonObject);
            }

            call.enqueue(new retrofit2.Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    ErrorMessage.E("error code" + response.code());
                    if (response.code() == 200) {
                        try {
                            materialDialog.dismiss();
                            ErrorMessage.E("Response" + response.body().toString());
                            JSONObject jsonObject = new JSONObject(response.body().toString());
                            UserProfileModel userProfileModel = new UserProfileModel();
                            userProfileModel.setId(jsonObject.getString("_id"));
                            userProfileModel.setUser_id(jsonObject.getString("username"));
                            userProfileModel.setUid(jsonObject.getString("uid"));
                            userProfileModel.setDisplayName(jsonObject.getString("displayName"));
                            userProfileModel.setProvider(jsonObject.getString("provider"));
                            userProfileModel.setEmaiiId(EmailID);
                            UserProfileHelper.getInstance().insertUserProfileModel(userProfileModel);
                            ErrorMessage.I_clear(LoginActivity.this, DashBoardActivity.class, null);
                        } catch (Exception e) {
                            e.printStackTrace();
                            ErrorMessage.E("IOException" + e.toString());
                            ErrorMessage.T(LoginActivity.this, "Server Error");
                            materialDialog.dismiss();
                        }
                    } else {
                        ErrorMessage.T(LoginActivity.this, "Response not successful");
                        materialDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    ErrorMessage.T(LoginActivity.this, "Response Fail");
                    System.out.println("============update profile fail  :" + t.toString());
                    materialDialog.dismiss();
                }
            });

        } else {
            ErrorMessage.T(LoginActivity.this, this.getString(R.string.no_internet));

        }

    }

}

