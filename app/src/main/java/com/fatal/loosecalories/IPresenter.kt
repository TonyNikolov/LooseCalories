package com.fatal.loosecalories

import com.fatal.loosecalories.models.DailyFood
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
        fun addDailyFood(dailyFood: DailyFood)
    }

    interface CreateDialogFoodFragment: BasePresenter<IView.CreateDialogFragment> {
        fun pushFood(food: Food)
    }
}
