package com.example.broadcastreceiverdemo

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

const val MY_ACTION_BROADCAST = "My Broadcasting"

class MainActivity : AppCompatActivity() {

    var airplaneReceiver:BroadcastReceiver = MyAirplaneReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 버튼
        val btn = findViewById<Button>(R.id.button)
        btn.setOnClickListener {
            val intent = Intent(MY_ACTION_BROADCAST)
            sendBroadcast(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // 방송 수신자 등록
        var intentFilter: IntentFilter = IntentFilter() // 받고자하는 내용을 필터로 설정
        intentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        intentFilter.addAction(MY_ACTION_BROADCAST) // filter 항목으로 등록
        registerReceiver(airplaneReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(airplaneReceiver)
    }
}

