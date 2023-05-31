package com.example.widgetdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // GridView에서 사용할 이미지 데이터 정보(ID)
    final Integer[] mThumbIds={
            R.drawable.image_01, R.drawable.image_02, R.drawable.image_03, R.drawable.image_04,
            R.drawable.image_05, R.drawable.image_06, R.drawable.image_07, R.drawable.image_08,
            R.drawable.image_09, R.drawable.image_10, R.drawable.image_11, R.drawable.image_12,
            R.drawable.image_13, R.drawable.image_14, R.drawable.image_15, R.drawable.image_16
    };

    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridHero);
        gridView.setAdapter(new ImageAdapter(this, mThumbIds));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), i+"번째 이미지를 선택", Toast.LENGTH_SHORT).show();
            }
        });
    }   // onCreate() 끝
}


