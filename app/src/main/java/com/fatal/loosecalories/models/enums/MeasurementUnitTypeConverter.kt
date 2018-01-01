package com.fatal.loosecalories.models.enums

import io.objectbox.converter.PropertyConverter

/**
 * Created by fatal on 11/11/2017.
 */

class MeasurementUnitTypeConverter : PropertyConverter<MeasurementUnitType, Int> {
    override fun convertToEntityProperty(databaseValue: Int?): MeasurementUnitType? {
        if (databaseValue == null) {
            return null
        }
        return MeasurementUnitType.values().firstOrNull { it.id == databaseValue }
                ?: MeasurementUnitType.GRAMS
    }

    override fun convertToDatabaseValue(entityProperty: MeasurementUnitType?): Int? {
        return entityProperty?.id
    }

}