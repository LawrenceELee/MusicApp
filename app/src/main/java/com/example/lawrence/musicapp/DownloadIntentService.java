package com.example.lawrence.musicapp;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

// Android made writing services easier by providing the IntentService class
public class DownloadIntentService extends IntentService{

    private static final String TAG = DownloadIntentService.class.getSimpleName();

    public DownloadIntentService(){
        super("DownloadIntentService");

        // by default services starts using START_STICKY.
        // we can change the return value of onStartCommand to START_REDELIEVER_INTENT below.
        setIntentRedelivery(true);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String song = intent.getStringExtra(MainActivity.KEY_SONG);
        downloadSong(song);
    }

    // pretend to download a song by simulating the time it takes to download a song.
    private void downloadSong(String songName) {
        // working with time is easier when working in milliseconds
        long endTime = System.currentTimeMillis() + (10 * 1000);    // add 10 seconds.
        while(System.currentTimeMillis() < endTime){
            // instead of Thread.sleep(1); which sleeps for 1 millisecond and waste resources constantly checking the loop
            // we only check every 1 second (1000 milliseconds).
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, songName + " downloaded!");
    }
}
