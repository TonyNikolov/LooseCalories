package com.fatal.loosecalories.data

import io.objectbox.android.AndroidScheduler
import io.objectbox.reactive.Scheduler

/**
 * Created by fatal on 10/31/2017.
 */

interface SchedulerContract {
    //    val io: Scheduler
    val ui: Scheduler
//    val diskIO: Scheduler
}

object DefaultScheduler : SchedulerContract {
    override val ui: Scheduler = AndroidScheduler.mainThread()
}