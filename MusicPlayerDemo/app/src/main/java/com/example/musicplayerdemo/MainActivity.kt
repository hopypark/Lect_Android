package com.example.musicplayerdemo

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var btnPlay: Button
    lateinit var btnPause: Button
    lateinit var btnStop: Button

    var mService: MusicPlayerService? = null;   // 서비스 변수

    // 서비스와 구성요소 연결 상태 모니터링
    val mServiceConnection = object : ServiceConnection{
        // 만약 서비스가 연결되면...
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            // service 참조변수를  MusicPlayerService의 MusicPlayerBinder객체로 변환
            // 변환된 Binder객체의 getService()함수로 서비스 객체를 받음
            mService = (service as MusicPlayerService.MusicPlayerBinder).getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mService = null // 서비스가 끊기면 mService를 null로 만든다.
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPlay = findViewById(R.id.btn_play)
        btnPause = findViewById(R.id.btn_pause)
        btnStop = findViewById(R.id.btn_stop)

        btnPlay.setOnClickListener{
            play()
        }

        btnPause.setOnClickListener{
            puase()
        }

        btnStop.setOnClickListener{
            stop()
        }
    }

    private fun stop() {
        mService?.stop()
    }

    private fun puase() {
        mService?.pause()
    }

    private fun play() {
        mService?.play()
    }

    override fun onResume() {
        super.onResume()

        // 액티비티가 화면 전면으로 실행될 때, 서비스 실행
        if (mService == null){
            // 안드로이드 Oreo 이상이면 startForegroundService를 사용해야 한다.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                startForegroundService(Intent(this, MusicPlayerService::class.java))
            } else {
                startService(Intent(applicationContext, MusicPlayerService::class.java))
            }
            // 액티비티를 서비스와 바인드 시킨다.
            val intent = Intent(this, MusicPlayerService::class.java)
            // 서비스와 바인드
            bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onPause() {
        super.onPause()
        // 사용자가 액티비티를 떠났을 대 처리
        if (mService != null){
            if(!mService!!.isPlaying()){    // mService가 재생되고 있지 않다면
                mService!!.stopSelf()       // 서비스를 중단
            }
            unbindService(mServiceConnection)   // 서비스로부터 연결을 끊는다.
            mService = null
        }
    }
}

