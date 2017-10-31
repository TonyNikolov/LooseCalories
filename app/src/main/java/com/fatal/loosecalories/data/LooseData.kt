package com.fatal.loosecalories.data

import com.fatal.loosecalories.data.local.LocalData
import com.fatal.loosecalories.data.remote.RemoteService
import com.fatal.loosecalories.models.Food
import io.reactivex.Flowable
import io.reactivex.Observable

/**
 * Created by fatal on 10/28/2017.
 */
class LooseData(private val localData: LocalData, private val remoteService: RemoteService) : LooseDataService {
    override fun getFood(): Flowable<List<Food>> = Flowable.just(arrayListOf(Food("asd", 1, 2, 3), Food("asd", 1, 2, 3)))
}