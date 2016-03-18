package com.example.lawrence.musicapp;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.util.Log;

public class PlayerService extends Service {

    private static final String TAG = PlayerService.class.getSimpleName();
    private MediaPlayer mPlayer;

    // no longer need since we are converted a BoundService to a Process
    //private IBinder mIBinder = new LocalBinder();

    public Messenger mMessenger = new Messenger(new PlayerHandler(this)); // so PlayerHandler has access to it.



    @Override
    public void onCreate() {  // only called once in the life of a Service.
        Log.d(TAG, "onCreate");
        mPlayer = MediaPlayer.create(this, R.raw.audiofile);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) { // might be called multiple times in the life of a Service.
        Log.d(TAG, "onBind");

        //return mIBinder; // no longer need since convert from BoundService to a Process.
        return mMessenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnBind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        mPlayer.release();
    }

    // make this process a foreground process to have top priority
    public int onStartCommand(Intent service, int flags, int startId) {
        Notification.Builder notificationBuilder = new Notification.Builder(this);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        Notification notification = notificationBuilder.build();
        startForeground(11, notification); // just pick any non-zero random number, so we picked 11.

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                stopSelf();
                stopForeground(true);
            }
        });

        return Service.START_NOT_STICKY;
    }

    // no longer need since we are converted a BoundService to a Process
    // define a binder object/class needed to bind PlayerService with MainActivity
    // IBinder is an interface, Java docs say to extend Binder class (which implements IBinder).
    /*
    public class LocalBinder extends Binder {
        public PlayerService getService(){
            return PlayerService.this;
        }
    }
    */

    // client methods
    public void play(){
        mPlayer.start();
    }

    public void pause(){
        mPlayer.pause();
    }

    public boolean isPlaying(){
        return mPlayer.isPlaying();
    }

}
