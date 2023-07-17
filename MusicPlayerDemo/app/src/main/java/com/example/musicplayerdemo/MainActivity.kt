package com.example.musicplayerdemo

import android.content.ComponentName
import android.content.ServiceConnection
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
        TODO("Not yet implemented")
    }

    private fun puase() {
        TODO("Not yet implemented")
    }

    private fun play() {
        TODO("Not yet implemented")
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }
}

