package com.fatal.loosecalories.data.local

import android.util.Log
import com.fatal.loosecalories.models.DailyFood
import com.fatal.loosecalories.models.DailyFood_
import com.fatal.loosecalories.models.Food
import com.fatal.loosecalories.models.Food_
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.query.Query
import io.reactivex.Flowable
import java.util.*


/**
 * Created by fatal on 11/2/2017.
 */
class LocalData(val boxStore: BoxStore) {
    var dailyFoodStore: Box<DailyFood> = boxStore.boxFor(DailyFood::class.java)
    var foodStore: Box<Food> = boxStore.boxFor(Food::class.java)

    fun getTodaysDailyFoods(): Query<DailyFood> {
        return dailyFoodStore.query().between(DailyFood_.localDate, getYesterday(), getTomorrow()).build()
    }

    fun pushDailyFood(dailyFood: DailyFood) {
        var id = dailyFoodStore.put(dailyFood)
        Log.i("dailyFoodSaved", id.toString())
    }

    fun pushFood(food: Food): Flowable<Long> {
        if (!foodExists(food.name)) {
            val id = foodStore.put(food)
            Log.i("food", id.toString())
            return Flowable.just(id)
        } else {
            val error: String = "food with ${food.name} exists"
            Log.i(error, food.name)
            return Flowable.error(Throwable(error))
        }
    }

    fun foodExists(name: String): Boolean {
        val lstt: List<Food> = foodStore.query().equal(Food_.name,name).build().find()
        return lstt.size>0
    }

    private fun getYesterday(): Date {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -1)
        return cal.time
    }

    private fun getTomorrow(): Date {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, 1)
        return cal.time

    }

}