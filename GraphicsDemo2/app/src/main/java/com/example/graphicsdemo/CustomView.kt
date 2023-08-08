package com.example.graphicsdemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.View

class CustomView : View {
    constructor(context: Context?) : super(context)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //
        val paint = Paint()
        paint.setColor(Color.YELLOW)
        canvas.drawColor(Color.BLUE)
        val rect = RectF(30f, 50f, 300f, 550f)
        canvas.drawRoundRect(rect, 15f, 15f, paint)
        canvas.drawOval(450f, 50f, 750f, 550f, paint)
        // 직선 그리기
        paint.strokeWidth = 10f
        canvas.drawLine(10f, 600f, 800f, 600f, paint)
        canvas.drawLines(floatArrayOf(30f, 650f, 300f, 750f, 300f,
            750f, 60f, 850f, 60f, 850f, 360f, 900f), paint)
    }
}


