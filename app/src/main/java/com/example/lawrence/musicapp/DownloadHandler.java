package com.example.lawrence.musicapp;

import android.os.Message;
import android.os.Handler;
import android.util.Log;

public class DownloadHandler extends Handler{

    private static final String TAG = DownloadHandler.class.getSimpleName();

    @Override
    public void handleMessage(Message msg) {
        downloadSong(msg.obj.toString());
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
