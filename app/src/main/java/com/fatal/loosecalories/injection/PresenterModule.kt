package com.fatal.loosecalories.injection

import com.fatal.loosecalories.IPresenter
import com.fatal.loosecalories.data.LooseData
import com.fatal.loosecalories.presenters.MainActivityPresenter
import dagger.Module
import dagger.Provides

/**
 * Created by fatal on 10/28/2017.
 */
@Module
class PresenterModule {
    @Provides
    fun providesMainActivityPresenter(looseData: LooseData): IPresenter.MainActivity =
            MainActivityPresenter(looseData)
}