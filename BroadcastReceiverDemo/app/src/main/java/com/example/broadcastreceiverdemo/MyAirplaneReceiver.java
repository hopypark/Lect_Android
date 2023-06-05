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
        boolean state = intent.getBooleanExtra("state", false);
        if (state){
            Log.d(TAG, "Action: 비행기 모드 설정");
        } else {
            Log.d(TAG, "Action: 비행기 모드 해제");
        }
    }
}

