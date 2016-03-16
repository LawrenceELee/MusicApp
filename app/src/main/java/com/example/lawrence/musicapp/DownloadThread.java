package com.example.lawrence.musicapp;

import android.util.Log;

// refactor the thread code into its own separate class.
public class DownloadThread extends Thread {

    private static final String TAG = DownloadThread.class.getSimpleName();

    @Override
    public void run() {

        // the loop needs to be inside the run() method, not surrounding the code OnClickListener
        // since we want to download each song 1 at a time (one after the previous finished).
        // if it were in the OnClickListener, it would create a new thread for each song and
        // download all the songs at the same time.
        for(String song : Playlist.songs){
            downloadSong();
        }
    }

    // pretend to download a song by simulating the time it takes to download a song.
    private void downloadSong() {

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
        Log.d(TAG, "Song downloaded!");

    }
}
