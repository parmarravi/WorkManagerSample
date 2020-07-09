package com.iowave.workmanagertest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Calendar;

/**
 * Created by Ravi Parmar on 09-07-2020.
 */
public  class MyWorker  extends Worker {

    private Context context;

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
       generateLocalNotification(context);
        return Worker.Result.success();
    }

    private void generateLocalNotification(Context context) {
        try {
            String channelId = "890";
            String channelName = "workerTest";

            NotificationManager mNotificationManager;
            mNotificationManager = (NotificationManager) context.
                    getSystemService(Context.NOTIFICATION_SERVICE);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId, channelName,
                        NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription("WORKER choice");
                channel.enableLights(true);
                channel.setLightColor(ContextCompat.getColor(context, R.color.colorPrimary));
                mNotificationManager.createNotificationChannel(channel);
            }


            // Apply the layouts to the notification
            Notification customNotification = new NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setAutoCancel(true)
                    .setContentTitle("WORK MANAGER TEST")
                    .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                    .build();


            mNotificationManager.notify(30, customNotification);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

