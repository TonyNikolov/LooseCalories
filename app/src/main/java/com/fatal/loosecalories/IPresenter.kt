package com.fatal.loosecalories

import com.fatal.loosecalories.models.PushDailyFoodEvent
import com.fatal.loosecalories.models.PushFoodEvent
import com.fatal.loosecalories.models.entities.DailyFood


/**
 * Created by fatal on 10/28/2017.
 */
interface IPresenter {
    interface MainActivity {
    }

    interface ChartFragment {
        fun getChart()
    }

    interface AddDailyFoodFrgment {
        fun pushDailyFood(dailyFoodEvent: PushDailyFoodEvent)
    }

    interface CreateDialogFoodFragment {
        fun pushFood(pushFoodEvent: PushFoodEvent)
    }
}
