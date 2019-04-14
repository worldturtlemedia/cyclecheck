package com.worldturtlemedia.cyclecheck.core.ktx

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView

inline fun TextView.doBeforeTextChanged(
    crossinline action: (text: CharSequence?, start: Int, count: Int, after: Int) -> Unit
) = addTextChangedListener(beforeTextChanged = action)

/**
 * Add an action which will be invoked when the text is changing.
 *
 * @return the [TextWatcher] added to the TextView
 */
inline fun TextView.doOnTextChanged(
    crossinline action: (text: CharSequence?, start: Int, count: Int, after: Int) -> Unit
) = addTextChangedListener(onTextChanged = action)

/**
 * Add an action which will be invoked after the text changed.
 *
 * @return the [TextWatcher] added to the TextView
 */
inline fun TextView.doAfterTextChanged(
    crossinline action: (text: Editable?) -> Unit
) = addTextChangedListener(afterTextChanged = action)

/**
 * Add a text changed listener to this TextView using the provided actions
 *
 * @return the [TextWatcher] added to the TextView
 */
inline fun TextView.addTextChangedListener(
    crossinline beforeTextChanged: (
        text: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) -> Unit = { _, _, _, _ -> },
    crossinline onTextChanged: (
        text: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) -> Unit = { _, _, _, _ -> },
    crossinline afterTextChanged: (text: Editable?) -> Unit = {}
): TextWatcher {
    val textWatcher = object : TextWatcher {

        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s)
        }

        override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
            beforeTextChanged.invoke(text, start, count, after)
        }

        override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged.invoke(text, start, before, count)
        }
    }
    addTextChangedListener(textWatcher)

    return textWatcher
}
