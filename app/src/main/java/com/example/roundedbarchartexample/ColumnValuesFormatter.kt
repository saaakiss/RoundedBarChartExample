package com.example.roundedbarchartexample

import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.DecimalFormat

/**
 * Format column values according to a pattern
 */
class ColumnValuesFormatter() : ValueFormatter() {

    private var mFormat: DecimalFormat = DecimalFormat("###,###,###,##0.0")

    override fun getFormattedValue(value: Float): String {

        return if (value == 0f) "" else {
            "$" + mFormat.format(value)
        }
    }

}