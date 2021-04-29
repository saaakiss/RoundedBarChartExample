package com.example.roundedbarchartexample

import android.content.Context
import android.util.AttributeSet
import com.github.mikephil.charting.charts.BarChart

class RoundedBarChart : BarChart {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        readRadiusAttr(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        readRadiusAttr(context, attrs)
    }

    private fun readRadiusAttr(context: Context, attrs: AttributeSet) {
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.RoundedBarChart, 0, 0)
        try {
            setRadius(a.getDimensionPixelSize(R.styleable.RoundedBarChart_radius, 0))
        } finally {
            a.recycle()
        }
    }

    private fun setRadius(radius: Int) {
        renderer = RoundedBarChartRenderer(
            this,
            animator, viewPortHandler, radius
        )
    }
}