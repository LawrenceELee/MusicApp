package com.example.lawrence.musicapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Button mDownloadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDownloadButton = (Button) findViewById(R.id.downloadButton);

        mDownloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Downloading", Toast.LENGTH_LONG).show();

                // need to use a separate thread to run downloadSong() method so that it
                // doesn't slow down the MainUI thread.
                // Threads take a Runnable object as a parameter, so we wrap our method inside one.
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        downloadSong();
                    }
                }; // don't forget to end Runnable STATEMENT with a semicolon.
                Thread thread = new Thread(runnable);
                thread.setName("DownloadThread");
                thread.start();
            }
        });
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
