package com.fatal.loosecalories.ui.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * Created by fatal on 10/28/2017.
 */
abstract class BaseFragment : Fragment(), BaseView{

    private var mActivity: BaseActivity? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            this.mActivity = context
        }
    }

    override fun showMessage(message: String) {
        mActivity.let {
            if (it is BaseView) {
                it.showMessage(message)
            }
        }
    }
}
