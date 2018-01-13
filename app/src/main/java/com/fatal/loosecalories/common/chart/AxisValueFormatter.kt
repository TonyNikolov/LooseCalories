package com.fatal.loosecalories.common.chart

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import java.text.DecimalFormat


/**
 * Created by fatal on 10/30/2017.
 */
class AxisValueFormatter : IAxisValueFormatter {

    private val mFormat: DecimalFormat = DecimalFormat("###,###,###,##0.0")

    override fun getFormattedValue(value: Float, axis: AxisBase): String {
        return mFormat.format(value) + " $"
    }
}
