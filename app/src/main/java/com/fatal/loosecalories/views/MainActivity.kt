package com.fatal.loosecalories.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.fatal.loosecalories.App
import com.fatal.loosecalories.IPresenter
import com.fatal.loosecalories.IView
import com.fatal.loosecalories.R
import javax.inject.Inject

class MainActivity : AppCompatActivity(), IView.MainActivity {
    @Inject
    lateinit var presenter: IPresenter.MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        App.graph.inject(this)
        presenter.attachView(this)
    }

    override fun showMessage(message: String) {
        Log.i("MainActivity", message)
    }
}

