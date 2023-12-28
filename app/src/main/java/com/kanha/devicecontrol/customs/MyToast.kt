package com.kanha.devicecontrol.customs

import android.app.Application
import android.content.Context
import android.widget.Toast

class MyToast {
    companion object {
        private var toast: Toast? = null
        fun show(
            context: Context,
            text: String,
            duration: Int = Toast.LENGTH_SHORT
        ) {
            toast?.cancel()
            toast = Toast.makeText(context, text, duration)
            toast?.show()
        }
    }
}