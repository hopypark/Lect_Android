package com.example.intentdemo;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // Intent를 받기 위한 activityResultLauncher 객체 생성
    ActivityResultLauncher<Intent> activityResultLauncher;
    TextView textView; // SubActivity로부터 받은 텍스트를 표시하기 위한 텍스트뷰

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tvReturn);
        // "문자열 반환 받기"-버튼 클릭 처리
        Button btnReturn = findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SubActivity.class);
                activityResultLauncher.launch(intent);  //
            }
        });

        // ActivityResultLauncher에 SubActivity로부터 데이터를 받을 때의 처리 내용을 등록
        // 어떻게?  registerForActivityResult(Contract 자료형, 콜백 메서드)
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent intent = result.getData();
                            textView.setText(intent.getStringExtra("INPUT_TEXT"));
                        }
                    }
                }); //
    }


    public void btnClick(View whichView){
        Intent intent = null;
        switch (whichView.getId()){
            case R.id.btnContract:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people/"));
                break;
            case R.id.btnCall:
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:(+82)1012341234"));
                break;
        }
        if (intent != null) startActivity(intent);
    }
}