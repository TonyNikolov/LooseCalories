package com.fatal.loosecalories.views

interface BasePresenter<in V : BaseView> {
    fun detachView(view: V)
    fun attachView(view: V)

    fun unsubscribe()
}