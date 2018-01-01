package com.fatal.loosecalories.models.entities

import com.fatal.loosecalories.models.enums.MeasurementUnitType
import com.fatal.loosecalories.models.enums.MeasurementUnitTypeConverter
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id


/**
 * Created by fatal on 11/8/2017.
 */
@Entity
data class Food(
        @Id
        var id: Long = 0,
        var name: String,
        var protein: Float,
        var carbs: Float,
        var fats: Float,
        var quantity: Int,
        @Convert(converter = MeasurementUnitTypeConverter::class, dbType = Int::class)
        val measurementUnitType: MeasurementUnitType)