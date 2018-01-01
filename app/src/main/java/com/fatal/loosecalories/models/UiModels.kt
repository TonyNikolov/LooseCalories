package com.fatal.loosecalories.models

import com.fatal.loosecalories.models.entities.DailyFood
import com.github.mikephil.charting.data.BarData

/**
 * Created by fatal on 12/10/2017.
 */
sealed class UiModels

class CreateFoodDialogFragmentUiModel(val id: Long?, val inProgress: Boolean, val error: Throwable?) : UiModels() {
    fun copy(id: Long?, inProgress: Boolean, error: Throwable?): CreateFoodDialogFragmentUiModel {
        return CreateFoodDialogFragmentUiModel(id, inProgress, error)
    }
}

class ChartFragmentUiModel(val dailyFoods: BarData?, val inProgress: Boolean, val error: Throwable?) : UiModels() {
    fun copy(dailyFoods: BarData?, inProgress: Boolean, error: Throwable?): ChartFragmentUiModel {
        return ChartFragmentUiModel(dailyFoods, inProgress, error)
    }
}

class AddDailyFoodFragmentUiModel(val id: Long?, val inProgress: Boolean, val error: Throwable?) : UiModels() {
    fun copy(id: Long?, inProgress: Boolean, error: Throwable?): AddDailyFoodFragmentUiModel {
        return AddDailyFoodFragmentUiModel(id, inProgress, error)
    }
}