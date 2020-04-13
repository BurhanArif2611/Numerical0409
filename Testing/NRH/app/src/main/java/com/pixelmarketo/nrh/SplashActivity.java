package com.pixelmarketo.nrh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.widget.Toast;

import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.pixelmarketo.nrh.database.UserProfileHelper;
import com.pixelmarketo.nrh.utility.ErrorMessage;
import com.pixelmarketo.nrh.utility.SavedData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE;

public class SplashActivity extends AppCompatActivity {
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    final int MY_REQUEST_CODE = 1000;
    AppUpdateManager appUpdateManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appUpdateManager = AppUpdateManagerFactory.create(this);

// Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

// Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        // For a flexible update, use AppUpdateType.FLEXIBLE
                        && appUpdateInfo.isUpdateTypeAllowed(IMMEDIATE)) {
                    // Request the update.
                    try {
                        appUpdateManager.startUpdateFlowForResult(
                                // Pass the intent that is returned by 'getAppUpdateInfo()'.
                                appUpdateInfo,
                                // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                                IMMEDIATE,
                                // The current activity making the update request.
                                SplashActivity.this,
                                // Include a request code to later monitor this update request.
                                MY_REQUEST_CODE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (Build.VERSION.SDK_INT >= 23) {
                                permissioncheck();
                            } else {
                                LaunchApp();

                            }
                        }
                    }, 4000);
                }
            }
        });

    }

    private void permissioncheck() {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE))
            permissionsNeeded.add("READ");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("WRITE");
        if (!addPermission(permissionsList, Manifest.permission.CAMERA))
            permissionsNeeded.add("CAMERA");
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
            permissionsNeeded.add("FINE LOCATION");
        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (Build.VERSION.SDK_INT >= 23) {
                            // Marshmallow+
                            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                        } else {
                            // Pre-Marshmallow
                        }

                    }
                });
                return;
            }

            if (Build.VERSION.SDK_INT >= 23) {
                // Marshmallow+
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);


            } else {
                // Pre-Marshmallow

            }

            return;
        } else {
            // Toast.makeText(this,"Permission",Toast.LENGTH_LONG).show();
            LaunchApp();
        }

        //insertDummyContact();
    }


    private boolean addPermission(List<String> permissionsList, String permission) {
        Boolean cond;
        if (Build.VERSION.SDK_INT >= 23) {
            // Marshmallow+
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
                // Check for Rationale Option
                if (!shouldShowRequestPermissionRationale(permission))
                    //  return false;
                    cond = false;
            }
            //  return true;
            cond = true;
        } else {
            // Pre-Marshmallow
            cond = true;
        }
        return cond;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(SplashActivity.this).setMessage(message).setPositiveButton("OK", okListener).setNegativeButton("Cancel", null).create().show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Checking the request code of our request
        if (requestCode == 23) {
            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Permission Needed To Run The App", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS) {
            Map<String, Integer> perms = new HashMap<String, Integer>();
            // Initial
            perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
//            perms.put(Manifest.permission.CALL_PHONE, PackageManager.PERMISSION_GRANTED);
      /*      perms.put(Manifest.permission.SEND_SMS, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.RECEIVE_SMS, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.READ_SMS, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);*/
            //Toast.makeText(SplashActivityScreen.this, " Permissions are jddddd", Toast.LENGTH_SHORT).show();
            // Fill with results
            for (int i = 0; i < permissions.length; i++)
                perms.put(permissions[i], grantResults[i]);
            // Check for ACCESS_FINE_LOCATION
            if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
//                    perms.get(Manifest.permission.CALL_PHONE)==PackageManager.PERMISSION_GRANTED
                  /*  && perms.get(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED
                    && perms.get(Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED
                    && perms.get(Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED
                    && perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED*/) {
                // All Permissions Granted
                // insertDummyContact();

                //Toast.makeText(SplashActivityScreen.this, " Permissions are l", Toast.LENGTH_SHORT).show();
                LaunchApp();

            } else {
                // Permission Denied
                Toast.makeText(SplashActivity.this, "Some Permission is Denied", Toast.LENGTH_SHORT).show();

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                        finish();
                    }
                }, 3000);
            }

        }
    }

    public void LaunchApp() {

        if (UserProfileHelper.getInstance().getUserProfileModel().size() > 0) {
            if (UserProfileHelper.getInstance().getUserProfileModel().get(0).getUserType().equals("User")) {
                ErrorMessage.I_clear(SplashActivity.this, UserDashboardActivity.class, null);
            } else if (UserProfileHelper.getInstance().getUserProfileModel().get(0).getUserType().equals("Vender")) {
                if (SavedData.getPayment_Status().equals("1")) {
                    ErrorMessage.I_clear(SplashActivity.this, VenderDashboardActivity.class, null);
                } else if (SavedData.getPayment_Status().equals("0")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Check", "Splash");
                    ErrorMessage.I_clear(SplashActivity.this, Vender_RegistrationActivity.class, bundle);
                }
            }
        } else {
            ErrorMessage.I_clear(SplashActivity.this, ServiceTypeActivity.class, null);
        }


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LaunchApp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {

                if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    // If an in-app update is already running, resume the update.
                    try {
                        appUpdateManager.startUpdateFlowForResult(appUpdateInfo, IMMEDIATE, SplashActivity.this, MY_REQUEST_CODE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}