package com.doodle.doodle.Doodle_Books

/**
 * Created by SAMSUNG on 2018-01-12.
 */

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.LinearLayout

class CarouselLinearLayout : LinearLayout {
    private var scale = CarouselPagerAdapter.BIG_SCALE

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context) : super(context) {}

    fun setScaleBoth(scale: Float) {
        this.scale = scale
        this.invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // The main mechanism to display scale animation, you can customize it as your needs
        val w = this.width
        val h = this.height
        canvas.scale(scale, scale, (w / 2).toFloat(), (h / 2).toFloat())
    }
}