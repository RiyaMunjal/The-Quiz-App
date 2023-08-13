package com.example.drawingapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View


class DrawingView(context: Context, attrs: AttributeSet): View(context,attrs) {
    private var mDrawpath:CustomPath?=null
    private var mCanvasBitmap: Bitmap?=null
    private var mDrawPaint: Paint?=null
    private var mCanvasPaint:Paint?=null
    private var color=Color.BLACK
    private var mBrushSize=0. toFloat()
    private var canvas : Canvas?=null

    init{
        setDrawingVar()
    }

    private fun setDrawingVar() {

        mDrawPaint=Paint()
        mDrawPaint!!.color=color
        mDrawPaint!!.style=Paint.Style.STROKE
        mDrawPaint!!.strokeCap=Paint.Cap.ROUND
        mDrawPaint!!.strokeJoin=Paint.Join.ROUND
        mCanvasPaint=Paint(Paint.DITHER_FLAG)
        mBrushSize=20.toFloat()

    }

    internal inner class CustomPath(var color:Int, var Brushsize: Float): Path() {


    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mCanvasBitmap=Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888)
        canvas=Canvas(mCanvasBitmap!!)

    }

    override fun onDraw(canvas: Canvas?){
        super.onDraw(canvas)
        canvas?.drawBitmap(mCanvasBitmap!!, 0F, 0F, mCanvasPaint)
        if(!mDrawpath!!.isEmpty){
            mDrawPaint!!.color=mDrawpath!!.color
            mDrawPaint!!.strokeWidth=mDrawpath!!.Brushsize
            canvas?.drawPath(mDrawpath!!, mDrawPaint!!)
        }
    }

//    @SuppressLint("ClickableViewAccessibility")

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        val touchx=event?.x
        val touchy=event?.y
        when(event?.action){
            MotionEvent.ACTION_DOWN->{
                mDrawpath?.color=color
                mDrawpath?.Brushsize=mBrushSize
                mDrawpath!!.reset()
                if(touchx!=null){
                    if(touchy!=null){
                        mDrawpath?.moveTo(touchx, touchy)
                    }
                }

            }
            MotionEvent.ACTION_MOVE->{
                if(touchx!=null){
                    if(touchy!=null) {
                        mDrawpath?.lineTo(touchx, touchy)
                    }}

            }
            MotionEvent.ACTION_UP->{
                mDrawpath=CustomPath(color, mBrushSize)
            }
            else->return false
        }
        invalidate()
        return true

    }




}