package com.example.broadcastreceiverdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private BroadcastReceiver airplaneReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 방송수신자 등록 - 동적 등록
        airplaneReceiver = new MyAirplaneReceiver();
        IntentFilter filter = new IntentFilter(); // 받고자하는 방송 내용에 대한 필터 설정
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED); // AIRPLANE 모드가 변경되는 것
        registerReceiver(airplaneReceiver, filter); // 리시버에 등록
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        IntentFilter filter = new IntentFilter(); // 받고자하는 방송 내용에 대한 필터 설정
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED); // AIRPLANE 모드가 변경되는 것
        registerReceiver(airplaneReceiver, filter); // 리시버에 등록
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(airplaneReceiver);
    }
}

