package com.fernandowagner104.todolist.extensions


import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*

private val locale = Locale("pt","BR")

fun Date.format(): String {
    return SimpleDateFormat("dd/MM/yyyy", locale).format(this)
} // function extendida de classe Date, que irá formatar a nossa data

var TextInputLayout.text: String
    get() = editText?.text?.toString() ?: ""
    set(value) {
        editText?.setText(value)
    } // essa var extension irá ser usada no binding.tilDate.text
     //  se caso nós não tivessemos feito essa extension, o nosso binding ficaria assim:  binding.tilDate.editText?.setText(Date(it).format())

