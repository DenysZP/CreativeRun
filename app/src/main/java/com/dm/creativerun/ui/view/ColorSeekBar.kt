package com.dm.creativerun.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.databinding.BindingAdapter
import com.dm.creativerun.R

class ColorSeekBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        @BindingAdapter("onColorSelect")
        @JvmStatic
        fun bindOnColorSelectListener(
            view: ColorSeekBar,
            selectListener: OnColorSelectListener?
        ) {
            view.onColorSelectListener = selectListener
        }
    }

    var onColorSelectListener: OnColorSelectListener? = null

    private val trackHeight: Float
    private val trackCornerRadius: Float
    private val thumbHeight: Float
    private val thumbWidth: Float
    private val thumbPadding: Float
    private val thumbStrokeWidth: Float
    private val thumbStrokeColor: Int
    private val thumbCornerRadius: Float

    private val gradientRect: RectF
    private val colors: IntArray
    private val thumbHalfHeight: Float
    private val thumbHalfWidth: Float
    private val gradientPaint: Paint
    private val thumbFillPaint: Paint
    private val thumbStrokePaint: Paint

    private var trackCenter = 0f
    private var thumbPosition = 0f
    private var clusterWidth = 0f

    init {
        val resources = context.resources
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorSeekBar)

        trackHeight = typedArray.getDimension(
            R.styleable.ColorSeekBar_trackHeight,
            resources.getDimension(R.dimen.csb_default_track_height)
        )
        trackCornerRadius = typedArray.getDimension(
            R.styleable.ColorSeekBar_trackCornerRadius,
            resources.getDimension(R.dimen.csb_default_track_corner_radius)
        )
        thumbHeight = typedArray.getDimension(
            R.styleable.ColorSeekBar_thumbHeight,
            resources.getDimension(R.dimen.csb_default_thumb_height)
        )
        thumbWidth = typedArray.getDimension(
            R.styleable.ColorSeekBar_thumbWidth,
            resources.getDimension(R.dimen.csb_default_thumb_width)
        )
        thumbStrokeWidth = typedArray.getDimension(
            R.styleable.ColorSeekBar_thumbStrokeWidth,
            resources.getDimension(R.dimen.csb_default_thumb_stroke_width)
        )
        thumbStrokeColor = typedArray.getColor(
            R.styleable.ColorSeekBar_thumbStrokeColor,
            Color.WHITE
        )
        thumbCornerRadius = typedArray.getDimension(
            R.styleable.ColorSeekBar_thumbCornerRadius,
            resources.getDimension(R.dimen.csb_default_thumb_corner_radius)
        )
        val useThumbPadding = typedArray.getBoolean(
            R.styleable.ColorSeekBar_useThumbPadding,
            false
        )

        typedArray.recycle()

        minimumHeight = maxOf(trackHeight, thumbHeight).toInt()
        thumbHalfHeight = thumbHeight / 2
        thumbHalfWidth = thumbWidth / 2
        thumbPadding = if (useThumbPadding) thumbHalfWidth else 0f

        gradientRect = RectF(thumbPadding, 0f, 0f, trackHeight)

        colors = intArrayOf(
            Color.RED,
            Color.YELLOW,
            Color.GREEN,
            Color.CYAN,
            Color.BLUE,
            Color.MAGENTA,
            Color.RED
        )
        val gradientShader = LinearGradient(
            gradientRect.left,
            gradientRect.top,
            gradientRect.right,
            gradientRect.bottom,
            colors,
            null,
            Shader.TileMode.CLAMP
        )
        gradientPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        gradientPaint.shader = gradientShader

        thumbFillPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        thumbFillPaint.color = Color.TRANSPARENT
        thumbFillPaint.style = Paint.Style.FILL

        thumbStrokePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        thumbStrokePaint.color = thumbStrokeColor
        thumbStrokePaint.style = Paint.Style.STROKE
        thumbStrokePaint.strokeWidth = thumbStrokeWidth
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        buildGradient(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRoundRect(gradientRect, trackCornerRadius, trackCornerRadius, gradientPaint)

        if (thumbFillPaint.color != Color.TRANSPARENT) {
            val thumbTopPosition = trackCenter - thumbHalfHeight
            val thumbBottomPosition = trackCenter + thumbHalfHeight
            val thumbStartPosition = thumbPosition - thumbHalfWidth
            val thumbEndPosition = thumbPosition + thumbHalfWidth

            canvas.drawRoundRect(
                thumbStartPosition,
                thumbTopPosition,
                thumbEndPosition,
                thumbBottomPosition,
                thumbCornerRadius,
                thumbCornerRadius,
                thumbFillPaint
            )

            canvas.drawRoundRect(
                thumbStartPosition,
                thumbTopPosition,
                thumbEndPosition,
                thumbBottomPosition,
                thumbCornerRadius,
                thumbCornerRadius,
                thumbStrokePaint
            )
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val position = event.x
        return if (clusterWidth > 0 && position in gradientRect.left..gradientRect.right) {
            when (event.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                    thumbPosition = position
                    val fixedPosition = thumbPosition - thumbPadding
                    val blending = calculateBlendingLevel(fixedPosition)
                    val color = getBlendedColor(blending, extractColors(fixedPosition))
                    thumbFillPaint.color = color
                    thumbStrokePaint.color = thumbStrokeColor
                    invalidate()
                    onColorSelectListener?.onColorChange(color)
                    true
                }
                MotionEvent.ACTION_UP -> {
                    onColorSelectListener?.onColorSelect(thumbFillPaint.color)
                    true
                }
                else -> super.onTouchEvent(event)
            }
        } else {
            super.onTouchEvent(event)
        }
    }

    private fun buildGradient(width: Int, height: Int) {
        trackCenter = height / 2f
        val halfTrackHeight = trackHeight / 2f
        gradientRect.top = trackCenter - halfTrackHeight
        gradientRect.right = width - thumbPadding
        gradientRect.bottom = trackCenter + halfTrackHeight
        gradientPaint.shader = LinearGradient(
            gradientRect.left,
            gradientRect.top,
            gradientRect.right,
            gradientRect.bottom,
            colors,
            null,
            Shader.TileMode.CLAMP
        )

        clusterWidth = gradientRect.width() / (colors.size - 1)
    }

    private fun calculateBlendingLevel(position: Float): Float {
        return (position % (clusterWidth)) / clusterWidth
    }

    private fun extractColors(position: Float): Pair<Int, Int> {
        val colorPosition = (position / clusterWidth).toInt()
        return colors[colorPosition] to colors[colorPosition + 1]
    }

    private fun getBlendedColor(blending: Float, colors: Pair<Int, Int>): Int {
        val (firstColor, secondColor) = colors
        val inverseBlending = 1 - blending

        val red = Color.red(firstColor) * inverseBlending + Color.red(secondColor) * blending
        val green = Color.green(firstColor) * inverseBlending + Color.green(secondColor) * blending
        val blue = Color.blue(firstColor) * inverseBlending + Color.blue(secondColor) * blending

        return Color.rgb(red.toInt(), green.toInt(), blue.toInt())
    }

    interface OnColorSelectListener {

        fun onColorChange(color: Int)

        fun onColorSelect(color: Int)
    }
}