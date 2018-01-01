package com.fatal.loosecalories.injection

import android.content.Context
import com.fatal.loosecalories.App
import com.fatal.loosecalories.common.Validator
import com.fatal.loosecalories.data.DefaultScheduler
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by fatal on 10/28/2017.
 */
@Module(includes = arrayOf(PresenterModule::class))
internal class AppModule(private val app: App) {
    @Provides
    @Singleton
    fun provideApp() = app


    @Provides
    @Singleton
    fun providesApplicationContext(): Context = app

    @Provides
    fun providesValidator(): Validator = Validator()

    @Provides
    fun providesSchedulers(): DefaultScheduler = DefaultScheduler


    @Provides
    @Singleton
    fun providesGson(): Gson =
            GsonBuilder()
                    .serializeNulls()
                    .create()
}
