package com.example.sensorservicedemo;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

public class SensorService extends Service implements SensorEventListener {
    // 상수 선언
    public static final String MY_ACTION_SENSOR = "MY_ACTION_SENSOR";
    private static final String TAG = "SensorService";
    // 센서관련 참조 변수
    private SensorManager sm;
    private Sensor sensor;  // 가속도 센서
    Intent intent = new Intent(MY_ACTION_SENSOR); // 방송과 센서의 가속도 값을 담을 인텐트

    public SensorService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() 메서드 호출 - 센서 관리자 획득");
        sm = (SensorManager) getSystemService(SENSOR_SERVICE); //
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() 메서드 호출 - 가속도 센서 등록");
        sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() 메서드 호출 - 센서 이벤트리스터 등록 해제");
        sm.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d(TAG, "onSensorChanged() 메서드 호출");
        intent.putExtra("acc", event.values); // 가속도 정보를 인텐트에 담는다.
        sendBroadcast(intent);  // 방송한다.
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

