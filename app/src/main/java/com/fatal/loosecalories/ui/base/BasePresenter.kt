package com.fatal.loosecalories.ui.base

interface BasePresenter<in V : BaseView> {
    fun detachView()
    fun attachView(view: V)
    fun unsubscribe()
}