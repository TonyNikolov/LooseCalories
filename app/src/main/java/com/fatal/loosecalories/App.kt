package com.fatal.loosecalories

import android.app.Application
import com.fatal.loosecalories.injection.*
import io.objectbox.BoxStore

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

        initDagger()
    }

    private fun initDagger() {
        graph = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .dataModule(DataModule())
                .presenterModule(PresenterModule(this))
                .build()
        graph.inject(this)
    }

}