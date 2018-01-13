package com.fatal.loosecalories.ui.ChartFragment

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.util.Log
import com.fatal.loosecalories.IPresenter
import com.fatal.loosecalories.R
import com.fatal.loosecalories.Utils.LogUtils
import com.fatal.loosecalories.Utils.Util
import com.fatal.loosecalories.common.chart.ValueFormatter
import com.fatal.loosecalories.data.DefaultScheduler
import com.fatal.loosecalories.data.LooseData
import com.fatal.loosecalories.models.*
import com.fatal.loosecalories.models.entities.DailyFood
import com.fatal.loosecalories.models.GetDailyFoodResult
import com.fatal.loosecalories.models.Results
import com.fatal.loosecalories.ui.base.BasePresenter
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import io.objectbox.reactive.DataObserver
import io.objectbox.reactive.DataSubscriptionList
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.processors.PublishProcessor
import javax.inject.Inject


/**
 * Created by fatal on 10/29/2017.
 */
class ChartFragmentPresenter @Inject constructor(
        private val context: Context,
        public val looseData: LooseData,
        public val scheduler: DefaultScheduler) : BasePresenter(), IPresenter.ChartFragment {

    private val BASIC_TAG = " chartFragment "+ChartFragmentPresenter::class.java.name
    private val subscriptions: DataSubscriptionList = DataSubscriptionList()
    private var currentState = ChartFragmentUiModel(null, false, null)
    private val uiEvents: PublishProcessor<Events> = PublishProcessor.create<Events>()

    private val dailyFoodObserver: DataObserver<List<DailyFood>> = DataObserver {
        val TAG = Util.stringsToPath(BASIC_TAG, " dailyFoodObserver")
        LogUtils.log(TAG, " onData")

        uiEvents.onNext(GetFoodEvent(it))
    }

    private val uiEventTransformer: FlowableTransformer<Events, ChartFragmentUiModel>

    init {
        uiEventTransformer = FlowableTransformer { event ->
            event.publish { shared ->
                shared.ofType(GetFoodEvent::class.java)
                        .compose(pushFoodTransformer)
                        .scan(currentState, { previous, result ->
                            stateReducer(previous, result)
                        })
            }
        }
    }

    private val pushFoodTransformer: FlowableTransformer<GetFoodEvent, Results> = FlowableTransformer { event ->
        val TAG = Util.stringsToPath(BASIC_TAG, " pushFoodTransformer")
        event.takeWhile { Util.isListNotEmpty(it.dailyFoods) }
                .map { addFoodToBarData(it.dailyFoods) }
                .flatMap {
                    Flowable.just(GetDailyFoodResult(dailyFoods = it))
                }

    }

    val uiModelObservable: Flowable<ChartFragmentUiModel>

    init {
        uiModelObservable =
                uiEvents.compose(uiEventTransformer)
                        .replay(1)
                        .autoConnect()
                        .doOnSubscribe {
                            val TAG = Util.stringsToPath(BASIC_TAG, " uiModelObservable")
                            LogUtils.log(TAG, " doOnSubscribe called")
                            getFoodLocal()
                        }
                        .doOnEach {
                            val TAG = Util.stringsToPath(BASIC_TAG, " uiModelObservable")
                            LogUtils.log(TAG, " onEach called")
                        }
    }

    fun getFoodLocal() {
        looseData.getFood().subscribe(subscriptions).observer(dailyFoodObserver)
    }

    override fun getChart() {
        getFoodLocal()
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

    fun addFoodToBarData(dailyFoods: List<DailyFood>): BarData {
        Log.i("dailyFoodsCount", dailyFoods.size.toString())


        val targetProtein = 160F
        val targetCarb = 240F
        val targetFat = 90F

        var proteinCount = 0F
        var carbCount = 0F
        var fatCount = 0F

        for (dailyFood: DailyFood in dailyFoods) {
            proteinCount += dailyFood.protein
            carbCount += dailyFood.carbs
            fatCount += dailyFood.fats
        }

        val totalMacroCount = proteinCount + carbCount + fatCount
        val targetMacroCount = targetProtein + targetCarb + targetFat

        val dataSets: ArrayList<IBarDataSet> = ArrayList()

        dataSets.add(getBarDataSet(1, proteinCount, targetProtein, "protein"))
        dataSets.add(getBarDataSet(2, carbCount, targetCarb, "carb"))
        dataSets.add(getBarDataSet(3, fatCount, targetFat, "fat"))
        dataSets.add(getBarDataSet(4, totalMacroCount, targetMacroCount, "total"))


        val data = BarData(dataSets)
        data.setValueFormatter(ValueFormatter())
        data.setValueTextColor(Color.WHITE)

        return data
    }

    private fun stateReducer(previousState: ChartFragmentUiModel, result: Results): ChartFragmentUiModel {
        val TAG = Util.stringsToPath(BASIC_TAG, "stateReducer")
        when (result) {
            is GetDailyFoodResult -> {
                currentState = previousState.copy(
                        dailyFoods = result.dailyFoods,
                        inProgress = result.inProgress,
                        error = result.error)
            }
        }
        return currentState
    }


    fun showChart() {

//        mView?.setData(data)
    }
//
//    override fun attachView(view: IView.ChartFragment) {
////        mView = view
////        getFoodLocal()
//
//    }
//
//    override fun detachView() {
////        mView = null
////        subscriptions.cancel()
//    }

    private fun getUnderflowColors(): IntArray {
        return intArrayOf(ContextCompat.getColor(context, R.color.light_green), ContextCompat.getColor(context, R.color.orange))
    }

    private fun getOverflowColors(): IntArray {
        return intArrayOf(ContextCompat.getColor(context, R.color.light_green), ContextCompat.getColor(context, R.color.red_dark))
    }
}