package com.example.roundedbarchartexample

import com.github.mikephil.charting.formatter.ValueFormatter

/**
 * Maps values from 1 to 12 to Months from Jan to Dec
 */
class MonthAxisValueFormatter() : ValueFormatter() {

    private val mMonths = arrayOf(
        "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    )

    override fun getFormattedValue(value: Float): String {
        val selectedMonth = value.toInt() - 1
        return mMonths[selectedMonth]
    }

}