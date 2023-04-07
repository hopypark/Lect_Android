package com.example.graphicsdemo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomView extends View {


    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();  // 펜 생성
        paint.setColor(Color.YELLOW);   // 펜색상
        canvas.drawColor(Color.BLUE);   // 캔버스 배경은 파란색
        canvas.drawRoundRect(30,50, 330, 550, 15, 15, paint); // 둥근 꼭지점 사각형
        canvas.drawOval(450, 50, 750, 550, paint);
        // 직선 그리기
        float[] points = {30, 650, 300, 750, 300, 750, 60, 850, 60, 850, 360, 900}; //
        paint.setStrokeWidth(10); // 펜(선) 굵기
        canvas.drawLines(points, paint);    // 선 그리기

    }
}
