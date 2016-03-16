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

                /*
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
                */

                // alternate way by extending Thread class to our own DownloadThread class.
                DownloadThread thread = new DownloadThread();
                thread.setName("DownloadThread");
                thread.start();
            }
        });
    }
}
