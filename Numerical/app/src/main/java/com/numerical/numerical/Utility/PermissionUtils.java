package com.numerical.numerical.Utility;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;


import com.numerical.numerical.R;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class PermissionUtils {
    private static final int PERMISSION_REQUEST_CODE =3453 ;
    private String TAG = PermissionUtils.class.getSimpleName();
    private Activity mActivity;
    private PermissionResultCallback permissionResultCallback;
    
    
    public PermissionUtils(Activity activity, PermissionResultCallback callback) {
        mActivity = activity;
        permissionResultCallback = callback;
    }
    
    
    public void check_permission(List<String> permision) {
        if (Build.VERSION.SDK_INT >= 23) {
            checkPermissions(permision);
        } else {
            permissionResultCallback.PermissionGranted();
            Log.e(TAG, "All Permissions- granted");
        }
    }
    
    
    /**
     * @param permissions  permissions
     * @param grantResults results
     */
    public void onRequestPermissionsResult(String permissions[], int[] grantResults) {
        List<String> permissionsList = Arrays.asList(permissions);
        List<String> permissionsNeeded = new Vector<>();
        for (int i = 0; i < permissionsList.size(); i++) {
            if (ContextCompat.checkSelfPermission(mActivity,
                    permissionsList.get(i)) != PackageManager.PERMISSION_GRANTED)
                permissionsNeeded.add(permissionsList.get(i));
        }
        if (permissionsNeeded.isEmpty()) {
            permissionResultCallback.PermissionGranted();
        } else
            Toast.makeText(mActivity, mActivity.getString(R.string.enable_permissions_manually), Toast.LENGTH_LONG).show();
    }
    
    public interface PermissionResultCallback {
        void PermissionGranted();
    }
    
    
    private void checkPermissions(List<String> permissions) {
        List<String> permissionsNeeded = new Vector<>();
        for (int i = 0; i < permissions.size(); i++) {
            if (ContextCompat.checkSelfPermission(mActivity,
                    permissions.get(i)) != PackageManager.PERMISSION_GRANTED)
                permissionsNeeded.add(permissions.get(i));
        }
        if (!permissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(mActivity, permissionsNeeded.toArray(new String[permissionsNeeded.size()]), PERMISSION_REQUEST_CODE);
        } else {
            permissionResultCallback.PermissionGranted();
            Log.e(TAG, "All Permissions- granted");
        }
    }
}