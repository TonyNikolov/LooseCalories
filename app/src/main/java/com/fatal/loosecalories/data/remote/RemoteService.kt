package com.fatal.loosecalories.data.remote

import com.fatal.loosecalories.models.entities.DailyFood
import com.fatal.loosecalories.models.entities.Food
import io.objectbox.query.Query
import io.reactivex.Flowable

/**
 * Created by fatal on 10/28/2017.
 */
interface RemoteService {
    fun getFood(): Query<DailyFood>
    fun pushDailyFood(dailyFood: DailyFood): Flowable<Long>
    fun pushFood(food: Food): Flowable<Long>
}