package com.fatal.loosecalories.ui.ChartFragment

import android.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fatal.loosecalories.App
import com.fatal.loosecalories.IPresenter
import com.fatal.loosecalories.IView
import com.fatal.loosecalories.R
import com.github.mikephil.charting.components.Legend
import kotlinx.android.synthetic.main.chart_fragment.*
import javax.inject.Inject
import com.fatal.loosecalories.common.AxisValueFormatter
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.data.*


/**
 * Created by fatal on 10/29/2017.
 */
class ChartFragment : Fragment(), IView.ChartFragment {

    @Inject
    lateinit var presenter: IPresenter.ChartFragment

    companion object {
        fun getInstance(): ChartFragment = ChartFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.graph.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.chart_fragment, container, false)

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.attachView(this)
        setupChart()
        presenter.getChart()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    private fun setupChart() {
        // scaling can now only be done on x- and y-axis separately
        barchart.setPinchZoom(false)
        barchart.description = null
        barchart.setDrawGridBackground(false)
        barchart.setDrawBarShadow(false)

        barchart.setDrawValueAboveBar(false)
        barchart.isHighlightFullBarEnabled = false

        barchart.setScaleEnabled(false)
        barchart.setFitBars(true)

        // change the position of the y-labels
        val leftAxis = barchart.axisLeft
        leftAxis.valueFormatter = AxisValueFormatter()
        leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)
        barchart.axisRight.isEnabled = false

        val xLabels = barchart.xAxis
        xLabels.position = XAxisPosition.TOP

        val l = barchart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        l.formSize = 8f
        l.formToTextSpace = 4f
        l.xEntrySpace = 6f

    }


    override fun setData(data: BarData) {
        barchart.setMaxVisibleValueCount(300)
        barchart.data = data
        barchart.invalidate()
    }

    override fun showMessage(message: String) {
        Log.i("ChartFragment", message)
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }
}