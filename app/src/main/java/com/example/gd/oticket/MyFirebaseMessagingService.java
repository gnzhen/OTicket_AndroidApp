package com.example.gd.oticket;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Random;

/**
 * Created by GD on 3/27/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String ADMIN_CHANNEL_ID = "0";

    private NotificationManager notificationManager;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if(remoteMessage.getData().size() > 0){

            Map<String, String> params = remoteMessage.getData();
            JSONObject object = null;
            try {
                object = new JSONObject(params.get("message"));

                //Calling method to generate notification
                sendNotification(
                        object.get("title").toString(),
                        object.get("body").toString()
                );

                broadcastDialog(object.get("type").toString(), object.getJSONObject("data"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //This method is only generating push notification
    private void sendNotification(String title, String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        //Setting up Notification channels for android O and above
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            setupChannels();
        }
        int notificationId = new Random().nextInt(60000);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    private void broadcastDialog(String type, JSONObject data){

        Log.d("broadcast type", "type");

        String titleLg, titleSm, ticketId, ticketNo, branchName, serviceName, waitTime;

        try {
            titleLg = "";
            titleSm = "";
            ticketId = data.get("ticket_id").toString();
            ticketNo = data.get("ticket_no").toString();
            waitTime = data.get("ticket_wait_time").toString();
            branchName = data.get("branch_name").toString();
            serviceName = data.get("service_name").toString();

            if(type.equals("call")){
                titleLg = "Calling";
                titleSm = "at " + data.get("counter_name").toString();
            }
            else if(type.equals("recall")){
                titleLg = "Recalling";
                titleSm = "at " + data.get("counter_name").toString();
            }
            else if(type.equals("skip")){
                titleLg = "You're skipped";
                titleSm = "Opps! It takes too long.";
            }
            else if(type.equals("next")){
                titleLg = "You are next!";
                titleSm = "Prepare for you turn.";
            }
            else if(type.equals("near")){
                titleLg = "Almost there";
                titleSm = "in " + waitTime;
            }
            else if(type.equals("change")){
                String status = data.get("change_type").toString();
                titleLg = data.get("change_time").toString() + " " + status;

                if(status.equals("delay")){
                    titleSm = "The queue goes slower than expected.";
                }
                else{
                    titleSm = "The queue goes faster than expected.";
                }
            }

            NotiMsg notiMsg = new NotiMsg(titleLg, titleSm, ticketId, ticketNo, waitTime, branchName, serviceName);

            Intent intent = new Intent("show-noti-dialog");

            Bundle bundle = new Bundle();
            bundle.putSerializable("notiMsg", notiMsg);

            intent.putExtras(bundle);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannels(){
        CharSequence adminChannelName = "notifications_admin_channel_name";
        String adminChannelDescription = "notifications_admin_channel_description";
        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_LOW);
        adminChannel.setDescription(adminChannelDescription);
        adminChannel.enableLights(true);
        adminChannel.setLightColor(Color.RED); adminChannel.enableVibration(true);

        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }
    }

}
