package com.fatal.loosecalories.ui.CreateFoodDialogFragment

import android.content.Context
import com.fatal.loosecalories.IPresenter
import com.fatal.loosecalories.IView
import com.fatal.loosecalories.data.DefaultScheduler
import com.fatal.loosecalories.data.LooseData
import com.fatal.loosecalories.models.Food
import io.reactivex.Flowable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

/**
 * Created by fatal on 11/8/2017.
 */
class CreateFoodDialogFragmentPresenter @Inject constructor(
        private val looseData: LooseData,
        private val scheduler: DefaultScheduler) : IPresenter.CreateDialogFoodFragment {

    override fun detachView() {
    }

    private lateinit var view: IView.CreateDialogFragment

    override fun attachView(view: IView.CreateDialogFragment) {
        this.view = view
    }

    override fun unsubscribe() {
    }

    override fun pushFood(food: Food) {
        looseData.pushFood(food)
                .subscribeOn(scheduler.diskIO)
                .observeOn(scheduler.ui)
                .subscribeBy (
                        onNext = {
                            view.showMessage(it.toString())
                        },
                        onError = {
                            it.message?.let { it1 -> view.showMessage(it1) }
                        }
                )
    }
}