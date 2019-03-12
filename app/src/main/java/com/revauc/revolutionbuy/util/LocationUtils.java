package com.revauc.revolutionbuy.util;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.revauc.revolutionbuy.ui.BaseActivity;

/**
 *
 */
public class LocationUtils extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, ResultCallback<LocationSettingsResult> {

    public static final String TAG = LocationUtils.class.getSimpleName();
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    public static final int REQUEST_CHECK_SETTINGS = 0x1;
    public static final int REQUEST_LOCATION_PERMISSION = 1000;
    public static final int REQUEST_SETTINGS_LOCATION_PERMISSION = 425;
    private static final float MINIMUM_DISPLACEMENT = 100;
    protected GoogleApiClient googleApiClient;
    protected LocationRequest locationRequest;
    protected LocationSettingsRequest locationSettingsRequest;
    protected Location currentLocation;
    protected Boolean requestingLocationUpdates;
//    private LocationUpdateListener mLocationUpdateListener;

    public static void addFragment(AppCompatActivity activity) {
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(LocationUtils.newInstance(), LocationUtils.TAG);
        fragmentTransaction.commit();
    }

    public static void addFragment(AppCompatActivity activity, LocationUtils locationUtils) {
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(locationUtils, LocationUtils.TAG);
        fragmentTransaction.commit();
    }

    public void removeFragment(AppCompatActivity activity, LocationUtils locationUtils) {
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.remove(locationUtils);
        fragmentTransaction.commit();
    }

    public static LocationUtils newInstance() {
        return new LocationUtils();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestingLocationUpdates = false;
        buildGoogleApiClient();
        createLocationRequest();
        buildLocationSettingsRequest();
        startUpdates();
    }

    protected synchronized void buildGoogleApiClient() {
        LogUtils.LOGI(TAG, "Building GoogleApiClient");
        googleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    protected void createLocationRequest() {
        locationRequest = new LocationRequest();
        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
        locationRequest.setSmallestDisplacement(MINIMUM_DISPLACEMENT);
    }

    protected void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        locationSettingsRequest = builder.build();
    }

    protected void checkLocationSettings() {
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        googleApiClient,
                        locationSettingsRequest
                );
        result.setResultCallback(this);
    }

    @Override
    public void onResult(LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                LogUtils.LOGI(TAG, "All location settings are satisfied.");
                startLocationUpdates();
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                LogUtils.LOGI(TAG, "Location settings are not satisfied. Show the user a dialog to" +
                        "upgrade location settings ");
                try {
                    // Show the dialog by calling startResolutionForResult(), and check the result
                    // in onActivityResult().
                    status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException e) {
                    LogUtils.LOGI(TAG, "PendingIntent unable to execute request.");
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                LogUtils.LOGI(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog " +
                        "not created.");
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        LogUtils.LOGI(TAG, "User agreed to make required location settings changes.");
                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        LogUtils.LOGI(TAG, "User chose not to make required location settings changes.");
                        break;
                }
                break;
            case REQUEST_SETTINGS_LOCATION_PERMISSION:
//                switch (resultCode) {
//                    case Activity.RESULT_OK:
//                        LogUtils.LOGI(TAG, "User agreed to make required location settings changes.");
                        startLocationUpdates();
//                        break;
//                    case Activity.RESULT_CANCELED:
//                        LogUtils.LOGI(TAG, "User chose not to make required location settings changes.");
//                        break;
//                }
                break;
        }
    }

    public void startUpdates() {
        checkLocationSettings();
    }


    /**
     * Requests location updates from the FusedLocationApi.
     */
    protected void startLocationUpdates() {
//        ((BaseActivity) getActivity()).showProgressBar();
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if(googleApiClient.isConnected()) {
                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest,
                        this).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        requestingLocationUpdates = true;
                    }
                });
            }
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION && !StringUtils.isNullOrEmpty(grantResults)
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            LogUtils.LOGV(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
            startLocationUpdates();
        } else {
            if (permissions.length > 0) {
                boolean showRational = shouldShowRequestPermissionRationale(permissions[0]);
                if (!showRational) {
                    ((BaseActivity) getActivity()).showDualActionSnackBar("Find your Location",
                            "Turn on locations for the P2F App in your device settings.",
                            "SETTINGS", "CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //ON POSITIVE CLICK
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                                    intent.setData(uri);
                                    getActivity().startActivityForResult(intent, REQUEST_SETTINGS_LOCATION_PERMISSION);
                                }
                            }, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                } else if (Manifest.permission.ACCESS_FINE_LOCATION.equals(permissions[0])) {
                    ((BaseActivity) getActivity()).showDualActionSnackBar("Find your Location",
                            "The P2F App can locate your device to check you are in a legal territory for betting.",
                            "OK", "NOT NOW", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //ON POSITIVE CLICK
                                    startLocationUpdates();
                                }
                            }, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                    // user did NOT check "never ask again"
                    // this is a good place to explain the user
                    // why you need the permission and ask if he wants
                    // to accept it (the rationale)
                }
            }
        }
    }

    /**
     * Removes location updates from the FusedLocationApi.
     */
    protected void stopLocationUpdates() {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.
        LocationServices.FusedLocationApi.removeLocationUpdates(
                googleApiClient,
                this
        ).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                requestingLocationUpdates = false;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Within {@code onPause()}, we pause location updates, but leave the
        // connection to GoogleApiClient intact.  Here, we resume receiving
        // location updates if the user has requested them.
        if (googleApiClient.isConnected() && requestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // Stop location updates to save battery, but don't disconnect the GoogleApiClient object.
        if (googleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        LogUtils.LOGI(TAG, "Connected to GoogleApiClient");
    }

    /**
     * Callback that fires when the location changes.
     */
    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
        if (location != null) {
//            mLocationUpdateListener.onLocationUpdate(location);
        }
//        ((BaseActivity) getActivity()).hideProgressBar();
//        LogUtils.LOGD("Location", "Lat : " + location.getLatitude() + ", " + "Long : " + location.getLongitude());
    }

    @Override
    public void onConnectionSuspended(int cause) {
        LogUtils.LOGI(TAG, "Connection suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        LogUtils.LOGI(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

//    public void addLocationUpdateListener(LocationUpdateListener locationUpdateListener) {
//        mLocationUpdateListener = locationUpdateListener;
//    }
}
