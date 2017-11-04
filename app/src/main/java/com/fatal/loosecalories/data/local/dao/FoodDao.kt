package com.fatal.loosecalories.data.local.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.fatal.loosecalories.models.Food
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by fatal on 10/28/2017.
 */
@Dao
interface FoodDao {
    @Query("select * from food")
    fun getAllFood(): Flowable<List<Food>>

    @Insert
    fun pushFood(food: Food)
}
