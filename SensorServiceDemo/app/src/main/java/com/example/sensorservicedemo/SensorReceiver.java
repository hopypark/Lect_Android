package com.example.sensorservicedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SensorReceiver extends BroadcastReceiver {
    private static final String TAG = "SensorService";

    private MainActivity mContext;

    public SensorReceiver(Context mContext) {
        this.mContext = (MainActivity) mContext;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive() 메서드 호출");

        float values[] = intent.getFloatArrayExtra("acc");
        //MainActivity main = (MainActivity) context;
        mContext.tv_accX.setText("X: " + values[0]);
        mContext.tv_accY.setText("Y: " + values[1]);
        mContext.tv_accZ.setText("Z: " + values[2]);
        Log.d(TAG, "X: " + values[0] + ", Y: " + values[1] + ", Z: " + values[2]);
    }
}

