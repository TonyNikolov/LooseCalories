package com.fatal.loosecalories.ui.MainActivity

import android.app.FragmentTransaction
import android.content.pm.ActivityInfo
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.widget.Toast
import com.fatal.loosecalories.App
import com.fatal.loosecalories.IPresenter
import com.fatal.loosecalories.IView
import com.fatal.loosecalories.R
import com.fatal.loosecalories.databinding.MainActivityBinding
import com.fatal.loosecalories.ui.AddDailyFoodFragment.AddDailyFoodFragment
import com.fatal.loosecalories.ui.ChartFragment.ChartFragment
import com.fatal.loosecalories.ui.CreateFoodDialogFragment.CreateFoodDialogFragment
import com.fatal.loosecalories.ui.base.BaseActivity
import kotlinx.android.synthetic.main.main_activity.*
import javax.inject.Inject


class MainActivity : BaseActivity(), IView.MainActivity {

    @Inject
    lateinit var presenter: IPresenter.MainActivity
    lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT

        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)

        App.graph.inject(this)
        presenter.attachView(this)

        setupUI()
    }

    private fun setupUI() {
        setSupportActionBar(toolbar)
        // refer the navigation view of the xml
        navigation_view.setNavigationItemSelectedListener { menuItem ->
            val id = menuItem.itemId
            when (id) {
                R.id.nav_item_about -> {
                    Toast.makeText(applicationContext, "You Clicked Options About", Toast.LENGTH_SHORT).show()
                    drawer_view!!.closeDrawers()
                }
                R.id.nav_item_logout -> {
                    Toast.makeText(applicationContext, "You Clicked Options Logout", Toast.LENGTH_SHORT).show()
                    drawer_view!!.closeDrawers()
                }
            }
            true
        }

        val actionBarDrawerToggle = object : ActionBarDrawerToggle(this, drawer_view, toolbar, R.string.open_drawer, R.string.close_drawer) {

        }

        drawer_view.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        val transaction: FragmentTransaction = fragmentManager.beginTransaction().add(R.id.pie_chart, ChartFragment.getInstance())
        transaction.commit()

        val transaction2: FragmentTransaction = fragmentManager.beginTransaction().add(R.id.container_add_food_fragment, AddDailyFoodFragment.getInstance())
        transaction2.commit()

        CreateFoodDialogFragment.getInstance().show(supportFragmentManager)
    }


    override fun showMessage(message: String) {
        Log.i("MainActivity", message)
    }


    override fun showLoading() {
    }

    override fun hideLoading() {
    }
}

