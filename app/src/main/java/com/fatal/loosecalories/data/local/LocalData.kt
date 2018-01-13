package com.fatal.loosecalories.data.local

import android.util.Log
import com.fatal.loosecalories.Utils.LogUtils
import com.fatal.loosecalories.Utils.Util
import com.fatal.loosecalories.models.entities.DailyFood
import com.fatal.loosecalories.models.entities.DailyFood_
import com.fatal.loosecalories.models.entities.Food
import com.fatal.loosecalories.models.entities.Food_
import com.fatal.loosecalories.ui.ChartFragment.ChartFragment
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.query.Query
import io.reactivex.Flowable
import java.util.*


/**
 * Created by fatal on 11/2/2017.
 */
class LocalData(boxStore: BoxStore) {
    private val BASIC_TAG = " chartFragment "+ LocalData::class.java.name

    private var dailyFoodStore: Box<DailyFood> = boxStore.boxFor(DailyFood::class.java)
    private var foodStore: Box<Food> = boxStore.boxFor(Food::class.java)

    fun getTodaysDailyFoods(): Query<DailyFood> {
        return dailyFoodStore.query().between(DailyFood_.localDate, getYesterday(), getTomorrow()).build()
    }

    fun pushDailyFood(dailyFood: DailyFood): Flowable<Long> {
        return Flowable.fromCallable({
            val TAG = Util.stringsToPath(BASIC_TAG, " pushDailyFood")
            LogUtils.log(TAG, " fromCallable called")
            val t = Thread.currentThread()
            val name = t.name
            Log.i("thread :", name)

            val id = dailyFoodStore.put(dailyFood)
            Log.i("pushDailyFood", id.toString())

            id
        })
    }

    fun pushFood(food: Food): Flowable<Long> {
        return Flowable.fromCallable({
            val t = Thread.currentThread()
            val name = t.name
            Log.i("thread :", name)


            if (!foodExists(food.name)) {
                val id = foodStore.put(food)
                Log.i("food", id.toString())
                id
            } else {
                val error = "food with ${food.name} exists"
                Log.i(error, food.name)
                throw Throwable(error)
            }

        })

    }

    private fun foodExists(name: String): Boolean {
        return foodStore.query().equal(Food_.name, name).build().find().isNotEmpty()
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

    fun pushDailyFood2(dailyFood: DailyFood) {
        val TAG = Util.stringsToPath(BASIC_TAG, " pushDailyFood")
        LogUtils.log(TAG, " fromCallable called")
        val t = Thread.currentThread()
        val name = t.name
        Log.i("thread :", name)

        val id = dailyFoodStore.put(dailyFood)
        Log.i("pushDailyFood", id.toString())
    }

}