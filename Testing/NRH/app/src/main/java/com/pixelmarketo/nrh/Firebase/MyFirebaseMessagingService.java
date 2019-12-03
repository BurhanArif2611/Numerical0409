package com.pixelmarketo.nrh.Firebase;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.pixelmarketo.nrh.R;
import com.pixelmarketo.nrh.VenderDashboardActivity;
import com.pixelmarketo.nrh.activity.BidRequestActivity;
import com.pixelmarketo.nrh.activity.user.HistoryServiceActivity;
import com.pixelmarketo.nrh.activity.user.VenderlistActivity;
import com.pixelmarketo.nrh.database.UserProfileHelper;
import com.pixelmarketo.nrh.utility.ErrorMessage;
import com.pixelmarketo.nrh.utility.NetworkUtil;
import com.pixelmarketo.nrh.utility.SavedData;


import org.json.JSONObject;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    private NotificationUtils notificationUtils;
    String title = "";
    JSONObject jsonObject3;

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        SavedData.saveFCM_ID(s);
        Log.e("", "Refreshed token:" + s);
        if (UserProfileHelper.getInstance().getUserProfileModel().size() > 0) {
            UpdateFCMOnServer(s);
        }
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try {
            Log.e(TAG, "From data: " + remoteMessage.getData().toString());
            String Response = remoteMessage.getData().toString();
            JSONObject jsonObject = new JSONObject(Response);
            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
            JSONObject jsonObject2 = jsonObject1.getJSONObject("message");
            title = jsonObject1.getString("user_type");
            ErrorMessage.E("MyFirebaseMessagingService" + title);
            if (title.equals("New Service Request")) {
                JSONObject jsonObject3 = jsonObject2.getJSONObject("msg");
                New_Service_Request(title, jsonObject3.getString("service_name"),jsonObject3.toString());
            } else if (title.equals("New Bid Request")) {
                JSONObject jsonObject3 = jsonObject2.getJSONObject("msg");
                New_Bid_Request(title, jsonObject3.getString("service"),jsonObject3.toString());
            }else if (title.equals("Approve Bid Request")) {
                JSONObject jsonObject3 = jsonObject2.getJSONObject("msg");
                Approve_Bid_Request(title, jsonObject3.getString("service"),jsonObject3.toString());
            }/* else if (title.equals("New Sales Order Request")) {
                New_Order_Request(title, jsonObject2.getString("body"));
            }else if (title.equals("Sales Order Approve")) {
                Sales_Order_Approve(title, jsonObject2.getString("body"));
            }*/

        } catch (Exception e) {
            e.printStackTrace();
            ErrorMessage.E("MyFirebaseMessagingService Exception " + e.toString());
        }


    }
    private void New_Service_Request(String title, String Content,String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.NewRequest);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                final String ANDROID_CHANNEL_ID = "com.pixelmarketo.nrh.ANDROID";
                Intent intent = new Intent(getApplicationContext(), VenderDashboardActivity.class);
                intent.putExtra("check", "NewRequest");
                intent.putExtra("message", message);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NetworkUtileforOreao mNotificationUtils = new NetworkUtileforOreao(getApplicationContext());
                Notification.Builder nb = new Notification.Builder(getApplicationContext(), ANDROID_CHANNEL_ID).
                        setSmallIcon(R.drawable.ic_launcher).setContentTitle(title).setContentText(Content).setAutoCancel(true).setSound(defaultSoundUri).setContentIntent(pendingIntent);
                mNotificationUtils.getManager().notify(0, nb.build());
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
            } else {
                Intent intent = new Intent(getApplicationContext(), VenderDashboardActivity.class);
                intent.putExtra("check", "NewRequest");
                intent.putExtra("message", message);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this).
                        setSmallIcon(R.drawable.ic_launcher).setContentTitle(title).setContentText(Content).setAutoCancel(true).setSound(defaultSoundUri).setContentIntent(pendingIntent);
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0, notificationBuilder.build());
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
            }

        }


    }
    private void New_Bid_Request(String title, String Content,String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.New_Bid_Request);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                final String ANDROID_CHANNEL_ID = "com.pixelmarketo.nrh.ANDROID";
                Intent intent = new Intent(getApplicationContext(), HistoryServiceActivity.class);
                intent.putExtra("Check", "New_Bid_Request");
                intent.putExtra("message", message);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NetworkUtileforOreao mNotificationUtils = new NetworkUtileforOreao(getApplicationContext());
                Notification.Builder nb = new Notification.Builder(getApplicationContext(), ANDROID_CHANNEL_ID).
                        setSmallIcon(R.drawable.ic_launcher).setContentTitle(title).setContentText(Content).setAutoCancel(true).setSound(defaultSoundUri).setContentIntent(pendingIntent);
                mNotificationUtils.getManager().notify(0, nb.build());
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
            } else {
                Intent intent = new Intent(getApplicationContext(), HistoryServiceActivity.class);
                intent.putExtra("Check", "New_Bid_Request");
                intent.putExtra("message", message);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this).
                        setSmallIcon(R.drawable.ic_launcher).setContentTitle(title).setContentText(Content).setAutoCancel(true).setSound(defaultSoundUri).setContentIntent(pendingIntent);
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0, notificationBuilder.build());
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
            }

        }


    }private void Approve_Bid_Request(String title, String Content,String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.New_Bid_Request);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                final String ANDROID_CHANNEL_ID = "com.pixelmarketo.nrh.ANDROID";
                Intent intent = new Intent(getApplicationContext(), BidRequestActivity.class);
                intent.putExtra("Check", "Approve_Bid_Request");
                intent.putExtra("message", message);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NetworkUtileforOreao mNotificationUtils = new NetworkUtileforOreao(getApplicationContext());
                Notification.Builder nb = new Notification.Builder(getApplicationContext(), ANDROID_CHANNEL_ID).
                        setSmallIcon(R.drawable.ic_launcher).setContentTitle(title).setContentText(Content).setAutoCancel(true).setSound(defaultSoundUri).setContentIntent(pendingIntent);
                mNotificationUtils.getManager().notify(0, nb.build());
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
            } else {
                Intent intent = new Intent(getApplicationContext(), BidRequestActivity.class);
                intent.putExtra("Check", "Approve_Bid_Request");
                intent.putExtra("message", message);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this).
                        setSmallIcon(R.drawable.ic_launcher).setContentTitle(title).setContentText(Content).setAutoCancel(true).setSound(defaultSoundUri).setContentIntent(pendingIntent);
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0, notificationBuilder.build());
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
            }

        }


    }

    /*private void Assign_New_Request(String title, String Content) {
        // app is in background, show the notification in notification tray
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            final String ANDROID_CHANNEL_ID = "com.pixelmarketo.lakshyaoffical.ANDROID";
            Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
           intent.putExtra("message", Content);
            intent.putExtra("Check", "");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NetworkUtileforOreao mNotificationUtils = new NetworkUtileforOreao(getApplicationContext());
            Notification.Builder nb = new Notification.Builder(getApplicationContext(), ANDROID_CHANNEL_ID).
                    setSmallIcon(R.mipmap.ic_launcher).setContentTitle(title).setContentText(Content).setAutoCancel(true).setSound(defaultSoundUri).setContentIntent(pendingIntent);
            *//*Noti ficationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)*//*
            ;
            mNotificationUtils.getManager().notify(0, nb.build());
        } else {
            Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
            intent.putExtra("message", Content);
            intent.putExtra("Check", "");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this).
                    setSmallIcon(R.mipmap.ic_launcher).setContentTitle(title).setContentText(Content).setAutoCancel(true).setSound(defaultSoundUri).setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notificationBuilder.build());
        }
    }*/

   /* private void New_Request(String title, String Content) {
        // app is in background, show the notification in notification tray
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            final String ANDROID_CHANNEL_ID = "com.pixelmarketo.decgroupapp.ANDROID";
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("message", Content);
            intent.putExtra("Check", "");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NetworkUtileforOreao mNotificationUtils = new NetworkUtileforOreao(getApplicationContext());
            Notification.Builder nb = new Notification.Builder(getApplicationContext(), ANDROID_CHANNEL_ID).
                    setSmallIcon(R.mipmap.ic_launcher).setContentTitle(title).setContentText(Content).setAutoCancel(true).setSound(defaultSoundUri).setContentIntent(pendingIntent);
            *//*Noti ficationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)*//*
            ;
            mNotificationUtils.getManager().notify(0, nb.build());
        } else {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("message", Content);
            intent.putExtra("Check", "");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this).
                    setSmallIcon(R.mipmap.ic_launcher).setContentTitle(title).setContentText(Content).setAutoCancel(true).setSound(defaultSoundUri).setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notificationBuilder.build());
        }


    }

    private void New_Order_Request(String title, String Content) {
        // app is in background, show the notification in notification tray
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            final String ANDROID_CHANNEL_ID = "com.pixelmarketo.decgroupapp.ANDROID";
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("message", Content);
            intent.putExtra("Check", "");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NetworkUtileforOreao mNotificationUtils = new NetworkUtileforOreao(getApplicationContext());
            Notification.Builder nb = new Notification.Builder(getApplicationContext(), ANDROID_CHANNEL_ID).
                    setSmallIcon(R.mipmap.ic_launcher).setContentTitle(title).setContentText(Content).setAutoCancel(true).setSound(defaultSoundUri).setContentIntent(pendingIntent);
            *//*Noti ficationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)*//*
            ;
            mNotificationUtils.getManager().notify(0, nb.build());
        } else {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("message", Content);
            intent.putExtra("Check", "");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this).
                    setSmallIcon(R.mipmap.ic_launcher).setContentTitle(title).setContentText(Content).setAutoCancel(true).setSound(defaultSoundUri).setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notificationBuilder.build());
        }


    }

    private void Sales_Order_Approve(String title, String Content) {
        // app is in background, show the notification in notification tray
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            final String ANDROID_CHANNEL_ID = "com.pixelmarketo.decgroupapp.ANDROID";
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("message", Content);
            intent.putExtra("Check", "");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NetworkUtileforOreao mNotificationUtils = new NetworkUtileforOreao(getApplicationContext());
            Notification.Builder nb = new Notification.Builder(getApplicationContext(), ANDROID_CHANNEL_ID).
                    setSmallIcon(R.mipmap.ic_launcher).setContentTitle(title).setContentText(Content).setAutoCancel(true).setSound(defaultSoundUri).setContentIntent(pendingIntent);
            *//*Noti ficationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)*//*
            ;
            mNotificationUtils.getManager().notify(0, nb.build());
        } else {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("message", Content);
            intent.putExtra("Check", "");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this).
                    setSmallIcon(R.mipmap.ic_launcher).setContentTitle(title).setContentText(Content).setAutoCancel(true).setSound(defaultSoundUri).setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notificationBuilder.build());
        }


    }*/


    private void UpdateFCMOnServer(String Tokan) {
        if (NetworkUtil.isNetworkAvailable(getApplicationContext())) {
             /*LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            ErrorMessage.E("FCM_id" + SavedData.getFCM_ID());
           Call<ResponseBody> call = apiService.updatefcmtoken(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id(), Tokan, UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_type());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                    } else {
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                }
            });*/
        } else {

        }
    }
}

