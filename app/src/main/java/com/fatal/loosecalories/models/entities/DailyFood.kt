package com.fatal.loosecalories.models.entities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.util.*

/**
 * Created by fatal on 10/28/2017.
 */
@Entity
data class DailyFood(@Id
    var id: Long = 0,
    var name: String,
    var protein: Float,
    var carbs: Float,
    var fats: Float,
    var localDate: Date = Date())
