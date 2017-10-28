package com.fatal.loosecalories.injection

import com.fatal.loosecalories.App
import com.fatal.loosecalories.views.MainActivity
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
}