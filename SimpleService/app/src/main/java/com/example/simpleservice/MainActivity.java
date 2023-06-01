package com.example.simpleservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        Button button = findViewById(R.id.btn_Send);
        button.setOnClickListener(new View.OnClickListener() { // 버튼 클릭이벤트리스터
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                // 인텐트 생성
                Intent intent = new Intent(getApplicationContext(), MyService.class);
                intent.putExtra("command", "show");
                intent.putExtra("name", name);
                startService(intent);   // 서비스 시작
            }
        });

    }

}

