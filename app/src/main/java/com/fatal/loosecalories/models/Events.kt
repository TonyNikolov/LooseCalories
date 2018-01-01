package com.fatal.loosecalories.models

import com.fatal.loosecalories.models.entities.DailyFood
import com.fatal.loosecalories.models.entities.Food

/**
 * Created by fatal on 12/10/2017.
 */

sealed class Events

data class PushFoodEvent(val food: Food) : Events()
data class GetFoodEvent(val dailyFoods: List<DailyFood>) : Events()
data class PushDailyFoodEvent(val dailyFood: DailyFood) : Events()