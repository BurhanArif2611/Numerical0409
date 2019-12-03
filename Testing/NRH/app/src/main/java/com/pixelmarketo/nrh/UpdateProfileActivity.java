package com.pixelmarketo.nrh;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.pixelmarketo.nrh.database.UserProfileHelper;
import com.pixelmarketo.nrh.database.UserProfileModel;
import com.pixelmarketo.nrh.utility.AppConfig;
import com.pixelmarketo.nrh.utility.ErrorMessage;
import com.pixelmarketo.nrh.utility.LoadInterface;
import com.pixelmarketo.nrh.utility.NetworkUtil;
import com.pixelmarketo.nrh.utility.UserAccount;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class UpdateProfileActivity extends AppCompatActivity implements TextWatcher {

    @BindView(R.id.proffileImage)
    CircleImageView proffileImage;
    @BindView(R.id.takeimage)
    ImageView takeimage;
    @BindView(R.id.first_name_et)
    EditText firstNameEt;
    @BindView(R.id.last_name_et)
    EditText lastNameEt;
    @BindView(R.id.email_et)
    EditText emailEt;
    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.register_btn)
    Button registerBtn;
    public static final int PERMISSION_REQUEST_CODE = 1111;
    private static final int REQUEST = 1337;
    public static int SELECT_FROM_GALLERY = 2;
    public static int CAMERA_PIC_REQUEST = 0;
    Uri mImageCaptureUri;
    private File finalFile;
    String selectedImagePath = "";
    private FirebaseAuth mAuth;
    private String mVerificationId;
    EditText editTextone, editTexttwo, editTextthree, editTextfour, editTextfifth, editTextsixth, mobile_number_et;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode("fr");
        mAuth.useAppLanguage();
        if (UserProfileHelper.getInstance().getUserProfileModel().size() > 0) {
            firstNameEt.setText(UserProfileHelper.getInstance().getUserProfileModel().get(0).getDisplayName());
            emailEt.setText(UserProfileHelper.getInstance().getUserProfileModel().get(0).getEmaiiId());
            phoneEt.setText(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUserPhone());
            if (bundle == null) {
                if (!UserProfileHelper.getInstance().getUserProfileModel().get(0).getProfile_pic().equals("")) {
                    Glide.with(UpdateProfileActivity.this).load(UserProfileHelper.getInstance().getUserProfileModel().get(0).getProfile_pic()).placeholder(R.drawable.ic_defult_user).into(proffileImage);
                }
            }
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(phoneEt.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    @OnClick({R.id.takeimage, R.id.phone_et, R.id.register_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.takeimage:
                selectImage();
                break;
            case R.id.phone_et:
                Otp_PopUP();
                break;
            case R.id.register_btn:
                if (UserAccount.isEmpty(firstNameEt, lastNameEt, emailEt, phoneEt)) {
                    if (UserAccount.isEmailValid(emailEt)) {
                        if (UserAccount.isPhoneNumberLength(phoneEt)) {
                            updateProfileApi();
                        } else {
                            UserAccount.EditTextPointer.setError("Mobile Number Invalid !");
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
        }
    }

    private void selectImage() {
        final CharSequence[] options = {"From Camera", "From Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please choose an Image");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("From Camera")) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkCameraPermission()) cameraIntent();
                        else requestPermission();
                    } else cameraIntent();
                    //   cameraIntent();
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
        ActivityCompat.requestPermissions(UpdateProfileActivity.this, new String[]{CAMERA}, PERMISSION_REQUEST_CODE);
    }

    private void requestGalleryPermission() {
        ActivityCompat.requestPermissions(UpdateProfileActivity.this, new String[]{READ_EXTERNAL_STORAGE}, REQUEST);
    }

    private boolean checkCameraPermission() {
        int result1 = ContextCompat.checkSelfPermission(UpdateProfileActivity.this, CAMERA);
        return result1 == PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkGalleryPermission() {
        int result2 = ContextCompat.checkSelfPermission(UpdateProfileActivity.this, READ_EXTERNAL_STORAGE);
        return result2 == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_PIC_REQUEST && resultCode == Activity.RESULT_OK && null != data) {
            Uri cameraURI = data.getData();
            Bitmap productImageBitmap = (Bitmap) data.getExtras().get("data");
            proffileImage.setImageBitmap(productImageBitmap);
            Uri tempUri = getImageUri(UpdateProfileActivity.this, productImageBitmap);
            // CALL THIS METHOD TO GET THE ACTUAL PATH
            finalFile = new File(getRealPathFromURI(tempUri));
            selectedImagePath = getRealPathFromURI(tempUri);
            ErrorMessage.E("selectedImagePath Camera" + selectedImagePath);
            ErrorMessage.E("finalFile Camera" + finalFile);
        } else if (requestCode == SELECT_FROM_GALLERY && resultCode == Activity.RESULT_OK && null != data) {
            Uri galleryURI = data.getData();
            proffileImage.setImageURI(galleryURI);
            selectedImagePath = getRealPathFromURIPath(galleryURI, UpdateProfileActivity.this);
            System.out.println("===========selectedImagePath on acti rest : " + selectedImagePath);
            /*finalFile = new File(selectedImagePath);*/
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

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    public void Otp_PopUP() {
        dialog = new Dialog(UpdateProfileActivity.this);
        dialog.setContentView(R.layout.input_otp_popup);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final TextInputLayout mobile_textinput = (TextInputLayout) dialog.findViewById(R.id.mobile_textinput);
        mobile_number_et = (EditText) dialog.findViewById(R.id.mobile_number_et);
        TextView title_tv = (TextView) dialog.findViewById(R.id.title_tv);
        TextView content_tv = (TextView) dialog.findViewById(R.id.content_tv);
        final LinearLayout layout_otp = (LinearLayout) dialog.findViewById(R.id.layout_otp);
        editTextone = (EditText) dialog.findViewById(R.id.editTextone);
        editTexttwo = (EditText) dialog.findViewById(R.id.editTexttwo);
        editTextthree = (EditText) dialog.findViewById(R.id.editTextthree);
        editTextfour = (EditText) dialog.findViewById(R.id.editTextfour);
        editTextfifth = (EditText) dialog.findViewById(R.id.editTextfifth);
        editTextsixth = (EditText) dialog.findViewById(R.id.editTextsixth);
        final Button submit_number_btn = (Button) dialog.findViewById(R.id.submit_number_btn);
        final Button submit_otp_btn = (Button) dialog.findViewById(R.id.submit_otp_btn);
        final Button cancel_btn = (Button) dialog.findViewById(R.id.cancel_btn);
        title_tv.setText("Enter Mobile Number");
        editTextone.addTextChangedListener(this);
        editTexttwo.addTextChangedListener(this);
        editTextthree.addTextChangedListener(this);
        editTextfour.addTextChangedListener(this);
        editTextfifth.addTextChangedListener(this);
        editTextsixth.addTextChangedListener(this);
        submit_number_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (UserAccount.isPhoneNumberLength(mobile_number_et)) {
                    sendVerificationCode(mobile_number_et.getText().toString());
                    mobile_textinput.setVisibility(View.GONE);
                    layout_otp.setVisibility(View.VISIBLE);
                    submit_number_btn.setVisibility(View.GONE);
                    submit_otp_btn.setVisibility(View.VISIBLE);
                    title_tv.setText("Enter OTP");
                    content_tv.setText("Please type verification code");
                } else {
                    UserAccount.EditTextPointer.setError("Mobile Number Invalid !");
                    UserAccount.EditTextPointer.requestFocus();
                }


            }
        });
        submit_otp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UserAccount.isEmpty(editTextone, editTexttwo, editTextthree, editTextfour, editTextfifth, editTextsixth)) {
                    verifyVerificationCode(editTextone.getText().toString() + editTexttwo.getText().toString() + editTextthree.getText().toString() + editTextfour.getText().toString() + editTextfifth.getText().toString() + editTextsixth.getText().toString());
                } else {
                    UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
                    UserAccount.EditTextPointer.requestFocus();
                }
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + mobile, 60, TimeUnit.SECONDS, UpdateProfileActivity.this, mCallbacks);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                String[] part = code.split("");
                editTextone.setText(part[0]);
                editTexttwo.setText(part[1]);
                editTextthree.setText(part[2]);
                editTextfour.setText(part[3]);
                editTextfifth.setText(part[4]);
                editTextsixth.setText(part[5]);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(UpdateProfileActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("FirebaseException", "" + e.getMessage());

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };


    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(UpdateProfileActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //verification successful we will start the profile activity
                    phoneEt.setText(mobile_number_et.getText().toString());
                    dialog.dismiss();
                } else {
                    //verification unsuccessful.. display an error message
                    String message = "Somthing is wrong, we will fix it soon...";
                    ErrorMessage.E("Else" + task.getException());
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        message = "Invalid code entered...";
                    }
                    Toast.makeText(UpdateProfileActivity.this, message, Toast.LENGTH_LONG).show();
                    Log.e("task", "" + message);
                           /* Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG);
                            snackbar.setAction("Dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                            snackbar.show();*/
                }
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        {
            if (editable.length() == 1) {
                if (editTextone.length() == 1) {
                    editTexttwo.requestFocus();
                }
                if (editTexttwo.length() == 1) {
                    editTextthree.requestFocus();
                }
                if (editTextthree.length() == 1) {
                    editTextfour.requestFocus();
                }
                if (editTextfour.length() == 1) {
                    editTextfifth.requestFocus();
                }
                if (editTextfifth.length() == 1) {
                    editTextsixth.requestFocus();
                }
            } else if (editable.length() == 0) {
                if (editTextsixth.length() == 0) {
                    editTextfifth.requestFocus();
                }
                if (editTextfifth.length() == 0) {
                    editTextfour.requestFocus();
                }
                if (editTextfour.length() == 0) {
                    editTextthree.requestFocus();
                }
                if (editTextthree.length() == 0) {
                    editTexttwo.requestFocus();
                }
                if (editTexttwo.length() == 0) {
                    editTextone.requestFocus();
                }
            }
        }
    }

    private void updateProfileApi() {
        ErrorMessage.E("selectImagePath in Update Profile" + selectedImagePath);
        if (!selectedImagePath.equals("")) {
            if (NetworkUtil.isNetworkAvailable(UpdateProfileActivity.this)) {
                final Dialog materialDialog = ErrorMessage.initProgressDialog(UpdateProfileActivity.this);
                File file = new File(selectedImagePath);
                final RequestBody requestfile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("profile_image", file.getName(), requestfile);
                Log.d("rrrr", body.toString());
                RequestBody fullname = RequestBody.create(MediaType.parse("text/plain"), firstNameEt.getText().toString() + " " + lastNameEt.getText().toString());
                RequestBody lastname = RequestBody.create(MediaType.parse("text/plain"), lastNameEt.getText().toString());
                RequestBody auth_token = RequestBody.create(MediaType.parse("text/plain"), UserProfileHelper.getInstance().getUserProfileModel().get(0).getUid());
                RequestBody email = RequestBody.create(MediaType.parse("text/plain"), emailEt.getText().toString());
                RequestBody mobile = RequestBody.create(MediaType.parse("text/plain"), phoneEt.getText().toString());

                LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
                Call<ResponseBody> call = apiService.UpdateProfile(auth_token, fullname, mobile, email, body);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                ErrorMessage.E("==========profile update  :" + jsonObject.toString());
                                if (jsonObject.getInt("status") == 200) {
                                    materialDialog.dismiss();
                                    JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                                    UserProfileModel userProfileModel = new UserProfileModel();
                                    userProfileModel.setUser_id(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
                                    userProfileModel.setEmaiiId(jsonObject1.getString("email"));
                                    userProfileModel.setUserPhone(jsonObject1.getString("contact"));
                                    userProfileModel.setDisplayName(jsonObject1.getString("first_name"));
                                    userProfileModel.setProfile_pic(jsonObject1.getString("profile_img"));

                                    UserProfileHelper.getInstance().delete();
                                    UserProfileHelper.getInstance().insertUserProfileModel(userProfileModel);
                                    ErrorMessage.T(UpdateProfileActivity.this, jsonObject.getString("message"));
                                    ErrorMessage.I_clear(UpdateProfileActivity.this, UserDashboardActivity.class, null);
                                } else {
                                    ErrorMessage.T(UpdateProfileActivity.this, jsonObject.getString("message"));
                                    materialDialog.dismiss();
                                    System.out.println("==========response not geting 400 :");

                                }
                            } catch (JSONException e) {
                                materialDialog.dismiss();
                                System.out.println("==========response not  :" + e.toString());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                            materialDialog.dismiss();
                            System.out.println("==========response not success :");

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                        materialDialog.dismiss();
                        System.out.println("==========response faiil :");
                    }
                });


            } else {
                ErrorMessage.T(UpdateProfileActivity.this, this.getString(R.string.no_internet));
            }
        } else if (selectedImagePath.equals("")) {
            if (NetworkUtil.isNetworkAvailable(UpdateProfileActivity.this)) {
                final Dialog materialDialog = ErrorMessage.initProgressDialog(UpdateProfileActivity.this);
                LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
                Call<ResponseBody> call = apiService.UpdateProfile_WithoutImage(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id(), firstNameEt.getText().toString().trim(), phoneEt.getText().toString(), emailEt.getText().toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        ErrorMessage.E("Response" + response.code());
                        if (response.isSuccessful()) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                ErrorMessage.E("==========profile update  :" + jsonObject.toString());
                                if (jsonObject.getInt("status") == 200) {
                                    materialDialog.dismiss();
                                    JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                                    UserProfileModel userProfileModel = new UserProfileModel();
                                    userProfileModel.setUser_id(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
                                    userProfileModel.setEmaiiId(jsonObject1.getString("email"));
                                    userProfileModel.setUserPhone(jsonObject1.getString("contact"));
                                    userProfileModel.setDisplayName(jsonObject1.getString("first_name"));
                                    userProfileModel.setProfile_pic(jsonObject1.getString("profile_img"));
                                    UserProfileHelper.getInstance().delete();
                                    UserProfileHelper.getInstance().insertUserProfileModel(userProfileModel);
                                    ErrorMessage.T(UpdateProfileActivity.this, jsonObject.getString("message"));
                                    ErrorMessage.I_clear(UpdateProfileActivity.this, UserDashboardActivity.class, null);
                                } else {
                                    ErrorMessage.T(UpdateProfileActivity.this, jsonObject.getString("message"));
                                    materialDialog.dismiss();
                                    System.out.println("==========response not geting 400 :");

                                }
                            } catch (JSONException e) {
                                materialDialog.dismiss();
                                System.out.println("==========response not  :" + e.toString());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                            materialDialog.dismiss();
                            System.out.println("==========response not success :");


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
                ErrorMessage.T(UpdateProfileActivity.this, "No Internet");
            }
        }
    }
}
