package com.ekndev.gaugelibrary

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.util.AttributeSet

open class HalfGauge : AbstractGauge {
    private val needleStart = 30f
    private val needleEnd = 150f
    private var currentAngle = 30f
    private val startAngle = 210f
    private val sweepAngle = 120f
    private var needleAngleNext: Int? = null
    private val handler = Handler()
    var isEnableBackGroundShadow = true
    var isEnableNeedleShadow = true

    /**
     * Check if animation enable or disable for needle
     *
     * @return boolean value
     */
    var isEnableAnimation = true
        private set
    /**
     * Get current min value color
     * @return [int]
     */
    /**
     * Set Min value text color
     * @param minValueTextColor [int]
     */
    var minValueTextColor = Color.GRAY
    /**
     * Get current max value color
     * @return [int]
     */
    /**
     * Set Max value text color
     * @param maxValueTextColor [int]
     */
    var maxValueTextColor = Color.GRAY
    private val runnable = Runnable { invalidate() }

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
        getGaugeBackGround().strokeWidth = 100f
        //add BG Shadow
        //drawShadow();
        padding = 20f
    }

    private fun drawShadow() {
        if (isEnableBackGroundShadow) {
            getGaugeBackGround().setShadowLayer(15.0f, 0f, 5.0f, 0X50000000)
            setLayerType(LAYER_TYPE_HARDWARE, getGaugeBackGround())
        }
        if (isEnableNeedleShadow) {
            //add Needle Shadow
            needlePaint.setShadowLayer(10f, 0f, 5.0f, 0X50000000)
            setLayerType(LAYER_TYPE_HARDWARE, needlePaint)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (canvas == null) return

        //Add shadow
        drawShadow()
        canvas.save()
        canvas.translate(width / 2f - rectRight / 2f * scaleRatio, height / 2f - 50f * scaleRatio)
        canvas.scale(scaleRatio, scaleRatio)
        canvas.drawArc(rectF!!, startAngle, sweepAngle, false, getGaugeBackGround())
        drawRange(canvas)
        canvas.restore()
        canvas.save()
        canvas.translate(width / 2f - rectRight / 2f * scaleRatio, height / 2f - 50f * scaleRatio)
        canvas.scale(scaleRatio, scaleRatio)
        canvas.rotate(needleAngle.toFloat(), rectRight / 2f, rectBottom / 2f)
        canvas.drawLine(-30f, 400f / 2f, 400f / 2f, 400f / 2f, needlePaint)
        canvas.drawOval(190f, 190f, 210f, 210f, needlePaint)
        canvas.restore()
        canvas.save()
        canvas.translate(width / 2f - rectRight / 2f * scaleRatio, height / 2f - 50f * scaleRatio)
        canvas.scale(scaleRatio, scaleRatio)
        canvas.drawText(formattedValue + "", 200f, 240f, getTextPaint())
        canvas.restore()


        //draw Text Value
        drawValueText(canvas)
        //drawMinValue
        drawMinValue(canvas)
        //drawMaxValue
        drawMaxValue(canvas)

    }

    private fun drawValueText(canvas: Canvas) {
        canvas.save()
        canvas.translate(width / 2f - rectRight / 2f * scaleRatio, height / 2f - 50f * scaleRatio)
        canvas.scale(scaleRatio, scaleRatio)
        canvas.drawText(formattedValue + "", 200f, 240f, getTextPaint())
        canvas.restore()
    }

    private fun drawMinValue(canvas: Canvas) {
        canvas.save()
        canvas.translate(width / 2f - rectRight / 2f * scaleRatio, height / 2f - 50f * scaleRatio)
        canvas.scale(scaleRatio, scaleRatio)
        canvas.rotate(26f, 10f, 130f)
        canvas.drawText(
            getFormattedValue(minValue) + "", 10f + padding, 130f, getRangeValue(
                minValueTextColor
            )
        )
        canvas.restore()
    }

    private fun drawMaxValue(canvas: Canvas) {
        canvas.save()
        canvas.translate(width / 2f - rectRight / 2f * scaleRatio, height / 2f - 50f * scaleRatio)
        canvas.scale(scaleRatio, scaleRatio)
        canvas.rotate(-26f, 390f, 130f)
        canvas.drawText(
            getFormattedValue(maxValue) + "", 390f - padding, 130f, getRangeValue(
                maxValueTextColor
            )
        )
        canvas.restore()
    }

    private fun drawRange(canvas: Canvas) {
        for (range in getRanges()) {
            val startAngle = calculateStartAngle(range.from)
            val sweepAngle = calculateSweepAngle(range.from, range.to)
            canvas.drawArc(rectF!!, startAngle, sweepAngle, false, getRangePaint(range.color))
        }
    }

    private fun calculateStartAngle(from: Double): Float {
        return sweepAngle / 100 * getCalculateValuePercentage(from) + startAngle
    }

    private fun calculateSweepAngle(from: Double, to: Double): Float {
        return sweepAngle / 100 * getCalculateValuePercentage(to) - sweepAngle / 100 * getCalculateValuePercentage(
            from
        ) + 0.5f
    }

    private val needleAngle: Int
        get() {
            if (needleAngleNext != null && isEnableAnimation) {
                if (needleAngleNext!!.toFloat() != currentAngle) {
                    if (needleAngleNext!! < currentAngle) currentAngle-- else currentAngle++
                    handler.postDelayed(runnable, 5)
                }
            } else {
                currentAngle =
                    (needleEnd - needleStart) / 100 * calculateValuePercentage + needleStart
            }
            return currentAngle.toInt()
        }
    override var value: Double
        get() = super.value
        set(value) {
            super.value = value
            needleAngleNext =
                ((needleEnd - needleStart) / 100 * calculateValuePercentage + needleStart).toInt()
        }

    private fun getRangePaint(color: Int): Paint {
        val range = Paint()
        range.color = color
        range.isAntiAlias = true
        range.style = Paint.Style.STROKE
        range.strokeWidth = getGaugeBackGround().strokeWidth
        return range
    }

    private fun getRangeValue(color: Int): Paint {
        val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        textPaint.color = color
        textPaint.style = Paint.Style.FILL
        textPaint.textSize = 15f
        textPaint.textAlign = Paint.Align.CENTER
        return textPaint
    }

    /**
     * Enable or disable animation for needle
     * true will enable animation
     * false will disable animation
     *
     * @param enableAnimation [boolean]
     */
    fun enableAnimation(enableAnimation: Boolean) {
        isEnableAnimation = enableAnimation
    }
}