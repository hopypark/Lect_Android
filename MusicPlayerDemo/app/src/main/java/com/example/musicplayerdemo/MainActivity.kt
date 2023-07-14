package com.example.musicplayerdemo

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindService()
    }

    val mServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            // 서비스 연결 성공 TODO("Not yet implemented")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            // 서비스 연결 실패(종료) TODO("Not yet implemented")
        }
    }

    private fun bindService(){
        val intent = Intent(this, MusicPlayerService::class.java)
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
    }
}

