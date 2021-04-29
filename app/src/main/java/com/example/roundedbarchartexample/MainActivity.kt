package com.example.roundedbarchartexample

import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet


const val NUMBER_OF_MONTHS = 12
const val MAX_EXPENSES = 100f
const val MIN_EXPENSES = 0f
const val THRESHOLD = 20f
const val COLUMN_WIDTH = 0.55f

class MainActivity : AppCompatActivity() {

    private lateinit var chart: BarChart

    // xAxis values each one represents a month
    private val xAxisValues = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)

    // yAxis values monthly expenses (each one corresponds to a month)
    private val yAxisValues = listOf(50f, 80f, 40f, 0f, 55f, 77f, 88f, 99f, 30f, 39f, 76f, 59f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chart = findViewById(R.id.chart1)

        initializeBarChart()

        setChartData()
    }

    private fun initializeBarChart() {
        // disable description label from the bottom right corner
        chart.description.isEnabled = false

        // disable zoom across x and y axis
        chart.setScaleEnabled(false)

        // setup xAxis
        val xAxis = chart.xAxis
        // position xAxis at the bottom
        xAxis.position = XAxisPosition.BOTTOM
        // disable vertical grid lines
        xAxis.setDrawGridLines(false)
        // set the number of xAxis values
        xAxis.labelCount = NUMBER_OF_MONTHS
        // map values to months
        xAxis.valueFormatter = MonthAxisValueFormatter()
        // disable xAxis line
        xAxis.setDrawAxisLine(false)

        // set up left yAxis
        val leftAxis = chart.axisLeft
        // set max and min values for yAxis
        leftAxis.axisMaximum = MAX_EXPENSES
        leftAxis.axisMinimum = MIN_EXPENSES
        // disable left yAxis
        leftAxis.isEnabled = false

        // disable right yAxis
        chart.axisRight.isEnabled = false

        // disable chart legend
        chart.legend.form = LegendForm.NONE
    }

    private fun setChartData() {

        // init expenses values list
        val valuesList = mutableListOf<BarEntry>()
        for (i in xAxisValues) {
            val y = yAxisValues[i - 1]
            valuesList.add(BarEntry(i.toFloat(), y))
        }
        // init expenses Bar Data Set
        val expensesSet = BarDataSet(valuesList, "")
        // disable highlight column
        expensesSet.highLightAlpha = 0
        // set color of column
        expensesSet.color = Color.RED

        // init threshold values list
        val thresholdList = mutableListOf<BarEntry>()
        for (i in xAxisValues) {
            val y = if (yAxisValues[i - 1] > MIN_EXPENSES) THRESHOLD else MIN_EXPENSES
            thresholdList.add(BarEntry(i.toFloat(), y))
        }
        // init threshold Bar Data Set
        val thresholdSet = BarDataSet(thresholdList, "")
        // disable highlight column
        thresholdSet.highLightAlpha = 0
        // disable drawing values on threshold columns
        thresholdSet.setDrawValues(false)
        // set color of column
        thresholdSet.color = Color.GREEN

        // add Bar Data Sets to a list
        val expensesDataSets = mutableListOf<IBarDataSet>()
        expensesDataSets.add(expensesSet)
        expensesDataSets.add(thresholdSet)

        // create Bar Data
        val data = BarData(expensesDataSets)
        // set text size of column values
        data.setValueTextSize(resources.getDimension(R.dimen.column_values_text_size) / (resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT))
        // set column width
        data.barWidth = COLUMN_WIDTH
        // set column values formatter
        data.setValueFormatter(ColumnValuesFormatter())

        // set data to chart and invalidate
        chart.data = data
        chart.invalidate()
    }

}