package com.fatal.loosecalories.data

/**
 * Created by fatal on 10/31/2017.
 */
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.schedulers.ImmediateThinScheduler
import io.reactivex.schedulers.Schedulers

interface SchedulerContract {
    val io: Scheduler
    val ui: Scheduler
    val diskIO: Scheduler
}

object DefaultScheduler : SchedulerContract {
    override val io: Scheduler = Schedulers.io()
    override val ui: Scheduler = AndroidSchedulers.mainThread()
    override val diskIO: Scheduler = Schedulers.single()
}