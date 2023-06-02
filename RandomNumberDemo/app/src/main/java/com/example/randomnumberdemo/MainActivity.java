package com.example.randomnumberdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "LocalService";
    LocalService mService;
    boolean mBound = false;

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() 메서드 호출 - 서비스를 시작함");
        Intent intent = new Intent(getApplicationContext(), LocalService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() 메서드 호출");
        if(mBound){ unbindService(serviceConnection); mBound = false;}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv_number = findViewById(R.id.tv_number);
        Button btnRndGen = findViewById(R.id.btn_rndGen);
        btnRndGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBound){
                    tv_number.setText("" + mService.getRandomNumber());
                }
            }
        });
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG,"onServiceConnected() 호출 - 서비스 객체 가져오기");
            LocalService.LocalBinder binder = (LocalService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG,"onServiceDisconnected() 호출 ");
            mBound = false;
        }
    };  // ServiceConnection() 생성자 메소드 끝
}