package com.fatal.loosecalories.ui.AddDailyFoodFragment

import com.fatal.loosecalories.IPresenter
import com.fatal.loosecalories.Utils.LogUtils
import com.fatal.loosecalories.Utils.Util
import com.fatal.loosecalories.data.DefaultScheduler
import com.fatal.loosecalories.data.LooseData
import com.fatal.loosecalories.models.*
import com.fatal.loosecalories.models.entities.DailyFood
import com.fatal.loosecalories.ui.base.BasePresenter
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.processors.PublishProcessor
import javax.inject.Inject

/**
 * Created by fatal on 11/4/2017.
 */
class AddDailyFoodFragmentPresenter
@Inject constructor(private val looseData: LooseData, private val scheduler: DefaultScheduler)
    : BasePresenter(), IPresenter.AddDailyFoodFrgment {

    private val BASIC_TAG = AddDailyFoodFragmentPresenter::javaClass.name

    private var currentState = AddDailyFoodFragmentUiModel(null, false, null)
    private val uiEvents: PublishProcessor<Events> = PublishProcessor.create<Events>()

    private val uiEventTransformer: FlowableTransformer<Events, AddDailyFoodFragmentUiModel>

    init {
        uiEventTransformer = FlowableTransformer { event ->
            event.publish { shared ->
                shared.ofType(PushDailyFoodEvent::class.java)
                        .compose(pushFoodTransformer)
                        .scan(currentState, { previous, result ->
                            stateReducer(previous, result)
                        })
            }
        }
    }

    private val pushFoodTransformer: FlowableTransformer<PushDailyFoodEvent, Results> = FlowableTransformer { event ->
        val TAG = Util.stringsToPath(BASIC_TAG, "pushFoodTransformer")
        event.flatMap {
            looseData.pushDailyFood(it.dailyFood)
                    .map {
                        LogUtils.log(TAG, " .map id = $it")
                        PushDailyFoodResult(id = it)
                    }
                    .onErrorReturn {
                        LogUtils.log(TAG, ".onErrorReturn error = $it")
                        PushDailyFoodResult(error = it)
                    }
                    .startWith(PushDailyFoodResult(inProgress = true))
                    .subscribeOn(scheduler.diskIO)
                    .observeOn(scheduler.ui)
        }

    }

    val uiModelObservable: Flowable<AddDailyFoodFragmentUiModel>

    init {
        val TAG = Util.stringsToPath(BASIC_TAG, "uiModelObservable")
        uiModelObservable =
                uiEvents.compose(uiEventTransformer)
                        .replay(1)
                        .autoConnect()
                        .doOnNext {
                            LogUtils.log(TAG, """"
                                id: ${it.id})
                                error: ${it.error.toString()}
                                inprogress: ${it.inProgress}""")

                        }

    }

    override fun pushDailyFood(dailyFoodEvent: PushDailyFoodEvent) {
        val TAG = Util.stringsToPath(BASIC_TAG, "pushDailyFood")

        uiEvents.onNext(dailyFoodEvent)
    }

    private fun stateReducer(previousState: AddDailyFoodFragmentUiModel, result: Results): AddDailyFoodFragmentUiModel {
        val TAG = Util.stringsToPath(BASIC_TAG, "stateReducer")
        when (result) {
            is PushDailyFoodResult -> {
                currentState = previousState.copy(
                        id = result.id,
                        inProgress = result.inProgress,
                        error = result.error)

            }
        }
        return currentState
    }

}
