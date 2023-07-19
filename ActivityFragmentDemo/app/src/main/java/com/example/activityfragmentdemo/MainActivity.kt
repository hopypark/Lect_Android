package com.example.activityfragmentdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        settingButtons()
    }

    private fun settingButtons() {
        val redButton = findViewById<Button>(R.id.btn_red_fragment)
        val blueButton = findViewById<Button>(R.id.btn_blue_fragment)

        redButton.setOnClickListener{
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout, RedFragment())
            fragmentTransaction.commit()
        }

        blueButton.setOnClickListener{
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout, RedFragment())
            fragmentTransaction.commit()
        }
    }
}

