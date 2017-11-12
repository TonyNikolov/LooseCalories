package com.fatal.loosecalories.ui.ChartFragment

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.util.Log
import com.fatal.loosecalories.IPresenter
import com.fatal.loosecalories.IView
import com.fatal.loosecalories.R
import com.fatal.loosecalories.common.ValueFormatter
import com.fatal.loosecalories.data.DefaultScheduler
import com.fatal.loosecalories.data.LooseData
import com.fatal.loosecalories.models.DailyFood
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import io.objectbox.reactive.DataObserver
import io.objectbox.reactive.DataSubscriptionList
import javax.inject.Inject


/**
 * Created by fatal on 10/29/2017.
 */
class ChartFragmentPresenter @Inject constructor(
        private val context: Context,
        private val looseData: LooseData,
        private val scheduler: DefaultScheduler) : IPresenter.ChartFragment {


    var mView: IView.ChartFragment? = null
    private lateinit var data: BarData
    private val subscriptions: DataSubscriptionList = DataSubscriptionList()

    private val dailyFoodObserver: DataObserver<List<DailyFood>> = DataObserver {
        addFoodToBarData(it)
        showChart()
    }


    fun getFoodLocal() {
        looseData.getFood().subscribe(subscriptions).observer(dailyFoodObserver)
    }

    override fun getChart() {

    }

    fun getBarDataSet(index: Int, macroCount: Float, targetMacroCount: Float, label: String): BarDataSet {
        val dataSet: BarDataSet
        val macroVals = ArrayList<BarEntry>()

        if (targetMacroCount - macroCount >= 0) {
            val targetProteinEntry = targetMacroCount - macroCount
            macroVals.add(BarEntry(index.toFloat(), floatArrayOf(macroCount, targetProteinEntry)))
            dataSet = BarDataSet(macroVals, label)
            dataSet.colors = getUnderflowColors().toList()
        } else {
            val overflowProteinEntry = macroCount - targetMacroCount
            macroVals.add(BarEntry(index.toFloat(), floatArrayOf(targetMacroCount, overflowProteinEntry)))
            dataSet = BarDataSet(macroVals, label)
            dataSet.colors = getOverflowColors().toList()
        }

        dataSet.setDrawIcons(false)
        dataSet.valueTextSize = 12F
        dataSet.form = Legend.LegendForm.NONE
        dataSet.stackLabels = arrayOf("")

        return dataSet
    }

    fun addFoodToBarData(dailyFoods: List<DailyFood>) {
        Log.i("dailyFoodsCount", dailyFoods.size.toString())

        val targetProtein = 160F
        val targetCarb = 240F
        val targetFat = 90F

        var proteinCount = 0F
        var carbCount = 0F
        var fatCount = 0F

        for (dailyFood: DailyFood in dailyFoods) {
            proteinCount += dailyFood.protein.toFloat()
            carbCount += dailyFood.carbs.toFloat()
            fatCount += dailyFood.fats.toFloat()
        }

        val totalMacroCount = proteinCount + carbCount + fatCount
        val targetMacroCount = targetProtein + targetCarb + targetFat

        val dataSets: ArrayList<IBarDataSet> = ArrayList()

        dataSets.add(getBarDataSet(1, proteinCount, targetProtein, "protein"))
        dataSets.add(getBarDataSet(2, carbCount, targetCarb, "carb"))
        dataSets.add(getBarDataSet(3, fatCount, targetFat, "fat"))
        dataSets.add(getBarDataSet(4, totalMacroCount, targetMacroCount, "total"))

        data = BarData(dataSets)
        data.setValueFormatter(ValueFormatter())
        data.setValueTextColor(Color.WHITE)
    }


    fun showChart() {

        mView?.setData(data)
    }

    override fun attachView(view: IView.ChartFragment) {
        mView = view
        getFoodLocal()

    }

    override fun detachView() {
        mView = null
        subscriptions.cancel()
    }

    override fun unsubscribe() {
    }

    private fun getUnderflowColors(): IntArray {
        return intArrayOf(ContextCompat.getColor(context, R.color.light_green), ContextCompat.getColor(context, R.color.orange))
    }

    private fun getOverflowColors(): IntArray {
        return intArrayOf(ContextCompat.getColor(context, R.color.light_green), ContextCompat.getColor(context, R.color.red_dark))
    }
}