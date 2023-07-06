package com.example.stopwatchdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    // 버튼 확인용 변수
    private lateinit var btnStart: Button
    private lateinit var btnStop: Button
    private lateinit var btnReset: Button
    // 이미지뷰 확인용 변수
    private lateinit var ivClock: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 디자인 화면의 뷰 가져오기
        btnStart = findViewById(R.id.btn_start)
        btnStop = findViewById(R.id.btn_stop)
        btnReset = findViewById(R.id.btn_reset)

        ivClock.setImageResource(R.drawable.start) // 초기 이미지 설정

        // 버튼별 이벤트 동록하기
        // 시작 버튼을 눌렀을 때의 로직 처리 - 무명클래스를 이용한 처리
        btnStart.setOnClickListener(View.OnClickListener {

        })
        // 중지 버튼을 눌렀을 때의 로직 처리 - 람다사용1
        btnStop.setOnClickListener({

        })
        // 람다사용2 - btnReset.setOnClickListener(){ }
        // 재설정 버튼을 눌렀을 때의 로직 처리 - 람다사용3
        btnReset.setOnClickListener{
 
        }
    }

}
