package com.fatal.loosecalories.views

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.fatal.loosecalories.App
import com.fatal.loosecalories.IPresenter
import com.fatal.loosecalories.IView
import com.fatal.loosecalories.R
import com.fatal.loosecalories.databinding.MainActivityBinding
import javax.inject.Inject
import android.support.v4.widget.DrawerLayout
import butterknife.BindView
import butterknife.ButterKnife
import android.support.design.widget.NavigationView
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.main_activity.*


class MainActivity : AppCompatActivity(), IView.MainActivity {

    @Inject
    lateinit var presenter: IPresenter.MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: MainActivityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        App.graph.inject(this)
        presenter.attachView(this)

        setupUI();
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
            override fun onDrawerClosed(v: View?) {
                super.onDrawerClosed(v)
            }

            override fun onDrawerOpened(v: View?) {
                super.onDrawerOpened(v)
            }
        }
        drawer_view.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
    }

    override fun showMessage(message: String) {
        Log.i("MainActivity", message)
    }
}

