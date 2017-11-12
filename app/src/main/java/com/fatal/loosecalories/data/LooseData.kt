package com.fatal.loosecalories.data

import com.fatal.loosecalories.data.local.LocalData
import com.fatal.loosecalories.data.remote.RemoteService
import com.fatal.loosecalories.models.DailyFood
import com.fatal.loosecalories.models.Food
import io.objectbox.query.Query
import io.reactivex.Flowable

/**
 * Created by fatal on 10/28/2017.
 */
class LooseData(private val localData: LocalData, private val remoteService: RemoteService) : LooseDataService {
    override fun getFood(): Query<DailyFood> {
        return localData.getTodaysDailyFoods()
    }

    override fun pushDailyFood(dailyFood: DailyFood) {
        localData.pushDailyFood(dailyFood)
    }

    override fun pushFood(food: Food): Flowable<Long> {
        return localData.pushFood(food)
    }
}