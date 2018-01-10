package com.doodle.doodle.Doodle_Me

import android.content.Context
import android.support.v4.view.MotionEventCompat
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * Created by Jinyoung on 2018-01-06.
 */
class mySwipeViewPager:ViewPager {
    private var status : Boolean = false
    private val mCurrentPagePosition = 0
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}
    //스와이프 동작 고정해놓음
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (status) {
            return super.onInterceptTouchEvent(ev)
        } else {
            if (MotionEventCompat.getActionMasked(ev) == MotionEvent.ACTION_MOVE) {

            } else {
                if (super.onInterceptTouchEvent(ev)) {
                    super.onTouchEvent(ev)
                }
            }
            return false
        }
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return if (status) {
            super.onTouchEvent(ev)
        } else {
            MotionEventCompat.getActionMasked(ev) != MotionEvent.ACTION_MOVE && super.onTouchEvent(ev)
        }
    }
//추가
//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        try {
//            val child = getChildAt(mCurrentPagePosition)
//            if (child != null) {
//                child.measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
//                val h = child.measuredHeight
//
//                heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(h, View.MeasureSpec.EXACTLY)
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//    }
    //추가 끝

    fun setPagingEnabled(enabled: Boolean) {
        this.status = enabled
    }
}