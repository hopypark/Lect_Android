package com.example.broadcastreceiverdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private BroadcastReceiver airplaneReceiver;
    public static final String MY_ACTION_BROADCAST = "MY Broadcasting...";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MY_ACTION_BROADCAST);
                sendBroadcast(intent); // 인텐트를 사용한 Broadcast 보내기
            }
        });
        
        // 방송수신자 등록 - 동적 등록
        airplaneReceiver = new MyAirplaneReceiver();
        IntentFilter filter = new IntentFilter(); // 받고자하는 방송 내용에 대한 필터 설정
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED); // AIRPLANE 모드가 변경되는 것
        filter.addAction(MY_ACTION_BROADCAST); // filter 항목으로 MY_ACTION_BROADCAST 추가
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

