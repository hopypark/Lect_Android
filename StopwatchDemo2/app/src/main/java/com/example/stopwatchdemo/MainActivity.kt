package com.example.stopwatchdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Button
import android.widget.Chronometer
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    // 버튼 확인용 변수
    private lateinit var btnStart: Button
    private lateinit var btnStop: Button
    private lateinit var btnReset: Button
    // 이미지뷰 확인용 변수
    private lateinit var ivClock: ImageView
    // 타이머용 변수
    private lateinit var chronometer: Chronometer // Chronometer
    var pauseTime = 0L  // 시작부터 정지까지의 소요된 시간을 저장하는 변수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 화면 리소스와 객체 바인딩
        // Chronometer
        chronometer = findViewById(R.id.chronometer)
        // 디자인 화면의 뷰 가져오기
        btnStart = findViewById(R.id.btn_start)
        btnStop = findViewById(R.id.btn_stop)
        btnReset = findViewById(R.id.btn_reset)
        ivClock = findViewById(R.id.iv_Clock)

        // 버튼별 이벤트 동록하기
        // 시작 버튼을 눌렀을 때의 로직 처리 - 무명클래스를 이용한 처리
        btnStart.setOnClickListener(View.OnClickListener {
            chronometer.base = SystemClock.elapsedRealtime() + pauseTime // 스톱워치의 기준시간 설정
            chronometer.start() //스톱워치 동작
            btnStart.isEnabled = false
            btnStop.isEnabled = true
            btnReset.isEnabled = true
            ivClock.setImageResource(R.drawable.run) // run drawable이미지로 변경
        })
        // 중지 버튼을 눌렀을 때의 로직 처리 - 람다사용1
        btnStop.setOnClickListener({
            pauseTime = chronometer.base - SystemClock.elapsedRealtime()
            chronometer.stop()  // 스톱워치 정지
            btnStart.isEnabled = true
            btnStop.isEnabled = false
            btnReset.isEnabled = true
            ivClock.setImageResource(R.drawable.stop)
        })
        // 람다사용2 - btnReset.setOnClickListener(){ }
        // 재설정 버튼을 눌렀을 때의 로직 처리 - 람다사용3
        btnReset.setOnClickListener{
            pauseTime = 0L
            chronometer.base = SystemClock.elapsedRealtime()
            chronometer.stop()
            btnStart.isEnabled = true
            btnStop.isEnabled = false
            btnReset.isEnabled = false
            ivClock.setImageResource(R.drawable.start)
        }
    }

}
