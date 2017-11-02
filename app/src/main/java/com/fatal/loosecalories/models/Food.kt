package com.fatal.loosecalories.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * Created by fatal on 10/28/2017.
 */
@Entity(tableName = "food")
data class Food(var name: String, var protein: Int, var carbs: Int, var fats: Int,var localDate: Date = Date()) {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
