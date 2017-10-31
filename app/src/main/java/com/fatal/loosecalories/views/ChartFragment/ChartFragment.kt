package com.fatal.loosecalories.views.ChartFragment

import android.app.Fragment
import android.graphics.Color
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
import com.fatal.loosecalories.common.ValueFormatter
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet


/**
 * Created by fatal on 10/29/2017.
 */
class ChartFragment : Fragment(), IView.ChartFragment {
    @Inject
    lateinit var presenter: IPresenter.ChartFragment


    protected var mMonths = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec")

    protected var mParties = arrayOf("Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H", "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P", "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X", "Party Y", "Party Z")

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

        barchart.setMaxVisibleValueCount(40)

        // scaling can now only be done on x- and y-axis separately
        barchart.setPinchZoom(false)

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
        barchart.data = data
        barchart.invalidate()
    }

    override fun showMessage(message: String) {
        Log.i("ChartFragment", message)
    }

}