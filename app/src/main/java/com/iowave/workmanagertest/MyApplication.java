package com.iowave.workmanagertest;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Configuration;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

/**
 * Created by Ravi Parmar on 09-07-2020.
 */
public class MyApplication extends Application implements Configuration.Provider {


    @Override
    public void onCreate() {
        super.onCreate();
        setWorker();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @NonNull
    @Override
    public Configuration getWorkManagerConfiguration() {
        return new Configuration.Builder()
                .setMinimumLoggingLevel(Log.DEBUG)
                .build();
    }


    public void setWorker() {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();


        PeriodicWorkRequest myWork =
                new PeriodicWorkRequest.
                        Builder(MyWorker.class,
                        15, TimeUnit.MINUTES, 5, TimeUnit.MINUTES)
                        .addTag("app_periodic")
                        .setConstraints(constraints)
                        .build();


        try {

            WorkManager.getInstance(this)
                    .enqueueUniquePeriodicWork("app_worker_notify",
                            ExistingPeriodicWorkPolicy.KEEP, myWork);

        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }


}
