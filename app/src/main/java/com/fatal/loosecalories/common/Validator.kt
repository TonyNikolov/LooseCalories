package com.fatal.loosecalories.common

import android.widget.EditText


/**
 * Created by fatal on 11/11/2017.
 */
class Validator {
    fun stringEmptyOrNull(vararg strings: String): Boolean {
        return strings.any { current -> current.isEmpty() || current.trim { it <= ' ' }.isEmpty() }
    }

    fun validateRequiredEditTextFieldsWithNumbers(messageOnFail: String, et: Array<EditText>): Boolean{
        for (current in et)
            if (stringEmptyOrNull(current.text.toString())) {
                current.error = messageOnFail
                return false
            } else if(current.text.toString().toInt()<=0) {
                current.error = messageOnFail
                return false
            }
        return true
    }

    fun validateRequiredEditTextFields(messageOnFail: String, et: Array<EditText>): Boolean {
        for (current in et)
            if (stringEmptyOrNull(current.text.toString())) {
                current.error = messageOnFail
                return false
            }
        return true
    }
}