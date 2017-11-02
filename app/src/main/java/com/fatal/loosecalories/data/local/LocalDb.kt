package com.fatal.loosecalories.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.fatal.loosecalories.data.local.dao.FoodDao
import com.fatal.loosecalories.models.Calories
import com.fatal.loosecalories.models.Converters
import com.fatal.loosecalories.models.Food

/**
 * Created by fatal on 10/28/2017.
 */
@Database(entities = arrayOf(Food::class, Calories::class), version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class LocalDb : RoomDatabase() {
    abstract fun foodDao(): FoodDao
}
