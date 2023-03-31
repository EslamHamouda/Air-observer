package com.ekndev.gaugelibrary

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet

class ArcGauge : FullGauge {
    override var sweepAngle = 240f
    override var startAngle = 150f
    override var gaugeBGWidth = 25f

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    private fun init() {
        gaugeBackGroundPaint?.strokeWidth = gaugeBGWidth
        gaugeBackGroundPaint?.strokeCap = Paint.Cap.ROUND
        gaugeBackGroundPaint?.color = Color.parseColor("#D6D6D6")
        textPaints?.textSize = 35f
        padding = 20f
    }

    override fun drawValuePoint(canvas: Canvas) {
        //no point
    }
}