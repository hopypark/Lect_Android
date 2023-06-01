package com.example.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class MusicService extends Service {
    private static final String TAG = "MusicService"; // Log를 위한 태그명
    MediaPlayer player; // 음악 재생기용 인스턴스 변수 추가
    
    public MusicService() {
    }

    @Override
    public void onCreate() { // 서비스가 생성될 때
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.schoolbell);
        player.setLooping(true);
        Log.d(TAG, "onCreate() 호출 - 음악 재생기 설정");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start(); // 재생시작
        Log.d(TAG, "onStartCommand() 호출 - 음악 재생 서비스 시작");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop(); player.release();
        Log.d(TAG, "onDestroy() 호출 - 음약 재생 서비스 종료");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

