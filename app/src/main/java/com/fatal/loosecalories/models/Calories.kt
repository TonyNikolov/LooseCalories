package com.fatal.loosecalories.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by fatal on 10/28/2017.
 */
@Entity(tableName = "calories")
data class Calories(var protein: Int, var carbs: Int, var fats: Int) {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}