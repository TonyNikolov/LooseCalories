package com.fatal.loosecalories.ui.CreateFoodDialogFragment

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import butterknife.ButterKnife
import butterknife.OnTextChanged
import com.fatal.loosecalories.App
import com.fatal.loosecalories.IView
import com.fatal.loosecalories.R
import com.fatal.loosecalories.Utils.LogUtils
import com.fatal.loosecalories.common.Validator
import com.fatal.loosecalories.models.entities.Food
import com.fatal.loosecalories.models.PushFoodEvent
import com.fatal.loosecalories.models.enums.MeasurementUnitType
import com.fatal.loosecalories.ui.base.BaseDialogFragment
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.dialog_create_food_fragment.*
import javax.inject.Inject
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import com.fatal.loosecalories.models.CreateFoodDialogFragmentUiModel


/**
 * Created by fatal on 11/8/2017.
 */
class CreateFoodDialogFragment : BaseDialogFragment(), IView.CreateDialogFragment {
    private val BASIC_TAG = CreateFoodDialogFragment::javaClass.name

    lateinit var presenter: CreateFoodDialogFragmentPresenter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var validator: Validator

    var items = arrayOf("Grams", "Ounces")
    lateinit var adapter: ArrayAdapter<String>
    private val compositeDisposable = CompositeDisposable()

    companion object {
        fun getInstance(): CreateFoodDialogFragment = CreateFoodDialogFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.dialog_create_food_fragment, container, false)

        ButterKnife.bind(this, view)

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        App.graph.inject(this)

        presenter = ViewModelProviders.of(this, viewModelFactory).get(CreateFoodDialogFragmentPresenter::class.java)

        val uiEvents: Observable<PushFoodEvent> = btn_dialog_create_food_save
                .clicks()
                .takeWhile { validateFields() }
                .map {
                    LogUtils.log(BASIC_TAG, "map btnSaveFood clicks()")
                    PushFoodEvent(makeFood())
                }

        compositeDisposable.add(uiEvents.subscribe { presenter.pushFood(it) })
        // TODO
        compositeDisposable.add(presenter.uiModelObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(this::render))
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.dispose()
    }

    private fun setupUI() {
        et_dialog_create_food_calories.isEnabled = false
        adapter = ArrayAdapter(activity, android.R.layout.simple_spinner_dropdown_item, items)
        spinner_dialog_create_food_measurement_type.adapter = adapter
    }

    private fun render(uiModel: CreateFoodDialogFragmentUiModel) {
        if (uiModel.inProgress) {
            showLoading()
        } else {
            hideLoading()
        }

        btn_dialog_create_food_save.isEnabled = !uiModel.inProgress

        if (uiModel.error != null) {
            uiModel.error.message?.let { showMessage(it) }
        } else if (uiModel.id != null) {
            showMessage(uiModel.id.toString())
        }

    }

    override fun showLoading() {
        pb_dialog_create_food.visibility = View.VISIBLE

        Log.i("showLoading", id.toString())
    }

    override fun hideLoading() {

        Log.i("hideLoading", id.toString())
        pb_dialog_create_food.visibility = View.GONE
    }

    override fun dismissDialog() {
        dismiss()
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    fun show(fragmentManager: FragmentManager) {
        show(fragmentManager, BASIC_TAG)
    }


    @OnTextChanged(R.id.et_dialog_create_food_protein, R.id.et_dialog_create_food_carbs, R.id.et_dialog_create_food_fats)
    fun onMacroChangedListener() {

        val protein: Float = et_dialog_create_food_protein.text.toString().trim().takeUnless { it.isEmpty() }?.toFloat() ?: 0F
        val carbs: Float = et_dialog_create_food_carbs.text.toString().trim().takeUnless { it.isEmpty() }?.toFloat() ?: 0F
        val fats: Float = et_dialog_create_food_fats.text.toString().trim().takeUnless { it.isEmpty() }?.toFloat() ?: 0F

        val res = (protein * 4) + (carbs * 4) + (fats * 9)
        et_dialog_create_food_calories.setText(res.toString())
    }

    private fun validateFields(): Boolean {

        var etList: Array<EditText> = arrayOf(
                et_dialog_create_food_name,
                et_dialog_create_food_protein,
                et_dialog_create_food_carbs,
                et_dialog_create_food_fats)

        return validator.validateRequiredEditTextFields(
                "This field is required",
                etList)
                && validator.validateRequiredEditTextFieldsWithNumbers(
                "Must be greater than 0", arrayOf(et_dialog_create_food_quantity))
    }

    private fun makeFood(): Food {

        var name = et_dialog_create_food_name.text.toString()
        var quantity: Int = et_dialog_create_food_quantity.text.toString().toInt()
        var measurementType = spinner_dialog_create_food_measurement_type.selectedItem.toString().toUpperCase()
        var measurementTypeEnum: MeasurementUnitType = MeasurementUnitType.GRAMS
        var protein = et_dialog_create_food_protein.text.toString().toFloat()
        var carbs = et_dialog_create_food_carbs.text.toString().toFloat()
        var fats = et_dialog_create_food_fats.text.toString().toFloat()

        if (measurementType.equals(MeasurementUnitType.GRAMS.toString())) {
            measurementTypeEnum = MeasurementUnitType.GRAMS
        } else if (measurementType.equals(MeasurementUnitType.OUNCES.toString())) {
            measurementTypeEnum = MeasurementUnitType.OUNCES
        }

        return Food(name = name,
                protein = protein,
                carbs = carbs,
                fats = fats,
                quantity = quantity,
                measurementUnitType = measurementTypeEnum)
    }
}