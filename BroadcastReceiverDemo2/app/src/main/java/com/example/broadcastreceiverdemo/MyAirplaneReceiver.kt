package com.example.broadcastreceiverdemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MyAirplaneReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "MyReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        if(intent.action.equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)){
            var state = intent.getBooleanExtra("state", false)
            if (state){
                Log.d(TAG, "ACTION: 비행기 모드 설정")
            }else{
                Log.d(TAG, "ACTION: 비행기 모드 해제")
            }
        }
    }
}

