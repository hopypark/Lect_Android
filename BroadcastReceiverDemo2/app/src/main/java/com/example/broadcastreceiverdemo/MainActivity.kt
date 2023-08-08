package com.example.broadcastreceiverdemo

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    var airplaneReceiver:BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        // 방송 수신자 등록
        airplaneReceiver = MyAirplaneReceiver()
        var intentFilter: IntentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(airplaneReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(airplaneReceiver)
    }
}