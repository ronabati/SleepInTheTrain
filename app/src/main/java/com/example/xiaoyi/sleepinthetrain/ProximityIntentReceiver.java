package com.example.xiaoyi.sleepinthetrain;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

/**
 * Created by xiaoyi on 2/02/2016.
 */
public class ProximityIntentReceiver extends BroadcastReceiver {
    private static final int NOTIFICATION_ID = 1000;
    long[] pattern = {500,500,500,500,500,500,500,500,500};


    @Override
    public void onReceive(Context context, Intent intent) {
        String key = LocationManager.KEY_PROXIMITY_ENTERING;
        Boolean entering = intent.getBooleanExtra(key, false);
        if (entering) {
            Log.d(getClass().getSimpleName(), "entering");
        }else {
            Log.d(getClass().getSimpleName(), "exiting");
        }
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, MapsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

//        notification.setLatestEventInfo(context,"Proximity Alert!", "You are near your point of interest.", pendingIntent);

        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Wake Up!!")
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setVibrate(pattern)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("You are near your destination!"))

                .setContentText("You are near your destination!")
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis());

        Notification notification = mBuilder.build();
        notification.flags |= notification.FLAG_INSISTENT;





        notificationManager.notify(NOTIFICATION_ID, notification);




        //       notificationManager.notify(NOTIFICATION_ID, notification);

    }

    private Notification createNotification() {

        Notification notification = new Notification();
       // notification.icon = R.drawable.ic_launcher;
        notification.when = System.currentTimeMillis();
       // notification.flags |= Notification.FLAG_AUTO_CANCEL;
        //notification.flags |= Notification.FLAG_ONGOING_EVENT;
        //notification.flags |= Notification.FLAG_INSISTENT;
      //  notification.defaults |= Notification.DEFAULT_VIBRATE;
        //notification.defaults |= Notification.DEFAULT_LIGHTS;
        notification.ledARGB = Color.WHITE;
        notification.sound = Uri.parse("http://www.robtowns.com/music/blind_willie.mp3");
        //notification.ledOnMS = 1500;
        //notification.ledOffMS = 1500;


        return notification;
    }



}
