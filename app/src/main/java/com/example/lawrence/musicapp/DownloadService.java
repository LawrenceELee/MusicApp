package com.example.lawrence.musicapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

public class DownloadService extends Service {
    private static final String TAG = DownloadService.class.getSimpleName();

    private DownloadHandler mHandler;

    // There are 2 ways to run a service.
    // 1) start a service - typically used to start a SINGLE operation, and stop itself once it is done.
    // 2) bind a service - let's us use a service like a client-server model. for example, since we can't access the GPS directly, the location service acts like a server for our requests.


    @Override
    public void onCreate() {
        DownloadThread thread = new DownloadThread();
        thread.setName("DownloadThread");
        thread.start();

        while( thread.mHandler == null ){
            // do nothing, wait for DownloadThread to create mHandler before assigning below to avoid null pointer exception
            // this is not a best practice, but it does the job.
        }
        mHandler = thread.mHandler;
        mHandler.setService(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String song = intent.getStringExtra(MainActivity.KEY_SONG);

        Message msg = Message.obtain();
        msg.obj = song;
        msg.arg1 = startId;
        mHandler.sendMessage(msg);

        return Service.START_REDELIVER_INTENT;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
