package com.fatal.loosecalories.ui.AddDailyFoodFragment

import com.fatal.loosecalories.IPresenter
import com.fatal.loosecalories.IView
import com.fatal.loosecalories.data.DefaultScheduler
import com.fatal.loosecalories.data.LooseData
import com.fatal.loosecalories.models.DailyFood
import javax.inject.Inject

/**
 * Created by fatal on 11/4/2017.
 */
class AddDailyFoodFragmentPresenter
@Inject constructor(private val looseData: LooseData, private val scheduler: DefaultScheduler)
    : IPresenter.AddDailyFoodFrgment {

    var mView: IView.AddDailyFoodFragment? = null


    override fun attachView(view: IView.AddDailyFoodFragment) {
        mView = view

    }

    override fun detachView() {
        mView = null
    }

    override fun unsubscribe() {
    }

    override fun addDailyFood(dailyFood: DailyFood) {
        looseData.pushDailyFood(dailyFood)
        scheduler.io
    }

}
