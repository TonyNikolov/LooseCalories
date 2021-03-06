package com.fatal.loosecalories

import com.fatal.loosecalories.ui.base.BaseDialogView
import com.fatal.loosecalories.ui.base.BaseView
import com.github.mikephil.charting.data.BarData


/**
 * Created by fatal on 10/28/2017.
 */
interface IView {
    interface MainActivity : BaseView {
    }

    interface ChartFragment : BaseView {
        fun setData(data: BarData)
    }

    interface AddDailyFoodFragment : BaseView {

    }

    interface CreateDialogFragment : BaseDialogView{

    }
}


