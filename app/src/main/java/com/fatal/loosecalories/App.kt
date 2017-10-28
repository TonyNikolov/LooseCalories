package com.fatal.loosecalories

import android.app.Application
import com.fatal.loosecalories.injection.*

/**
 * Created by fatal on 10/28/2017.
 */
class App : Application() {
    companion object {
        //platformStatic allow access it from java code
        @JvmStatic lateinit var graph: AppComponent
    }
    override fun onCreate() {
        super.onCreate()
        graph = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .dataModule(DataModule())
                .presenterModule(PresenterModule())
                .build()
        graph.inject(this)

        //TODO do some other cool stuff here
    }
}