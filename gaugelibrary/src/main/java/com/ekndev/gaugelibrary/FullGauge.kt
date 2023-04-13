package com.ekndev.gaugelibrary

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet

open class FullGauge : AbstractGauge {
    protected open var sweepAngle = 360f
    protected open var startAngle = 270f
    protected open var gaugeBGWidth = 20f
    var isDisplayValuePoint = false
    protected var isDrawValueText = true

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
        getGaugeBackGround().strokeWidth = gaugeBGWidth
        getTextPaint().textSize = 35f
        padding = 20f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        //Draw Base Arc to display visual range
        drawBaseArc(canvas)

        //Draw Value Arc to display Value range
        drawValueArcOnCanvas(canvas)

        //drawText
        drawValueText(canvas)

        //draw value  point indicator
        drawValuePoint(canvas)
    }

    private fun drawBaseArc(canvas: Canvas) {
        drawBaseArc(canvas, rectF, startAngle, sweepAngle, getGaugeBackGround(value))
    }

    protected fun drawBaseArc(
        canvas: Canvas,
        rectF: RectF?,
        startAngle: Float,
        sweepAngle: Float,
        paint: Paint?
    ) {
        prepareCanvas(canvas)
        canvas.drawArc(rectF!!, startAngle, sweepAngle, false, paint!!)
        finishCanvas(canvas)
    }

    protected open fun drawValuePoint(canvas: Canvas) {
        if (isDisplayValuePoint) {
            prepareCanvas(canvas)
            //draw Value point indicator
            val rotateValue = calculateSweepAngle(value, minValue, maxValue)
            canvas.rotate(rotateValue, rectRight / 2f, rectBottom / 2f)
            canvas.drawCircle(400f / 2f, padding, 8f, getRangePaintForValue(value, getRanges()))
            canvas.drawLine(200f - 3f, 11f, 210f - 4f, 19f, arrowPaint)
            canvas.drawLine(210f - 4f, 20f, 200f - 3f, 27f, arrowPaint)
            finishCanvas(canvas)
        }
    }

    private val arrowPaint: Paint
        get() {
            val color = Paint(Paint.ANTI_ALIAS_FLAG)
            color.strokeWidth = 4f
            color.style = Paint.Style.STROKE
            color.color = Color.WHITE
            color.strokeCap = Paint.Cap.ROUND
            return color
        }

    protected fun prepareCanvas(canvas: Canvas) {
        canvas.save()
        canvas.translate(width / 2f - rectRight / 2f * scaleRatio, height / 2f - 200f * scaleRatio)
        canvas.scale(scaleRatio, scaleRatio)
    }

    protected fun finishCanvas(canvas: Canvas) {
        canvas.restore()
    }

    private fun drawValueText(canvas: Canvas) {
        if (isDrawValueText) {
            canvas.save()
            canvas.translate(
                width / 2f - rectRight / 2f * scaleRatio,
                height / 2f - 220f * scaleRatio
            )
            canvas.scale(scaleRatio, scaleRatio)
            canvas.drawText(formattedValue + "", 200f, 240f, getTextPaint())
            canvas.restore()
        }
    }

    protected fun getRangePaintForValue(value: Double, ranges: List<Range>): Paint {
        val color = Paint(Paint.ANTI_ALIAS_FLAG)
        color.strokeWidth = gaugeBGWidth
        color.style = Paint.Style.STROKE
        //color.setColor(getGaugeBackGround().getColor());
        color.strokeCap = Paint.Cap.ROUND
        color.color = getRangeColorForValue(value, ranges)
        return color
    }

    private fun drawValueArcOnCanvas(canvas: Canvas) {
        val sweepAngle = calculateSweepAngle(value, minValue, maxValue)
        drawValueArcOnCanvas(canvas, rectF, startAngle, sweepAngle, value, getRanges())
    }

    protected fun drawValueArcOnCanvas(
        canvas: Canvas,
        rectF: RectF?,
        startAngle: Float,
        sweepAngle: Float,
        value: Double,
        ranges: List<Range>
    ) {
        prepareCanvas(canvas)
        canvas.drawArc(rectF!!, startAngle, sweepAngle, false, getRangePaintForValue(value, ranges))
        finishCanvas(canvas)
    }

    protected fun calculateSweepAngle(to: Double, min: Double, max: Double): Float {
        val valuePer = getCalculateValuePercentage(min, max, to).toFloat()
        return sweepAngle / 100 * valuePer
    }
}