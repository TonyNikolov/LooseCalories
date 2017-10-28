package com.fatal.loosecalories.data.local.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.fatal.loosecalories.models.Food

/**
 * Created by fatal on 10/28/2017.
 */
@Dao
interface FoodDao {
    @Query("select * from food")
    fun getAllTasks(): List<Food>
}
