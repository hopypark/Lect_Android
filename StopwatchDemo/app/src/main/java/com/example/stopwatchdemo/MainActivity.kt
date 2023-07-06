package com.example.stopwatchdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import java.util.Timer
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    var isRunning: Boolean = false // 실행여부를 확인하기 위한 변수
    var timer: Timer? = null        // timer 변수 추가
    var time = 0                    // time 변수 추가

    // 버튼 확인용 변수
    private lateinit var btnStart: Button
    private lateinit var btnStop: Button
    private lateinit var btnReset: Button
    // 텍스트뷰 확인용 변수
    private lateinit var tvMinute:TextView
    private lateinit var tvSecond:TextView
    private lateinit var tvMillisecond:TextView
    // 이미지뷰 확인용 변수
    private lateinit var ivClock: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 디자인 화면의 뷰 가져오기
        btnStart = findViewById(R.id.btn_start)
        btnStop = findViewById(R.id.btn_stop)
        btnReset = findViewById(R.id.btn_reset)
        // 시간 표시 텍스트뷰 가져오기
        tvMinute = findViewById(R.id.tv_minute)
        tvSecond = findViewById(R.id.tv_second)
        tvMillisecond = findViewById(R.id.tv_millisecond)
        // 이미지뷰 가져오기
        ivClock = findViewById(R.id.iv_Clock)
        ivClock.setImageResource(R.drawable.start) // 초기 이미지 설정

        // 버튼별 이벤트 동록하기
        // 시작 버튼을 눌렀을 때의 로직 처리 - 무명클래스를 이용한 처리
        btnStart.setOnClickListener(View.OnClickListener {
            isRunning = true
            // 버튼 및 이미지 처리
            btnStart.isEnabled = false
            btnStop.isEnabled = true
            ivClock.setImageResource(R.drawable.run) // 타이머가 동작 중이라는 이미지 출력
            // 스톱워치 동작 로직
            timer = timer(period = 10){ // 10 millisec 단위로 Task 동작
                time++   // 10 millisec 단위 타이머
                // 시간 계산
                val milliSecond = time % 100
                val second = (time % 6000) / 100
                val minute = time / 6000
                runOnUiThread {         // 1. UI 스레드 생성
                    if(isRunning) {     // 2. UI 업데이트 조건 설정
                        // 밀리초(millisecond)
                        tvMillisecond.text = if (milliSecond < 10) ".0${milliSecond}" else ".${milliSecond}"
                        // 초(second)
                        tvSecond.text = if (second < 10) ":0${second}" else ":${second}"
                        // 분(minute)
                        tvMinute.text = if (minute < 10) "0${minute}" else "${minute}"
                    }
                }
            }
        })
        // 중지 버튼을 눌렀을 때의 로직 처리 - 람다사용1
        btnStop.setOnClickListener({
            btnStart.isEnabled = true
            btnStop.isEnabled = false
            btnReset.isEnabled = true
            ivClock.setImageResource(R.drawable.stop) // 타이머가 동작 중이라는 이미지 출력
            isRunning = false
            timer?.cancel()
        })
        // 람다사용2 - btnReset.setOnClickListener(){ }
        // 재설정 버튼을 눌렀을 때의 로직 처리 - 람다사용3
        btnReset.setOnClickListener{
            btnStart.isEnabled = true
            btnReset.isEnabled = false
            tvMinute.text = "00"
            tvSecond.text = ":00"
            tvMillisecond.text = ".00"
            ivClock.setImageResource(R.drawable.start) // 타이머가 시작 준비 중이라는 이미지 출력
            isRunning = false;
            time = 0;
        }
    }

}
