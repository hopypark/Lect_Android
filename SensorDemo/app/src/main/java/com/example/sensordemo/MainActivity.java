package com.example.sensordemo;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    ImageView imageView;    // smile.png 이미지 사용
    float iv_width, iv_height = 0f; // 이미지의 가로,세로 크기
    float x, y; // 이미지의 기준점(왼쪽 상단)이 이동해야할 위치
    // 근접 센서에서 사용할 변수
    TextView tv_distance;   // 센서의 거리 정보를 표시할 텍스트뷰 객체
    SensorManager sm;   // 센서 관리자 객체
    Sensor sensor_proximity; // 근접 센서용 객체
    Sensor sensor_accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 상단의 정보창도 제거하자(배터리, 안테나 정보창)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){ // API >= 30(Android 11, R이상)
            WindowInsetsController inset = getWindow().getInsetsController();
            if (inset != null) inset.hide(WindowInsets.Type.statusBars());
        }else{ // Android API < 30인 경우
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, // 쟈
                                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        // 이미지 뷰 처리
        imageView = findViewById(R.id.img_smile);
        imageView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        iv_width = imageView.getMeasuredWidth();
        iv_height = imageView.getMeasuredHeight();
        // 센서 관련 처리
        tv_distance = findViewById(R.id.tv_distance);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor_proximity = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensor_accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); //센서 확인
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);    // 더 이상 센서이벤트 처리를 하지 않겠다.
    }

    @Override
    protected void onResume() {
        super.onResume();
        //
        sm.registerListener(this, sensor_proximity, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this, sensor_accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_PROXIMITY){
            tv_distance.setText("거리: " + event.values[0]);
        } else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            Log.d("MainActivity", "x-axis: " +event.values[0] + ", y-axis: "
                    + event.values[1] + ", z-axis: " + event.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                // 터치한 위치(x, y)
                int touch_x = (int) event.getX();
                int touch_y = (int) event.getY();
                // 이미지 중심이 터치한 곳으로 이동하도록 좌표 재계산
                x = touch_x - iv_width / 2;
                y = touch_y - iv_height / 2;
                // 이동
                imageView.setX(x);
                imageView.setY(y);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return false;
    }
}


