package com.example.lawrence.musicapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String KEY_SONG = "song";

    private Button mDownloadButton;

    private Button mPlayButton;
    //private PlayerService mPlayerService; // convert BoundService to process.

    private Messenger mServiceMessenger;
    private Messenger mActivityMessenger = new Messenger(new ActivityHandler(this));

    private boolean mBound = false;
    // ServiceConnection is a way for Android to connect a client and a service.
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder binder) {
            mBound = true;
            /*
            // converted from BoundService to Process
            PlayerService.LocalBinder localBinder = (PlayerService.LocalBinder) binder;
            mPlayerService = localBinder.getService();

            // checks if music player was already playing
            if (mPlayerService.isPlaying()) {
                mPlayButton.setText("Pause");
            }
            */
            mServiceMessenger = new Messenger(binder);
            Message message = Message.obtain();
            message.arg1 = 2;
            message.arg2 = 1;
            message.replyTo = mActivityMessenger;
            try {
                mServiceMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        // code for starting a thread. not need anymore since we refactor into a service.
        final DownloadThread thread = new DownloadThread();
        thread.setName("DownloadThread");
        thread.start();
        */

        // for "Play" button
        mPlayButton = (Button) findViewById(R.id.playButton);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBound) {

                    /*
                    // converted from BoundService to Process
                    if (mPlayerService.isPlaying()) {
                        mPlayerService.pause();
                        mPlayButton.setText("Play");
                    } else {
                        Intent intent = new Intent(MainActivity.this, PlayerService.class);
                        startService(intent);
                        mPlayerService.play();
                        mPlayButton.setText("Pause");
                    }
                    */

                    Intent intent = new Intent(MainActivity.this, PlayerService.class);
                    startService(intent);

                    Message message = Message.obtain();
                    message.arg1 = 2;
                    message.replyTo = mActivityMessenger;
                    try {
                        mServiceMessenger.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // for "Download" button
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
                for (String song : Playlist.songs) {

                    /*
                    // code for sending a message. not need anymore since we refactor into a service.
                    Message message = Message.obtain(); // get a messsage object from pool
                    message.obj = song;                 // attach song obj to message
                    thread.mHandler.sendMessage(message);
                    */

                    /*
                    // code to start a Service
                    Intent intent = new Intent(MainActivity.this, DownloadService.class);
                    intent.putExtra(KEY_SONG, song);
                    startService(intent);
                    */

                    // code to start an IntentService
                    Intent intent = new Intent(MainActivity.this, DownloadIntentService.class);
                    intent.putExtra(KEY_SONG, song);
                    startService(intent);
                }
            }
        });
    }

    public void changePlayButtonText(String text) {
        mPlayButton.setText(text);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, PlayerService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mBound) {
            unbindService(mServiceConnection);
            mBound = false;
        }
    }
}
