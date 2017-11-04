package com.fatal.loosecalories.injection

import com.fatal.loosecalories.App
import com.fatal.loosecalories.IView
import com.fatal.loosecalories.views.AddDailyFoodFragment.AddDailyFoodFragment
import com.fatal.loosecalories.views.ChartFragment.ChartFragment
import com.fatal.loosecalories.views.MainActivity.MainActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by fatal on 10/28/2017.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class, DataModule::class, PresenterModule::class))
interface AppComponent {
    fun inject(app: App)
    fun inject(mainActivity: MainActivity)
    fun inject(chartFragment: ChartFragment)
    fun inject(addDailyFoodFragment: AddDailyFoodFragment)
}