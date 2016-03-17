package com.example.lawrence.musicapp;

import android.os.Looper;
import android.util.Log;

// refactor the thread code into its own separate class.
public class DownloadThread extends Thread {

    private static final String TAG = DownloadThread.class.getSimpleName();

    public DownloadHandler mHandler;

    @Override
    public void run() {
        Looper.prepare();
        mHandler = new DownloadHandler();
        Looper.loop();      // starts looping on message queue.

        /*
        // old style without Loopers and Handlers.
        // the loop needs to be inside the run() method, not surrounding the code OnClickListener
        // since we want to download each song 1 at a time (one after the previous finished).
        // if it were in the OnClickListener, it would create a new thread for each song and
        // download all the songs at the same time.
        for(String song : Playlist.songs){
            downloadSong();
        }
        */
    }
}
