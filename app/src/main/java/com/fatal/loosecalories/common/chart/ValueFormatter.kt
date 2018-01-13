package com.fatal.loosecalories.common.chart

import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.utils.ViewPortHandler
import com.github.mikephil.charting.formatter.IValueFormatter
import java.text.DecimalFormat


/**
 * Created by fatal on 10/30/2017.
 */

class ValueFormatter : IValueFormatter {

    private val mFormat: DecimalFormat = DecimalFormat("###,###,###,##0.0")

    override fun getFormattedValue(value: Float, entry: Entry, dataSetIndex: Int, viewPortHandler: ViewPortHandler): String {
        return mFormat.format(value)
    }
}
