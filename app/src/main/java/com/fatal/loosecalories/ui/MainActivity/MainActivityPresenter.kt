package com.fatal.loosecalories.ui.MainActivity

import com.fatal.loosecalories.IPresenter
import com.fatal.loosecalories.IView
import com.fatal.loosecalories.data.LooseData
import javax.inject.Inject

/**
 * Created by fatal on 10/28/2017.
 */
class MainActivityPresenter @Inject constructor(val looseData: LooseData) : IPresenter.MainActivity {
    override fun attachView(view: IView.MainActivity) {
        view.showMessage("Main Activity Presenter")
//
//        looseData.pushDailyFood(DailyFood("asd",15,20,25))
//        looseData.pushDailyFood(DailyFood("asd",25,25,25))
//        looseData.pushDailyFood(DailyFood("asd",25,15,15))
//        looseData.pushDailyFood(DailyFood("asd",45,20,15))
    }

    override fun detachView() {}

    override fun unsubscribe() {}
}