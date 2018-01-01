package com.fatal.loosecalories.models

import com.github.mikephil.charting.data.BarData

/**
 * Created by fatal on 12/10/2017.
 */

/**
 * Superclass for all the results from the model layer
 */
open class Results


data class PushFoodResult(val inProgress: Boolean = false,
                          val error: Throwable? = null,
                          val id: Long? = null) : Results()


data class GetDailyFoodResult(val inProgress: Boolean = false,
                              val error: Throwable? = null,
                              val dailyFoods: BarData? = null) : Results()


data class PushDailyFoodResult(val inProgress: Boolean = false,
                          val error: Throwable? = null,
                          val id: Long? = null) : Results()