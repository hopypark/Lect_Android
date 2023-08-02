package com.example.viewbindingsample

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.viewbindingsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater) // 바인딩 클래스의 객체 생성

        val rootView = binding.root;    // 바인딩 객체의 루트뷰 참조(ConstraintLayout)
        setContentView(rootView)        // 생성한 뷰 설정

        binding.bindingButton.setOnClickListener {
            // do something...
        }


    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun showToast() {
        val toast = Toast.makeText(applicationContext, "text", Toast.LENGTH_SHORT)
        toast.show()
        toast.addCallback(object : Toast.Callback() {})
    }
}

