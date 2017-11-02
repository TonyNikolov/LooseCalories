package com.fatal.loosecalories.models

import android.arch.persistence.room.TypeConverter
import org.joda.time.LocalDate
import java.util.*


/**
 * Created by fatal on 11/1/2017.
 */
class
Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {

        return when (value) {
            null -> null
            else -> Date(value)
        }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {

        return when (date) {
            null -> null
            else -> date.time
        }
    }
}