package com.fatal.loosecalories.ui.CreateFoodDialogFragment

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fatal.loosecalories.App
import com.fatal.loosecalories.IView
import com.fatal.loosecalories.R
import com.fatal.loosecalories.ui.base.BaseDialogFragment
import javax.inject.Inject
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.OnTextChanged
import com.fatal.loosecalories.Validator
import com.fatal.loosecalories.models.Food
import com.fatal.loosecalories.models.enums.MeasurementUnitType
import kotlinx.android.synthetic.main.dialog_create_food.*


/**
 * Created by fatal on 11/8/2017.
 */
class CreateFoodDialogFragment : BaseDialogFragment(), IView.CreateDialogFragment {
    val TAG = "CreateFoodDialogFragment"

    @Inject
    lateinit var mPresenter: CreateFoodDialogFragmentPresenter
    @Inject
    lateinit var validator: Validator

    var items = arrayOf("Grams", "Ounces")
    lateinit var adapter: ArrayAdapter<String>
    lateinit var food: Food

    companion object {
        fun getInstance(): CreateFoodDialogFragment = CreateFoodDialogFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.dialog_create_food, container, false)

        App.graph.inject(this)
        ButterKnife.bind(this, view)

        mPresenter.attachView(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
    }

    private fun setupUI() {
        et_dialog_create_food_calories.isEnabled = false
        adapter = ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, items)
        spinner_dialog_create_food_measurement_type.adapter = adapter
    }

    fun show(fragmentManager: FragmentManager) {
        super.show(fragmentManager, TAG)
    }

    override fun showMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun dismissDialog() {
        dismiss()
    }

    @OnClick(R.id.btn_dialog_create_food_save)
    fun onSaveClicked() {
        if (validateFields()) {
            makeFood()
            mPresenter.pushFood(food)
        }
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

    private fun makeFood() {

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

        food = Food(name = name,
                protein = protein,
                carbs = carbs,
                fats = fats,
                quantity = quantity,
                measurementUnitType = measurementTypeEnum)
    }
}