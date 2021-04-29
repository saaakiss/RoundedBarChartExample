package com.example.roundedbarchartexample

import android.graphics.Canvas
import com.github.mikephil.charting.animation.ChartAnimator
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.renderer.BarChartRenderer
import com.github.mikephil.charting.utils.ViewPortHandler

const val MAX_ALPHA = 255
const val MIDDLE_ALPHA = 127

class RoundedBarChartRenderer(
    chart: BarDataProvider,
    animator: ChartAnimator,
    viewPortHandler: ViewPortHandler,
    private val mRadius: Int
) : BarChartRenderer(chart, animator, viewPortHandler) {

    override fun drawDataSet(c: Canvas, dataSet: IBarDataSet, index: Int) {
        val trans = mChart.getTransformer(dataSet.axisDependency)

        // initialize the buffer
        val buffer = mBarBuffers[index]
        buffer.setBarWidth(mChart.barData.barWidth)
        buffer.feed(dataSet)
        trans.pointValuesToPixel(buffer.buffer)

        mRenderPaint.color = dataSet.color

        var j = 0
        while (j < buffer.size()) {
            if (!mViewPortHandler.isInBoundsLeft(buffer.buffer[j + 2])) {
                j += 4
                continue
            }
            if (!mViewPortHandler.isInBoundsRight(buffer.buffer[j])) break

            val selectedEntry = dataSet.getEntryForIndex(j / 4)

            if (index == 0 && selectedEntry.y == MIN_EXPENSES) {
                mRenderPaint.alpha = MIDDLE_ALPHA
                c.drawCircle(
                    buffer.buffer[j] + (buffer.buffer[j + 2] - buffer.buffer[j]) / 2,
                    buffer.buffer[j + 3] - (buffer.buffer[j + 2] - buffer.buffer[j]) / 2,
                    (buffer.buffer[j + 2] - buffer.buffer[j]) / 2,
                    mRenderPaint
                )
            } else if (index == 0) {
                mRenderPaint.alpha = MAX_ALPHA
                c.drawRoundRect(
                    buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2],
                    buffer.buffer[j + 3], mRadius.toFloat(), mRadius.toFloat(), mRenderPaint
                )
            } else if (index == 1 && selectedEntry.y > MIN_EXPENSES) {
                c.drawRoundRect(
                    buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2],
                    buffer.buffer[j + 3], mRadius.toFloat(), mRadius.toFloat(), mRenderPaint
                )
                c.drawRect(
                    buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2],
                    buffer.buffer[j + 3] - mRadius.toFloat(), mRenderPaint
                )
            }

            j += 4
        }

    }
}