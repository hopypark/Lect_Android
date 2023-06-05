package com.example.broadcastreceiverdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyAirplaneReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Broadcast Receiver의 상태 정보를 intent로부터 받음, 받는 값이 없다면 default값 false를 사용한다.
        if (intent.getAction().equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
            // 비행기 모드에 대한 방송수신 처리
            boolean state = intent.getBooleanExtra("state", false);
            if (state) {
                Log.d(TAG, "Action: 비행기 모드 설정");
            } else {
                Log.d(TAG, "Action: 비행기 모드 해제");
            }
        }else if (intent.getAction().equals(MainActivity.MY_ACTION_BROADCAST)){
            Log.d(TAG, "사용자가 보낸 방송을 받음");
        }
    }
}

