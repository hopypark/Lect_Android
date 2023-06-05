package com.example.sensorservicedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SensorReceiver receiver;
    Intent intent; // 센서 서비스를 구동하는 데 사용하는 인텐트
    public TextView tv_accX, tv_accY, tv_accZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_accX = findViewById(R.id.tv_accX);
        tv_accY = findViewById(R.id.tv_accY);
        tv_accZ = findViewById(R.id.tv_accZ);

        intent = new Intent(getApplicationContext(), SensorService.class);
        startService(intent);   // 서비스를 시작한다. - SensorService객체의 onStartCommand() 호출
   }

    @Override
    protected void onStop() {
        super.onStop();
        if (receiver != null){ // 방송 수신자 등록 해제
            unregisterReceiver(receiver);
            receiver = null;
        }
        stopService(intent);   // 센서서비스 중지
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null){ // 방송 수신자 등록 해제
            unregisterReceiver(receiver);
            receiver = null;
        }
        stopService(intent);   // 센서서비스 중지
    }

    @Override
    protected void onResume() {
        super.onResume();
        receiver = new SensorReceiver(MainActivity.this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(SensorService.MY_ACTION_SENSOR);
        registerReceiver(receiver, filter);
        startService(intent);
    }
}

