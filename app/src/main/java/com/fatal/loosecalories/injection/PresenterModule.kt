package com.fatal.loosecalories.injection

import android.arch.lifecycle.ViewModel
import android.content.Context
import com.fatal.loosecalories.data.LooseData
import com.fatal.loosecalories.ui.AddDailyFoodFragment.AddDailyFoodFragmentPresenter
import com.fatal.loosecalories.ui.ChartFragment.ChartFragmentPresenter
import com.fatal.loosecalories.ui.CreateFoodDialogFragment.CreateFoodDialogFragmentPresenter
import com.fatal.loosecalories.ui.MainActivity.MainActivityPresenter
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import android.arch.lifecycle.ViewModelProvider



/**
 * Created by fatal on 10/28/2017.
 */
@Module
abstract class PresenterModule(private val contex: Context) {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityPresenter::class)
     abstract fun bindMainActivityPresenter(mainActivityPresenter: MainActivityPresenter): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChartFragmentPresenter::class)
     abstract fun bindChartFragmentPresenter(chartFragmentPresenter: ChartFragmentPresenter): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddDailyFoodFragmentPresenter::class)
     abstract fun bindAddDailyFoodFragmentPresenter(addDailyFoodFragmentPresenter: AddDailyFoodFragmentPresenter): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateFoodDialogFragmentPresenter::class)
     abstract fun bindCreateFoodDialogFragment(createFoodDialogFragmentPresenter: CreateFoodDialogFragmentPresenter): ViewModel


    @Binds
    internal abstract fun bindViewModelFactory(factory: GithubViewModelFactory): ViewModelProvider.Factory
}