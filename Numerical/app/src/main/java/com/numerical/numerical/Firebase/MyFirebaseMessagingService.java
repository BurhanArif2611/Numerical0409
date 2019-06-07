package com.numerical.numerical.Firebase;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.numerical.numerical.Utility.ErrorMessage;

import org.json.JSONObject;



public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private NotificationUtils notificationUtils;
    String title = "";
    JSONObject jsonObject3;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try {
            Log.e(TAG, "From data: " + remoteMessage.getData());
            ErrorMessage.T(MyFirebaseMessagingService.this, String.valueOf(remoteMessage.getData()));
            /*String Response = remoteMessage.getData().toString();
            JSONObject jsonObject = new JSONObject(Response);
            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
            JSONObject jsonObject2 = jsonObject1.getJSONObject("message");
            jsonObject3 = jsonObject2.getJSONObject("msg");
            title = jsonObject3.getString("msg");*/
          /*  if (title.equals("logout")) {
                handleNotificationlogout(title, jsonObject3.getString("notification"), jsonObject3.toString());
            } else {
                handleNotification(title, jsonObject3.getString("notification"), jsonObject3.toString());
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


   /* private void handleNotification(String title, String Content, String message) {

        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = null;
            if (title.equals("Confirm Booking")) {
                pushNotification = new Intent(Config.Complete_Booking);
            } else if (title.equals("driver_AcceptOrder")) {
                pushNotification = new Intent(Config.driver_AcceptOrder);
            } else if (title.equals("Trip Complete")) {
                pushNotification = new Intent(Config.Customer_TripComplete);
            } else if (title.equals("Cancel_Request")) {
                pushNotification = new Intent(Config.Cancel_Request);
            }
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();

        } else {
            // app is in background, show the notification in notification tray
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                final String ANDROID_CHANNEL_ID = "cabbooking.pixelmarketo.ocab.ANDROID";
                if (title.equals("Trip Complete")) {
                    if (UserRideHelper.getInstance().getUserRideModel().size() > 0) {
                        UserRideHelper.getInstance().delete();
                    }
                }
                Intent intent = new Intent(getApplicationContext(), DashBoardActivity.class);
                intent.putExtra("message", message);
                intent.putExtra("Check", "");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NetworkUtileforOreao mNotificationUtils = new NetworkUtileforOreao(getApplicationContext());
                Notification.Builder nb = new Notification.Builder(getApplicationContext(), ANDROID_CHANNEL_ID).
                        setSmallIcon(R.drawable.ic_logo).setContentTitle(title).setContentText(Content).setAutoCancel(true).setSound(defaultSoundUri).setContentIntent(pendingIntent);
                *//*NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)*//*;
                mNotificationUtils.getManager().notify(0, nb.build());
            }
            else {
                if (title.equals("Trip Complete")) {
                    if (UserRideHelper.getInstance().getUserRideModel().size() > 0) {
                        UserRideHelper.getInstance().delete();
                    }
                }
                Intent intent = new Intent(getApplicationContext(), DashBoardActivity.class);
                intent.putExtra("message", message);
                intent.putExtra("Check", "");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this).
                        setSmallIcon(R.drawable.ic_logo).setContentTitle(title).setContentText(Content).setAutoCancel(true).setSound(defaultSoundUri).setContentIntent(pendingIntent);
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0, notificationBuilder.build());
            }


        }


    }

    private void handleNotificationlogout(String title, String Content, String message) {

        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = null;
            if (title.equals("logout")) {
                pushNotification = new Intent(Config.logout);
                UserProfileHelper.getInstance().delete();
                ErrorMessage.I_clear(MyFirebaseMessagingService.this, LoginActivity.class, null);
            }
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();

        } else {
            // app is in background, show the notification in notification tray
            UserProfileHelper.getInstance().delete();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("message", message);
            intent.putExtra("Check", "");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this).
                    setSmallIcon(R.drawable.ic_logo).setContentTitle(title).setContentText(Content).setAutoCancel(true).setSound(defaultSoundUri).setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notificationBuilder.build());


        }


    }
*/

}

