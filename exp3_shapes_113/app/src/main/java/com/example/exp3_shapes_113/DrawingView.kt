package com.example.exp3_shapes_113

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class DrawingView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint().apply {
        isAntiAlias = true
        strokeWidth = 5f
        style = Paint.Style.FILL
        textSize = 50f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.color = Color.RED
        canvas.drawCircle(200f, 200f, 100f, paint)

        paint.color = Color.BLUE
        canvas.drawRect(100f, 400f, 400f, 700f, paint)


        paint.color = Color.GREEN
        canvas.drawOval(100f, 800f, 400f, 1000f, paint)

        paint.color = Color.WHITE
        canvas.drawLine(100f,1100f,450f,1100f,paint)


        paint.color = Color.BLACK
        canvas.drawText("Hello, 113!", 100f, 1200f, paint)
    }
}
