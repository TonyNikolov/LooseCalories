package com.fatal.loosecalories.injection

import android.content.Context
import com.fatal.loosecalories.IPresenter
import com.fatal.loosecalories.data.DefaultScheduler
import com.fatal.loosecalories.data.LooseData
import com.fatal.loosecalories.ui.AddDailyFoodFragment.AddDailyFoodFragmentPresenter
import com.fatal.loosecalories.ui.ChartFragment.ChartFragmentPresenter
import com.fatal.loosecalories.ui.MainActivity.MainActivityPresenter
import dagger.Module
import dagger.Provides

/**
 * Created by fatal on 10/28/2017.
 */
@Module
class PresenterModule(private val contex: Context) {
    @Provides
    fun providesMainActivityPresenter(looseData: LooseData): IPresenter.MainActivity =
            MainActivityPresenter(looseData)

    @Provides
    fun providesChartFragmentPresenter(looseData: LooseData): IPresenter.ChartFragment =
            ChartFragmentPresenter(contex, looseData, DefaultScheduler)

    @Provides
    fun providesAddDailyFoodFragmentPresenter(looseData: LooseData): IPresenter.AddDailyFoodFrgment =
            AddDailyFoodFragmentPresenter(looseData, DefaultScheduler)
}