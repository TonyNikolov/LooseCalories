package com.fatal.loosecalories.data.local

import com.fatal.loosecalories.data.DefaultScheduler
import com.fatal.loosecalories.data.local.dao.FoodDao
import com.fatal.loosecalories.models.Food
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy


/**
 * Created by fatal on 11/2/2017.
 */
class LocalData(val foodDao: FoodDao, val scheduler: DefaultScheduler) {
    fun pushFood(food: Food) {
        Flowable.just(food)
                .observeOn(scheduler.diskIO)
                .subscribeOn(scheduler.ui)
                .subscribe({ foodDao.pushFood(it)})
    }

    fun getAllFood(): Flowable<List<Food>> {
        return foodDao.getAllFood()
    }

}