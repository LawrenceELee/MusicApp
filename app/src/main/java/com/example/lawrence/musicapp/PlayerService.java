package com.example.lawrence.musicapp;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class PlayerService extends Service {

    private static final String TAG = PlayerService.class.getSimpleName();
    private MediaPlayer mPlayer;

    private IBinder mIBinder = new LocalBinder();

    @Override
    public void onCreate() {  // only called once in the life of a Service.
        Log.d(TAG, "onCreate");
        mPlayer = MediaPlayer.create(this, R.raw.audiofile);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) { // might be called multiple times in the life of a Service.
        Log.d(TAG, "onBind");
        return mIBinder;
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

    public int onStartCommand(Intent service) {

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                stopSelf();
            }
        });

        return Service.START_NOT_STICKY;
    }

    // define a binder object/class needed to bind PlayerService with MainActivity
    // IBinder is an interface, Java docs say to extend Binder class (which implements IBinder).
    public class LocalBinder extends Binder {
        public PlayerService getService(){
            return PlayerService.this;
        }
    }

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
