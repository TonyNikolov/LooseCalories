package com.fatal.loosecalories.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.util.*

/**
 * Created by fatal on 10/28/2017.
 */
@Entity
data class DailyFood(var name: String,
                     var protein: Float,
                     var carbs: Float,
                     var fats: Float,
                     var localDate: Date = Date()) {

    @Id
    var id: Long = 0

}
