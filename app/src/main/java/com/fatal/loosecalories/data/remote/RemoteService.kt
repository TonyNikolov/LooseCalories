package com.fatal.loosecalories.data.remote

import com.fatal.loosecalories.models.Food
import io.reactivex.Flowable
import io.reactivex.Observable

/**
 * Created by fatal on 10/28/2017.
 */
interface RemoteService {
    fun getFood(): Flowable<List<Food>>
}