package com.pixelmarketo.nrh.fragment;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.pixelmarketo.nrh.R;
import com.pixelmarketo.nrh.ServiceForms.TentActivity;
import com.pixelmarketo.nrh.adapter.UploadImage_Adapter;
import com.pixelmarketo.nrh.database.UserProfileHelper;
import com.pixelmarketo.nrh.models.SelectImage;
import com.pixelmarketo.nrh.models.Service_Models.Result;
import com.pixelmarketo.nrh.utility.AppConfig;
import com.pixelmarketo.nrh.utility.ErrorMessage;
import com.pixelmarketo.nrh.utility.FilePath;
import com.pixelmarketo.nrh.utility.NetworkUtil;
import com.pixelmarketo.nrh.utility.UserAccount;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;


public class Service_FoodFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    View view;
    @BindView(R.id.event_name_etv)
    EditText eventNameEtv;
    @BindView(R.id.start_date_etv)
    EditText startDateEtv;
    @BindView(R.id.no_of_foods_etv)
    EditText noOfFoodsEtv;
    @BindView(R.id.no_of_guest_etv)
    EditText noOfGuestEtv;
    @BindView(R.id.city_village_etv)
    EditText cityVillageEtv;
    @BindView(R.id.tehsil_etv)
    EditText tehsilEtv;
    @BindView(R.id.image_Rcv)
    RecyclerView imageRcv;
    @BindView(R.id.select_image_layout)
    LinearLayout selectImageLayout;
    @BindView(R.id.submit_btn)
    Button submitBtn;
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
    Unbinder unbinder;
    private Result result;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_service__food, container, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            result = (Result) bundle.getSerializable("result");
        }
        return view;
    }


    @OnClick({R.id.start_date_etv, R.id.select_image_layout, R.id.submit_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_date_etv:
                datepicker();
                break;
            case R.id.select_image_layout:
                showFileChooser();
                break;
            case R.id.submit_btn:
                if (UserAccount.isEmpty(eventNameEtv, startDateEtv, noOfFoodsEtv, noOfGuestEtv, cityVillageEtv, tehsilEtv)) {
                    submitReportApi();
                } else {
                    UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
                    UserAccount.EditTextPointer.requestFocus();
                }
                break;
        }
    }

    private void showFileChooser() {
        /*From Camera*/
        final CharSequence[] options = {"From Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                    Intent intent = new Intent();
                    //sets the select file to all types of files
                    intent.setType("*/*");
                    //allows to select data and return it
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    //starts new activity to select file and return data
                    startActivityForResult(Intent.createChooser(intent, "Choose File to Upload.."), PICK_FILE_REQUEST);
                   /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkGalleryPermission()) galleryIntent();
                        else requestGalleryPermission();
                    } else galleryIntent();*/
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.create().show();


    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent, CAMERA_PIC_REQUEST);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{CAMERA}, PERMISSION_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        int result1 = ContextCompat.checkSelfPermission(getActivity(), CAMERA);
        return result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestGalleryPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{READ_EXTERNAL_STORAGE}, REQUEST);
    }

    private void galleryIntent() {
        Intent intent = new Intent().setType("image/*").setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_FROM_GALLERY);
    }

    private boolean checkGalleryPermission() {
        int result2 = ContextCompat.checkSelfPermission(getActivity(), READ_EXTERNAL_STORAGE);
        return result2 == PackageManager.PERMISSION_GRANTED;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_PIC_REQUEST && resultCode == Activity.RESULT_OK && null != data) {
            Uri cameraURI = data.getData();
            Bitmap productImageBitmap = (Bitmap) data.getExtras().get("data");
            //selectImage.setImageBitmap(productImageBitmap);
            Uri tempUri = getImageUri(getActivity(), productImageBitmap);
            // CALL THIS METHOD TO GET THE ACTUAL PATH

            selectedFilePath = getRealPathFromURI(tempUri);
            //filePathTxtvw.setText(selectedFilePath);
            SelectImage selectImage = new SelectImage();
            selectImage.setImage(productImageBitmap);
            selectImage.setImage_path(selectedFilePath);
            stringArrayList.add(selectImage);
            UploadImage_Adapter assignedTaskAdapter = new UploadImage_Adapter(getActivity(), stringArrayList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.HORIZONTAL, false);
            imageRcv.setLayoutManager(mLayoutManager);
            imageRcv.setItemAnimator(new DefaultItemAnimator());
            imageRcv.setAdapter(assignedTaskAdapter);
            ErrorMessage.E("selectedImagePath Camera" + selectedFilePath);

        } else if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_FILE_REQUEST) {
                try {
                    if (data == null) {
                        //no data present
                        return;
                    }
                    Uri selectedFileUri = data.getData();
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedFileUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("", "Selected File IOException" + e.toString());
                    }
                    selectedFilePath = FilePath.getPath(getActivity(), selectedFileUri);
                    Log.i("", "Selected File Path:" + selectedFilePath);

                    if (selectedFilePath != null && !selectedFilePath.equals("")) {
                        //filePathTxtvw.setText(selectedFilePath);
                    } else {
                        Toast.makeText(getActivity(), "Cannot upload file to server", Toast.LENGTH_SHORT).show();
                    }
                    SelectImage selectImage = new SelectImage();
                    selectImage.setImage(bitmap);
                    selectImage.setImage_path(selectedFilePath);
                    stringArrayList.add(selectImage);
                    UploadImage_Adapter assignedTaskAdapter = new UploadImage_Adapter(getActivity(), stringArrayList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.HORIZONTAL, false);
                    imageRcv.setLayoutManager(mLayoutManager);
                    imageRcv.setItemAnimator(new DefaultItemAnimator());
                    imageRcv.setAdapter(assignedTaskAdapter);
                } catch (Exception e) {
                }
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getActivity().getContentResolver() != null) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
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
        datePickerDialog.show(getActivity().getFragmentManager(), "Date Picker");
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    private void submitReportApi() {
        MultipartBody.Part[] surveyImagesParts = new MultipartBody.Part[stringArrayList.size()];
        ErrorMessage.E("Tokan" + UserProfileHelper.getInstance().getUserProfileModel().get(0).getUid());
        for (int index = 0; index < stringArrayList.size(); index++) {
            File file = new File(stringArrayList.get(index).getImage_path());
            RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), file);
            surveyImagesParts[index] = MultipartBody.Part.createFormData("images[]", file.getName(), surveyBody);
        }
        if (NetworkUtil.isNetworkAvailable(getActivity())) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(getActivity());
            Call<ResponseBody> call = AppConfig.getLoadInterface().service_request(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUid(), result.getServiceId(), "", eventNameEtv.getText().toString(), startDateEtv.getText().toString(), "", cityVillageEtv.getText().toString(), tehsilEtv.getText().toString(), noOfGuestEtv.getText().toString(), noOfFoodsEtv.getText().toString(), surveyImagesParts);
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
                                getActivity().finish();
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
}
