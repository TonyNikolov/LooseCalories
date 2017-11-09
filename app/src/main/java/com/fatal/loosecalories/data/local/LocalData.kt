package com.fatal.loosecalories.data.local

import android.util.Log
import com.fatal.loosecalories.data.DefaultScheduler
import com.fatal.loosecalories.models.DailyFood
import com.fatal.loosecalories.models.MyObjectBox
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.query.Query
import io.objectbox.reactive.DataSubscriptionList
import io.reactivex.Flowable


/**
 * Created by fatal on 11/2/2017.
 */
class LocalData(val boxStore: BoxStore, val scheduler: DefaultScheduler) {
    var dailyFoodStore: Box<DailyFood> = boxStore.boxFor(DailyFood::class.java)

    fun getFood(): Query<DailyFood> {
       return dailyFoodStore.query().build()
    }

    fun pushFood(dailyFood: DailyFood) {
        var id = dailyFoodStore.put(dailyFood)
        Log.i("foodSaved",id.toString())
    }
}