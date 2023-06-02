package com.example.servicedemo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class MusicService extends Service {
    private static final String TAG = "MusicService"; // Log를 위한 태그명
    MediaPlayer player; // 음악 재생기용 인스턴스 변수 추가
    //
    public static final String Channel_ID = "1";
    public static final String Channel_name = "1";
    public static final int NOTIFICATION_ID = 10;

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
        //return super.onStartCommand(intent, flags, startId);
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        // NotificationCompat.Builder : 다양한 정보를 가진 알림 생성
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, Channel_ID);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("MusicService 실행중")
                .setContentText("schoolbell.mp3 is playing")
                .setContentIntent(pendingIntent);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
            // NotificationManager - 알림을 시스템에 발생시키는 SystemService
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // NotificationChannel - 알림의 관리 단위
            NotificationChannel channel = new NotificationChannel(Channel_ID, Channel_name, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        startForeground(NOTIFICATION_ID, builder.build());
        return START_STICKY;
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

