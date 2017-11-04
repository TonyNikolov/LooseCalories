package com.fatal.loosecalories.views.AddDailyFoodFragment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fatal.loosecalories.App
import com.fatal.loosecalories.IPresenter
import com.fatal.loosecalories.IView
import com.fatal.loosecalories.R
import com.fatal.loosecalories.models.Food
import com.fatal.loosecalories.views.ChartFragment.ChartFragment
import kotlinx.android.synthetic.main.add_daily_food_fragment.*
import javax.inject.Inject

/**
 * Created by fatal on 11/4/2017.
 */
class AddDailyFoodFragment : Fragment(), IView.AddDailyFoodFragment {


    @Inject
    lateinit var presenter: IPresenter.AddDailyFoodFrgment

    companion object {
        fun getInstance(): AddDailyFoodFragment = AddDailyFoodFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.graph.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.add_daily_food_fragment, container, false)

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.attachView(this)
        btn_add_daily_food_fragment_add_food.setOnClickListener { presenter.addFood(Food("asd", 15, 15, 15)) }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun showMessage(message: String) {

    }

}