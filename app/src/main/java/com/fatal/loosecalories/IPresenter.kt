package com.fatal.loosecalories

import com.fatal.loosecalories.models.Food
import com.fatal.loosecalories.ui.base.BasePresenter


/**
 * Created by fatal on 10/28/2017.
 */
interface IPresenter {
    interface MainActivity : BasePresenter<IView.MainActivity> {
    }

    interface ChartFragment : BasePresenter<IView.ChartFragment> {
        fun getChart()
    }

    interface AddDailyFoodFrgment : BasePresenter<IView.AddDailyFoodFragment> {
        fun addFood(food: Food)
    }
}
