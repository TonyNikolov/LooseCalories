package com.fatal.loosecalories.ui.AddDailyFoodFragment

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fatal.loosecalories.App
import com.fatal.loosecalories.IView
import com.fatal.loosecalories.R
import com.fatal.loosecalories.Utils.LogUtils
import com.fatal.loosecalories.models.AddDailyFoodFragmentUiModel
import com.fatal.loosecalories.models.PushDailyFoodEvent
import com.fatal.loosecalories.models.entities.DailyFood
import com.fatal.loosecalories.ui.base.BaseFragment
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.add_daily_food_fragment.*
import java.util.*
import javax.inject.Inject


/**
 * Created by fatal on 11/4/2017.
 */
class AddDailyFoodFragment : BaseFragment(), IView.AddDailyFoodFragment {
    private val BASIC_TAG = AddDailyFoodFragment::javaClass.name

    lateinit var presenter: AddDailyFoodFragmentPresenter
    private val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = ViewModelProviders.of(this, viewModelFactory).get(AddDailyFoodFragmentPresenter::class.java)

        val uiEvents: Observable<PushDailyFoodEvent> = btn_add_daily_food_fragment_add_food
                .clicks()
                .takeWhile { true }
                .map {
                    PushDailyFoodEvent(dailyFood = DailyFood(name ="asd", protein = rand(1, 30).toFloat(), carbs = rand(1, 30).toFloat(), fats = rand(1, 30).toFloat()))
                }

        compositeDisposable.add(uiEvents.subscribe { presenter.pushDailyFood(it) })
        compositeDisposable.add(presenter.uiModelObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(this::render))
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.dispose()
    }

    private fun render(uiModel: AddDailyFoodFragmentUiModel) {
        if (uiModel.inProgress) {
            showLoading()
        } else {
            hideLoading()
        }

//        btn_dialog_create_food_save.isEnabled = !uiModel.inProgress

        if (uiModel.error != null) {
            uiModel.error.message?.let { showMessage(it) }
        } else if (uiModel.id != null) {
            showMessage(uiModel.id.toString())
        }

    }

    override fun showLoading() {

    }

    override fun hideLoading() {
    }

    private val random = Random()

    private fun rand(from: Int, to: Int): Int {
        return random.nextInt(to - from) + from
    }
}