package com.fatal.loosecalories.presenters

import com.fatal.loosecalories.IPresenter
import com.fatal.loosecalories.IView
import com.fatal.loosecalories.data.LooseData
import javax.inject.Inject

/**
 * Created by fatal on 10/28/2017.
 */
class MainActivityPresenter @Inject constructor(looseData: LooseData) : IPresenter.MainActivity {
    override fun attachView(view: IView.MainActivity) {
        view.showMessage("Main Activity Presenter");
    }

    override fun detachView() {}

    override fun unsubscribe() {}
}