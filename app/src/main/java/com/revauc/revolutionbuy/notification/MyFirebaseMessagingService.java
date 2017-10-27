/*
 * Copyright © 2017 Thrive fantasy. All rights reserved.
 * Developed by Appster.
 */

package com.revauc.revolutionbuy.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.ui.dashboard.DashboardActivity;
import com.revauc.revolutionbuy.util.Constants;
import com.revauc.revolutionbuy.util.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private static final String TYPE = "type";
    private static final String DATA = "data";
    private static final String NOTIFICATION_ID = "id";
    private static final String NOTIFICATION_TYPE_ID = "type";

    private static final String MESSAGE = "message";
    private static final String DESCRIPTION = "description";
    private static final String DICTIONARY = "datamsg";
    private static final int TYPE_BUYER_MARK = 3;
    private static final int TYPE_ADMIN = 2;
    private static final int TYPE_USER_DELETE = 10;
    private static final int TYPE_TERMS_UPDATE = 14;
    private static final int TYPE_DISABLED_PLAYER = 12;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        NotificationPayload payload = new NotificationPayload();

        LogUtils.LOGD(">>>>>>", "FCM MESSAGE RECIEVED : "+remoteMessage.getData());
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        // {orderId=325, restaurantName=Social, eatingType=2, orderStatus=2}
        // Check if message contains a data payload.

        // {data=4, notificationId=2, message=HB NBA - Trad this is invite message, notificationType=Invite to Contest}
        JSONObject notificationObject = null;
        if (remoteMessage.getData()!=null) {
            try {
                notificationObject = new JSONObject(remoteMessage.getData().get(DICTIONARY));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(notificationObject!=null)
            {
                try {
                    payload.setNotificationId(notificationObject.getInt(NOTIFICATION_ID));
                    payload.setNotificationtypeId(notificationObject.getInt(NOTIFICATION_TYPE_ID));
                    payload.setMessage(notificationObject.getString(DESCRIPTION));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            LogUtils.LOGD("notification", "" + remoteMessage.getData());
//            payload.setType(remoteMessage.getData().get(TYPE));
//            payload.setContestID(remoteMessage.getData().get(DATA));
//            payload.setNotificationType(remoteMessage.getData().get(DICTIONARY));
//            payload.setMessage(remoteMessage.getData().get(MESSAGE));
        sendNotification(remoteMessage.getData().get(MESSAGE), payload);
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }


    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody, NotificationPayload payload) {

        PendingIntent pendingIntent;
        Intent intent;

        if (payload == null) {
            return;
        }

        if (payload.getNotificationtypeId() == TYPE_BUYER_MARK) {
            intent = new Intent(this, DashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        }
        else if (payload.getNotificationtypeId() == TYPE_ADMIN) {
            intent = new Intent(this, DashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        }
        else
        {
            intent = new Intent(this, DashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        }
//        else if (Integer.parseInt(payload.getNotificationType()) == TYPE_15_MIN_BEFORE) {
//            intent = new Intent(this, DashboardActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            pendingIntent = PendingIntent.getActivity(this, notificationType, intent,
//                    PendingIntent.FLAG_UPDATE_CURRENT);
//        } else if (Integer.parseInt(payload.getNotificationType()) == TYPE_USER_DELETE) {
//            intent = new Intent(this, DashboardActivity.class);
//            intent.putExtra(Constants.EXTRA_IS_USER_DELETED, true);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            pendingIntent = PendingIntent.getActivity(this, notificationType, intent,
//                    PendingIntent.FLAG_UPDATE_CURRENT);
//
//            //Send Broadcast
//            sendBroadcast(new Intent(this, UserDeleteReceiver.class));
//        } else if (Integer.parseInt(payload.getNotificationType()) == TYPE_TERMS_UPDATE) {
//            intent = new Intent(this, DashboardActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            pendingIntent = PendingIntent.getActivity(this, notificationType, intent,
//                    PendingIntent.FLAG_UPDATE_CURRENT);
//
//            //Send Broadcast
//            sendBroadcast(new Intent(this, TnCReceiver.class));
//        }
//        else if (Integer.parseInt(payload.getNotificationType()) == TYPE_DISABLED_PLAYER) {
//            intent = new Intent(this, DashboardActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            pendingIntent = PendingIntent.getActivity(this, notificationType, intent,
//                    PendingIntent.FLAG_UPDATE_CURRENT);
//
//            //Send Broadcast
//            Intent broadcastIntent = new Intent();
//            broadcastIntent.putExtra(Constants.EXTRA_MESSSAGE,payload.getMessage());
//            broadcastIntent.setAction(Constants.INTENT_ACTION_PLAYER_DISABLED);
//            sendBroadcast(broadcastIntent);
//        }
//        else {

//        }

        int color = ContextCompat.getColor(this, R.color.colorAccent);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(getNotificationIcon())
                .setLargeIcon(getLargeIcon())
                .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                .setContentTitle(getString(R.string.app_name))
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setColor(color)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(payload.getNotificationId(), notificationBuilder.build());
    }


    private Bitmap getLargeIcon() {

        return BitmapFactory.decodeResource(
                getResources(),
                R.mipmap.ic_launcher);
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.ic_stat_sillhoute_icon : R.mipmap.ic_launcher;
    }
}
