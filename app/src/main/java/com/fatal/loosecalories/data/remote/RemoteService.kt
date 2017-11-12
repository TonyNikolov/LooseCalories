package com.fatal.loosecalories.data.remote

import com.fatal.loosecalories.models.DailyFood
import com.fatal.loosecalories.models.Food
import io.objectbox.query.Query
import io.reactivex.Flowable

/**
 * Created by fatal on 10/28/2017.
 */
interface RemoteService {
    fun getFood(): Query<DailyFood>
    fun pushDailyFood(dailyFood: DailyFood)
    fun pushFood(food: Food): Flowable<Long>
}