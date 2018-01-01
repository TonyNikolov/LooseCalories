package com.fatal.loosecalories.ui.ChartFragment

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fatal.loosecalories.App
import com.fatal.loosecalories.IView
import com.fatal.loosecalories.R
import com.fatal.loosecalories.Utils.Util
import com.fatal.loosecalories.common.AxisValueFormatter
import com.fatal.loosecalories.models.ChartFragmentUiModel
import com.fatal.loosecalories.models.UiModels
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.data.BarData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.chart_fragment.*
import kotlinx.android.synthetic.main.dialog_create_food_fragment.*
import javax.inject.Inject


/**
 * Created by fatal on 10/29/2017.
 */
class ChartFragment : Fragment(), IView.ChartFragment {

    lateinit var mPresenter: ChartFragmentPresenter
    private val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        App.graph.inject(this)
        setupChart()
        mPresenter = ViewModelProviders.of(this, viewModelFactory).get(ChartFragmentPresenter::class.java)


        mPresenter.getChart()
        // TODO Subscribe to chart instead
        compositeDisposable.add(mPresenter.uiModelObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(this::render))
    }

    private fun render(uiModel: ChartFragmentUiModel) {
        if (uiModel.inProgress) {
            showLoading()
        } else {
            hideLoading()
        }

//        btn_dialog_create_food_save.isEnabled = !uiModel.inProgress

        if (uiModel.error != null) {
            uiModel.error.message?.let { showMessage(it) }
        }

        if (uiModel.dailyFoods != null) {
            setData(uiModel.dailyFoods)
        }

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