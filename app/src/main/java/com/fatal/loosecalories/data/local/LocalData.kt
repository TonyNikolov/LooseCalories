package com.fatal.loosecalories.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.fatal.loosecalories.data.local.dao.FoodDao
import com.fatal.loosecalories.models.Calories
import com.fatal.loosecalories.models.Food

/**
 * Created by fatal on 10/28/2017.
 */
@Database(entities = arrayOf(Food::class, Calories::class), version = 1, exportSchema = false)
abstract class LocalData : RoomDatabase() {
    abstract fun foodDao(): FoodDao
}
