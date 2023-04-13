package com.ekndev.gaugelibrary

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.ekndev.gaugelibrary.contract.ValueFormatter
import kotlin.math.abs

abstract class AbstractGauge : View {
    private var ranges: MutableList<Range> = ArrayList()
    open var value = 0.0
        set(value) {
            field = value
            invalidate()
        }
    var minValue = 0.0
    var maxValue = 100.0
    private var needleColor: Paint? = null
    var gaugeBackGroundPaint: Paint? = null
    private var gaugeBackgroundColor = Color.parseColor("#00658D")
    var textPaints: Paint? = null
    protected var rectTop = 0f
    protected var rectLeft = 0f
    protected var rectRight = 400f
    protected var rectBottom = 440f
    var padding = 0f
    protected var rectF: RectF? = null
        get() {
            if (field == null) field = RectF(
                rectLeft + padding,
                rectTop + padding*3.5f,
                rectRight - padding,
                rectBottom - 0f
            )
            return field
        }
        private set
    private var isUseRangeBGColor = false
    private var formatter: ValueFormatter = ValueFormatterImpl()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        scaleRatio
    }

    protected val scaleRatio: Float
        get() {
            val measuredHeight = measuredHeight
            val measuredWidth = measuredWidth
            val minSize = measuredHeight.coerceAtMost(measuredWidth) / 1f
            val maxSize = measuredHeight.coerceAtLeast(measuredWidth) / 1f
            val f1 = minSize / 300f
            val f2 = minSize / 200f
            if (measuredWidth > measuredHeight) {
                if (f2 > f1) return f1
            } else {
                return minSize / 400f
            }
            return maxSize / 400f
        }

    fun addRange(range: Range?) {
        if (range == null) return
        ranges.add(range)
    }

    fun getRanges(): List<Range> {
        return ranges
    }

    fun setRanges(ranges: MutableList<Range>) {
        this.ranges = ranges
    }

    // needleColor.setShadowLayer(10.f,0f,5.0f,0X50000000);
    // setLayerType(LAYER_TYPE_SOFTWARE, needleColor);
    protected val needlePaint: Paint
        get() {
            if (needleColor == null) {
                needleColor = Paint()
                needleColor!!.color = Color.BLACK
                needleColor!!.isAntiAlias = true
                needleColor!!.style = Paint.Style.FILL_AND_STROKE
                needleColor!!.strokeWidth = 5f
                 //needleColor!!.setShadowLayer(10f,0f,5.0f,0X50000000);
                 //setLayerType(LAYER_TYPE_SOFTWARE, needleColor);
            }
            return needleColor!!
        }

    fun getGaugeBackGround(): Paint {
        if (gaugeBackGroundPaint == null) {
            gaugeBackGroundPaint = Paint()
            gaugeBackGroundPaint!!.color = gaugeBackgroundColor
            gaugeBackGroundPaint!!.isAntiAlias = true
            gaugeBackGroundPaint!!.style = Paint.Style.STROKE
            //gaugeBackGroundPaint!!.setShadowLayer(15.0f,0f,5.0f,0X50000000);
            //setLayerType(LAYER_TYPE_SOFTWARE, gaugeBackGroundPaint);
        }
        return gaugeBackGroundPaint!!
    }

    protected fun getGaugeBackGround(value: Double): Paint {
        if (isUseRangeBGColor) {
            getGaugeBackGround().color = getRangeColorForValue(value)
            getGaugeBackGround().alpha = 20
        }
        return getGaugeBackGround()
    }

    private fun getRangeColorForValue(value: Double): Int {
        return getRangeColorForValue(value, ranges)
    }

    protected fun getRangeColorForValue(value: Double, ranges: List<Range>): Int {
        var color = Color.GRAY
        for (range in ranges) {
            if (range.to <= value) color = range.color
            if (range.from <= value && range.to >= value) color = range.color
        }
        return color
    }

    protected val calculateValuePercentage: Int
        get() = getCalculateValuePercentage(value)

    protected fun getCalculateValuePercentage(value: Double): Int {
        return getCalculateValuePercentage(minValue, maxValue, value)
    }

    private fun getCalculateValuePercentageOld(min: Double, max: Double, value: Double): Int {
        if (min >= value) return 0
        return if (max <= value) 100 else ((value - min) / (max - min) * 100).toInt()
    }

    protected fun getCalculateValuePercentage(min: Double, max: Double, value: Double): Int {
        if (min < 0 && max < 0 && min < max) {
            return getCalculateValuePercentageUseCaseOne(min, max, value)
        } else if (min < 0 && max < 0 && min > max) {
            return getCalculateValuePercentageUseCaseTwo(min, max, value)
        } else if (min >= 0 && max < 0 || min < 0 && max >= 0) {
            if (min > max) {
                return getCalculateValuePercentageUseCaseThree(min, max, value)
            } else if (min < max) {
                return getCalculateValuePercentageUseCaseFoure(min, max, value)
            }
        }
        return getCalculateValuePercentageOld(min, max, value)
    }

    /**
     * Use case when min and max negative
     * and min smaller than max
     */
    private fun getCalculateValuePercentageUseCaseOne(
        min: Double,
        max: Double,
        value: Double
    ): Int {
        if (value <= min.coerceAtMost(max)) return 0
        return if (value >= min.coerceAtLeast(max)) 100 else {
            val available = abs(
                min.coerceAtMost(max)
            ) - abs(min.coerceAtLeast(max))
            val minValue = min.coerceAtMost(max)
            val result = abs((minValue - value) / available * 100)
            result.toInt()
        }
    }

    /**
     * Use Case when min and max negative
     * and min bigger than max
     */
    private fun getCalculateValuePercentageUseCaseTwo(
        min: Double,
        max: Double,
        value: Double
    ): Int {
        if (value <= min.coerceAtMost(max)) return 100
        return if (value >= min.coerceAtLeast(max)) 0 else {
            val available = abs(
                min.coerceAtMost(max)
            ) - abs(min.coerceAtLeast(max))
            val maxValue = min.coerceAtLeast(max)
            val result = abs((maxValue - value) / available * 100)
            result.toInt()
        }
    }
    //TODO: Need improvements for calculation algorithms for next to methods
    /**
     * Use case when one of the limits are negative and one is positive
     * TODO: Need Improvements
     */
    private fun getCalculateValuePercentageUseCaseThree(
        min: Double,
        max: Double,
        value: Double
    ): Int {
        val available = abs(min) + abs(max)
        return if (value <= min.coerceAtMost(max)) {
            100
        } else if (value >= min.coerceAtLeast(max)) 0 else {
            val positive = min.coerceAtLeast(max)
            val result = abs((positive - value) / available * 100)
            result.toInt()
        }
    }

    /**
     * Use case when one of the limits are negative and one is positive
     * and max is bigger than min
     * TODO: Need Improvements
     */
    private fun getCalculateValuePercentageUseCaseFoure(
        min: Double,
        max: Double,
        value: Double
    ): Int {
        val available = abs(min) + abs(max)
        return if (value <= min.coerceAtMost(max)) {
            0
        } else if (value >= min.coerceAtLeast(max)) 100 else {
            val negative = abs(min.coerceAtMost(max))
            val result = abs((negative + value) / available * 100)
            result.toInt()
        }
    }
    /**
     * Get Current Value Color
     *
     * @return [int]
     */
    /**
     * Set Value Color
     *
     * @param color [Integer]
     */
    var valueColor: Int
        get() = getTextPaint().color
        set(color) {
            getTextPaint().color = color
        }

    protected fun getTextPaint(): Paint {
        if (textPaints == null) {
            textPaints = Paint(Paint.ANTI_ALIAS_FLAG)
            textPaints!!.color = Color.BLACK
            textPaints!!.style = Paint.Style.FILL
            textPaints!!.textSize = 25f
            textPaints!!.textAlign = Paint.Align.CENTER
        }
        return textPaints!!
    }

    protected fun getFormattedValue(value: Double): String {
        return formatter.getFormattedValue(value.toInt())
            ?: return ValueFormatterImpl().getFormattedValue(value.toInt())
    }

    protected val formattedValue: String?
        get() = getFormattedValue(value)

    /**
     * Set Value Formatter
     * @param formatter [ValueFormatter]
     */
    fun setFormatter(formatter: ValueFormatter) {
        this.formatter = formatter
    }

    fun setNeedleColor(color: Int) {
        needlePaint.color = color
    }

    fun setGaugeBackGroundColor(color: Int) {
        gaugeBackGroundPaint!!.color = color
        gaugeBackgroundColor = color
    }
}