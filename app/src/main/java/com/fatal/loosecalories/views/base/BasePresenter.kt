package com.fatal.loosecalories.views.base

interface BasePresenter<in V : BaseView> {
    fun detachView()
    fun attachView(view: V)

    fun unsubscribe()
}