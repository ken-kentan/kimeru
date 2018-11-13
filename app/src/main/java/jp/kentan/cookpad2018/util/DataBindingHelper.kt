package jp.kentan.cookpad2018.util

import android.R
import android.databinding.BindingAdapter
import android.support.design.widget.TextInputLayout
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView

@BindingAdapter("errorMessage")
fun setErrorMessage(view: TextView, message: String?) {
    view.visibility = if (message == null || message.isBlank())  View.GONE else  View.VISIBLE
    view.text = message
}

@BindingAdapter("error")
fun setError(view: TextInputLayout, message: String?) {
    view.error = message
}

@BindingAdapter("adapterEntities")
fun setAdapterEntities(view: AutoCompleteTextView, list: List<*>?) {
    if (list != null) {
        view.setAdapter(ArrayAdapter(view.context, R.layout.simple_list_item_1, list))
    }
}