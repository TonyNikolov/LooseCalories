package com.fatal.loosecalories.injection

import android.content.Context
import com.fatal.loosecalories.App
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by fatal on 10/28/2017.
 */
@Module
internal class AppModule(private val app: App) {
    @Provides
    @Singleton
    fun provideApp() = app


    @Provides
    @Singleton
    fun providesApplicationContext(): Context = app


    @Provides
    @Singleton
    fun providesGson(): Gson =
            GsonBuilder()
                    .serializeNulls()
                    .create()
}