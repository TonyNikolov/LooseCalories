package com.fatal.loosecalories.presenters

import android.graphics.Color
import com.fatal.loosecalories.IPresenter
import com.fatal.loosecalories.IView
import com.fatal.loosecalories.common.ValueFormatter
import com.fatal.loosecalories.data.DefaultScheduler
import com.fatal.loosecalories.data.LooseData
import com.fatal.loosecalories.extensions.addToCompositeDisposable
import com.fatal.loosecalories.models.Food
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

/**
 * Created by fatal on 10/29/2017.
 */
class ChartFragmentPresenter @Inject constructor(private val looseData: LooseData, private val scheduler: DefaultScheduler) : IPresenter.ChartFragment {


    var mView: IView.ChartFragment? = null

    var foods: ArrayList<Food>? = null
    var foodsObservable: Flowable<List<Food>>? = null

    private lateinit var data: BarData

    private val compositeDisposable = CompositeDisposable()
    private val connectableDisposable = CompositeDisposable()

    fun getFoodLocal() {
        compositeDisposable.add(looseData.getFood()
                .subscribeOn(scheduler.io)
                .observeOn(scheduler.ui)
                .filter { it.isNotEmpty() }
                .subscribeBy(
                        onNext = { addFoodToBarData(it) },
                        onError = { it.printStackTrace() },
                        onComplete = { showChart(data) }
                ))
    }

    override fun getChart() {

    }
//
//
//    override fun getChart() {
//        getFoodLocal()
//        looseData.getFood()
//                .subscribeOn(scheduler.io)
//                .observeOn(scheduler.ui)
//                .replay()
//                .apply {
//                    subscribeToFoods(this)
//                    connectableDisposable.add(connect())
//                }
//
//
//    }

    fun addFoodToBarData(foods: List<Food>) {

        var proteinVals = ArrayList<BarEntry>()
        var carbVals = ArrayList<BarEntry>()
        var fatVals = ArrayList<BarEntry>()

        for(food:Food in foods){
            proteinVals.add(BarEntry(1.toFloat(), food.protein.toFloat()))
            carbVals.add(BarEntry(2.toFloat(), food.carbs.toFloat()))
            fatVals.add(BarEntry(3.toFloat(), food.fats.toFloat()))
        }

        var set1: BarDataSet
        var set2: BarDataSet
        var set3: BarDataSet
        set1 = BarDataSet(proteinVals, "protein intake")
        set1.setDrawIcons(false)
        set1.colors = getColors().toList()

        set2 = BarDataSet(carbVals, "carb intake")
        set2.setDrawIcons(false)
        set2.colors = getColors().toList()

        set3 = BarDataSet(fatVals, "fat intake")
        set3.setDrawIcons(false)
        set3.colors = getColors().toList()

        val dataSets: ArrayList<IBarDataSet> = ArrayList()
        dataSets.add(set1)
        dataSets.add(set2)
        dataSets.add(set3)

        data = BarData(dataSets)
        data.setValueFormatter(ValueFormatter())
        data.setValueTextColor(Color.WHITE)
    }

    fun showChart(data: BarData) {

        mView?.setData(data)
    }

//    fun subscribeToFoods(observable: Flowable<List<Food>>) {
//        foodsObservable = observable
//
//        mView?.let {
//            //            it.setLoadingIndicatorVisible(true)
//            observable
//                    .doOnError { _ ->
//                        //                        it.setLoadingIndicatorVisible(false)
//                        foodsObservable = null
//                    }
//                    .doOnComplete {
//                        //                        it.setLoadingIndicatorVisible(false)
//                        foodsObservable = null
//                    }
//                    .subscribe(
//                            { list ->
//                                foods?.addAll(list)
//                                generateBarData()
//                                showChart(data)
//                            },
//                            { e ->
//                                //                                when (e) {
////                                    is NoNetworkException -> view?.showNoConnectivityError()
////                                    else -> {
////                                        Timber.e(e)
////                                        view?.showUnknownError()
////                                    }
////                                }
//                            }
//                    )
//                    .addToCompositeDisposable(compositeDisposable)
//        }
//    }


    override fun attachView(view: IView.ChartFragment) {
        mView = view

//        foods?.let { l ->
//            addFoodToBarData(l)
//            showChart(data)
//        }
        getFoodLocal()

    }

    override fun detachView() {
        mView = null
        compositeDisposable.clear()
    }

    override fun unsubscribe() {
        connectableDisposable.clear()
    }

    private fun getColors(): IntArray {

        val stacksize = 3

        // have as many colors as stack-values per entry
        val colors = IntArray(stacksize)

        for (i in colors.indices) {
            colors[i] = ColorTemplate.MATERIAL_COLORS[i]
        }

        return colors
    }
}