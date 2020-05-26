package nb.visualtimer.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View

class SquareView @JvmOverloads constructor(context: Context, attrs: AttributeSet ?= null, defStyleAttr: Int=0)
    : View(context, attrs, defStyleAttr) {

    private var percentProgress : Float = 0F
    var xElements : Int = 6
    var yElements : Int = 6
    private val linePaint =
        Paint().apply {
            isAntiAlias = true
            color = Color.RED
            style = Paint.Style.STROKE
            strokeWidth = 10F
        }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawLine(0.0F, 10F, width - ((percentProgress * width) / 100F), 10F, linePaint)

    }

    fun setPercentProgress(progress : Float) {
        percentProgress = progress
        invalidate()
    }
}