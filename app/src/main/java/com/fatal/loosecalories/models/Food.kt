package com.fatal.loosecalories.models

import com.fatal.loosecalories.common.MeasurementUnit
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.util.*

/**
 * Created by fatal on 11/8/2017.
 */
@Entity
data class Food(var name: String,
                var protein: Float,
                var carbs: Float,
                var fats: Float) {
    @Id
    var id: Long = 0
}
