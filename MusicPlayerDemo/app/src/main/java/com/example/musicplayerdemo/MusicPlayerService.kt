package com.example.musicplayerdemo

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.widget.Toast

class MusicPlayerService : Service() {

    var mMediaPlayer: MediaPlayer? = null // 미디어 플레이어 객체를 null로 초기화

    var mBinder:MusicPlayerBinder = MusicPlayerBinder()

    inner class MusicPlayerBinder: Binder(){    //
        fun getService(): MusicPlayerService{
            return this@MusicPlayerService
        }
    }

    override fun onBind(intent: Intent): IBinder { // 바인터 반환, 반인드가 필요없는 서비스라면 null 반환
        return mBinder
    }

    override fun onCreate() {
        super.onCreate()
        startForegroundService()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    private fun startForegroundService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){ 
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val mChannel = NotificationChannel( // 알림 채널 생성 ( API 26. Oreo 이상부터는 채널 사용 
                "CHANNEL_ID", "CHANNEL_NAME", NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(mChannel)
        }
        // 알림 생성
        val notification: Notification = Notification.Builder(this, "CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_play)       // 알림 아이콘
            .setContentTitle("뮤직 플레이어 앱")       // 알림의 제목 설정
            .setContentText("앱이 실행 중입니다.")      // 알림의 내용
            .build()
        startForeground(1, notification)    // 알림 ID와 알림을 지정하여 포그라운드 서비스 시작
    }
    // 서비스 중단 처리
    override fun onDestroy() {
        super.onDestroy()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            stopForeground(STOP_FOREGROUND_DETACH)
        }
    }
    // 재생되고 있는지 확인
    fun isPlaying(): Boolean{
        return (mMediaPlayer != null && mMediaPlayer?.isPlaying ?: false)
    }
    // 음악 재생
    fun play(){
        if (mMediaPlayer == null){  // mp3 리소스를 가져와 미디어 플레이어 객체를 할당
            mMediaPlayer = MediaPlayer.create(this, R.raw.hare)
            mMediaPlayer?.setVolume(1.0f, 1.0f)   // 볼륨을 지정
            mMediaPlayer?.isLooping = true                           // 반복 재생 여부
            mMediaPlayer?.start()                                    // 음악을 재생
        } else{ // 음악이 재생 중인 경우
            if(mMediaPlayer!!.isPlaying){
                Toast.makeText(this, "이미 음악이 재생 중입니다.", Toast.LENGTH_SHORT).show()
            } else {
                mMediaPlayer?.start()
            }
        }
    }
    // 일시 정지
    fun pause(){
        mMediaPlayer?.let {
            if (it.isPlaying) it.pause()
        }
    }
    // 재생 중지
    fun stop(){
        mMediaPlayer?.let {
            if(it.isPlaying){
                it.stop()               // 음악을 멈춥니다.
                it.release()            // 미디어 플레이어에 할당된 자원을 해제시켜준다.
                mMediaPlayer = null
            }
        }
    }

}

