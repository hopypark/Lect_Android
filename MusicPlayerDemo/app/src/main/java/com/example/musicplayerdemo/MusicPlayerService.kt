package com.example.musicplayerdemo

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder

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

    private fun startForegroundService() {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}