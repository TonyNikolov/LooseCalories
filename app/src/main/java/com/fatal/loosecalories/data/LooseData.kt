package com.fatal.loosecalories.data

import com.fatal.loosecalories.data.local.LocalData
import com.fatal.loosecalories.data.local.LocalDb
import com.fatal.loosecalories.data.remote.RemoteService
import com.fatal.loosecalories.models.Food
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by fatal on 10/28/2017.
 */
class LooseData(private val localData: LocalData, private val remoteService: RemoteService) : LooseDataService {
    override fun pushFood(food: Food) = localData.pushFood(food)
    override fun getFood(): Flowable<List<Food>> = localData.getAllFood()
}