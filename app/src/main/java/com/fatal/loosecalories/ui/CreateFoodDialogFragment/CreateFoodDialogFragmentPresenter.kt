package com.fatal.loosecalories.ui.CreateFoodDialogFragment

import com.fatal.loosecalories.IPresenter
import com.fatal.loosecalories.IView
import com.fatal.loosecalories.Utils.LogUtils
import com.fatal.loosecalories.Utils.Util
import com.fatal.loosecalories.data.DefaultScheduler
import com.fatal.loosecalories.data.LooseData
import com.fatal.loosecalories.models.CreateFoodDialogFragmentUiModel
import com.fatal.loosecalories.models.PushFoodEvent
import com.fatal.loosecalories.models.Events
import com.fatal.loosecalories.models.PushFoodResult
import com.fatal.loosecalories.models.Results
import com.fatal.loosecalories.ui.base.BasePresenter
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor
import javax.inject.Inject

/**
 * Created by fatal on 11/8/2017.
 */
class CreateFoodDialogFragmentPresenter @Inject constructor(
        private val looseData: LooseData,
        private val scheduler: DefaultScheduler) : BasePresenter(), IPresenter.CreateDialogFoodFragment {

    private val BASIC_TAG = CreateFoodDialogFragmentPresenter::javaClass.name
    private val compositeDisposable = CompositeDisposable()
    private var view: IView.CreateDialogFragment? = null
    private var currentState = CreateFoodDialogFragmentUiModel(null, false, null)
    private val uiEvents: PublishProcessor<Events> = PublishProcessor.create<Events>()

    private val uiEventTransformer: FlowableTransformer<Events, CreateFoodDialogFragmentUiModel>
    init {
        uiEventTransformer = FlowableTransformer { event ->
            event.publish { shared ->
                shared.ofType(PushFoodEvent::class.java)
                        .compose(pushFoodTransformer)
                        .scan(currentState, { previous, result ->
                            stateReducer(previous, result)
                        })
            }
        }
    }

    private val pushFoodTransformer: FlowableTransformer<PushFoodEvent, Results> = FlowableTransformer { event ->
        val TAG = Util.stringsToPath(BASIC_TAG, "pushFoodTransformer")
        event.flatMap {
            looseData.pushFood(it.food)
                    .map {
                        LogUtils.log(TAG, " .map id = $it")
                        PushFoodResult(id = it)
                    }
                    .onErrorReturn {
                        LogUtils.log(TAG, ".onErrorReturn error = $it")
                        PushFoodResult(error = it)
                    }
                    .startWith(PushFoodResult(inProgress = true))
                    .subscribeOn(scheduler.diskIO)
                    .observeOn(scheduler.ui)
        }

    }

    val uiModelObservable: Flowable<CreateFoodDialogFragmentUiModel>
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

    private fun stateReducer(previousState: CreateFoodDialogFragmentUiModel, result: Results): CreateFoodDialogFragmentUiModel {
        val TAG = Util.stringsToPath(BASIC_TAG, "stateReducer")
        when (result) {
            is PushFoodResult -> {
                currentState = previousState.copy(
                        id = result.id,
                        inProgress = result.inProgress,
                        error = result.error)

            }
        }
        return currentState
    }

    override fun pushFood(pushFoodEvent: PushFoodEvent) {
        val TAG = Util.stringsToPath(BASIC_TAG, "pushFoodEvent")

        uiEvents.onNext(pushFoodEvent)
    }
}