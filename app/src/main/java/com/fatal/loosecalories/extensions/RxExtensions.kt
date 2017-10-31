package com.fatal.loosecalories.extensions


import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by fatal on 10/31/2017.
 */
fun Disposable.addToCompositeDisposable(composite: CompositeDisposable) {
    composite.add(this)
}