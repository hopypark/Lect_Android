package com.example.graphicsdemo

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.View

class BounceBall:View {

    var m_Drawable: ShapeDrawable

    var x = 50
    var y = 50
    val sheight = 100
    val swidth = 100
    var cx = 0
    var cy = 0
    var xdir = 1
    var ydir = 1
    val dx = 10
    val dy = 10
    //
    val screen_width = Resources.getSystem().displayMetrics.widthPixels
    val screen_height = Resources.getSystem().displayMetrics.heightPixels

    constructor(context: Context?) : super(context){
        m_Drawable = ShapeDrawable(OvalShape())
        m_Drawable.paint.setColor(Color.RED)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        cx = x + swidth / 2; cy = y + sheight / 2
        // x방향 이동시 방향 결정
        if ( cx <= swidth/2 ) xdir = 1
        else if (cx >=screen_width - swidth/2) xdir = -1
        // y방향 이동시 방향 결정
        if ( cy <= sheight/2 ) ydir = 1
        else if (cy >= screen_height - sheight/2) ydir = -1

        x = x + xdir * dx
        y = y + ydir * dy
        m_Drawable.setBounds(x, y, x + swidth, y + sheight)
        m_Drawable.draw(canvas!!)
        invalidate()
    }
}