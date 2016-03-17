package com.example.lawrence.musicapp;

import android.os.Message;
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

        final DownloadThread thread = new DownloadThread();
        thread.setName("DownloadThread");
        thread.start();

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

                /*
                // moved out outside the click listener.
                // alternate way by extending Thread class to our own DownloadThread class.
                DownloadThread thread = new DownloadThread();
                thread.setName("DownloadThread");
                thread.start();
                */

                // Send Messages (or Runnables) to Handler for processing
                // because of the nature of threads, you don't know if thread.start() will create the handler (in run())
                // before the main ui thread sends messages or runnables to that thread.
                // this will cause a NullPointerException.
                // The solution to this is to start() the thread when the Activity is created, not in the OnClickListener.

                // give our handler a bunch of song titles knowing that it can handle them by itself.
                // instead of creating a runnable for each song and specific how to download it.
                for( String song : Playlist.songs ) {
                    Message message = Message.obtain(); // get a messsage object from pool
                    message.obj = song;                 // attach song obj to message
                    thread.mHandler.sendMessage(message);
                }

            }
        });
    }
}
