package com.fatal.loosecalories.ui.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.util.Log
import android.view.ViewGroup
import android.view.Window


/**
 * Created by fatal on 11/11/2017.
 */
abstract class BaseDialogFragment : DialogFragment(), BaseDialogView {
    private var mActivity: BaseActivity? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            this.mActivity = context
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // the content
        val root = ConstraintLayout(activity)
        root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

        // creating the fullscreen dialog
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(root)
        if (dialog.window != null) {
            dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        dialog.setCanceledOnTouchOutside(true)

        return dialog
    }

    override fun showMessage(message: String) {
        mActivity.let {
            if (it is BaseView) {
                it.showMessage(message)
            }
        }
    }

    override fun show(fragmentManager: FragmentManager, tag: String) {
        val transaction = fragmentManager.beginTransaction()
        val prevFragment = fragmentManager.findFragmentByTag(tag)
        if (prevFragment != null) {
            transaction.remove(prevFragment)
        }
        transaction.addToBackStack(null)
        show(transaction, tag)
    }
}