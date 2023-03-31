/*******************************************************************************
 * Copyright 2018 Evstafiev Konstantin
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ekndev.gaugelibrary

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet

class MultiGauge : FullGauge {
    private val distance = 30f
    override var gaugeBGWidth = 20f
    var secondValue = 0.0
        set(secondValue) {
            field = secondValue
            invalidate()
        }
    var thirdValue = 0.0
        set(thirdValue) {
            field = thirdValue
            invalidate()
        }
    var secondMinValue = 0.0
    var thirdMinValue = 0.0
    var secondMaxValue = 100.0
    var thirdMaxValue = 100.0
    private var secondRanges: MutableList<Range> = ArrayList()
    private var thirdRanges: MutableList<Range> = ArrayList()
    private val secondRect: RectF
        get() = RectF(
            rectLeft + padding + distance,
            rectTop + padding + distance,
            rectRight - padding - distance,
            rectBottom - padding - distance
        )
    private val thirdRect: RectF
        get() = RectF(
            rectLeft + padding + distance * 2,
            rectTop + padding + distance * 2,
            rectRight - padding - distance * 2,
            rectBottom - padding - distance * 2
        )

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

    fun init() {
        getGaugeBackGround().strokeWidth = gaugeBGWidth
        getGaugeBackGround().color = Color.parseColor("#EAEAEA")
        getTextPaint().textSize = 35f
        padding = 20f
        isDrawValueText = false
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //Draw Base Arc's
        drawBaseArc(canvas, secondRect, startAngle, sweepAngle, getGaugeBackGround(secondValue))
        drawBaseArc(canvas, thirdRect, startAngle, sweepAngle, getGaugeBackGround(thirdValue))

        //Draw Value Arc's
        drawValueArcOnCanvas(
            canvas, secondRect, startAngle,
            calculateSweepAngle(secondValue, secondMinValue, secondMaxValue),
            secondValue, getSecondRanges()
        )
        drawValueArcOnCanvas(
            canvas, thirdRect, startAngle,
            calculateSweepAngle(thirdValue, thirdMinValue, thirdMaxValue),
            thirdValue, getThirdRanges()
        )
    }

    override fun drawValuePoint(canvas: Canvas) {
        //TODO : draw value point
    }

    private fun getRangePaint(value: Double, ranges: List<Range>): Paint {
        val color = Paint(Paint.ANTI_ALIAS_FLAG)
        color.strokeWidth = gaugeBGWidth
        color.style = Paint.Style.STROKE
        color.color = getGaugeBackGround().color
        color.strokeCap = Paint.Cap.ROUND
        for (range in ranges) {
            if (range.to <= value) color.color = range.color
            if (range.from <= value && range.to >= value) color.color = range.color
        }
        return color
    }

    fun addSecondRange(range: Range) {
        secondRanges.add(range)
    }

    fun addThirdRange(range: Range) {
        thirdRanges.add(range)
    }

    fun getSecondRanges(): List<Range> {
        return secondRanges
    }

    fun setSeconRanges(secondRanges: MutableList<Range>) {
        this.secondRanges = secondRanges
    }

    fun getThirdRanges(): List<Range> {
        return thirdRanges
    }

    fun setThirdRanges(thirdRanges: MutableList<Range>) {
        this.thirdRanges = thirdRanges
    }
}