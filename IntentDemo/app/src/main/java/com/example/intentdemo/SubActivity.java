package com.example.intentdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SubActivity extends AppCompatActivity {
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        editText = findViewById(R.id.editTextInputBox);
        // 입력버튼
        Button btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("INPUT_TEXT", editText.getText().toString());
                setResult(RESULT_OK, intent);
                finish();   // 현재 Activicity를 닫는다.
            }
        });
        // 취소버튼
        Button btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SubActivity.this, "입력을 취소하였습니다.", Toast.LENGTH_SHORT).show();
                finish(); // 현재 Activity를 닫는다.
            }
        });
    }
}