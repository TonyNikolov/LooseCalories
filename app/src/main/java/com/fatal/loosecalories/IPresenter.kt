package com.fatal.loosecalories

import com.fatal.loosecalories.views.base.BasePresenter


/**
 * Created by fatal on 10/28/2017.
 */
interface IPresenter {
    interface MainActivity : BasePresenter<IView.MainActivity> {
    }

    interface ChartFragment: BasePresenter<IView.ChartFragment> {
        fun getChart()
    }
}
