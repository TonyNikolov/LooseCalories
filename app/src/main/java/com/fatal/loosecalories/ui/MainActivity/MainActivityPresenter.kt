package com.fatal.loosecalories.ui.MainActivity

import com.fatal.loosecalories.IPresenter
import com.fatal.loosecalories.IView
import com.fatal.loosecalories.data.LooseData
import com.fatal.loosecalories.ui.base.BasePresenter
import javax.inject.Inject

/**
 * Created by fatal on 10/28/2017.
 */
class MainActivityPresenter @Inject constructor(val looseData: LooseData) : BasePresenter(), IPresenter.MainActivity {

}