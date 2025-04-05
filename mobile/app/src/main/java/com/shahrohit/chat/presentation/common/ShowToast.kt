package com.shahrohit.chat.presentation.common

import android.content.Context
import android.widget.Toast

fun showToast(context: Context, message :String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}