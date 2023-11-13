package com.example.wym_002

import android.app.Dialog
import android.view.View
import android.view.Window


fun hidingPanel(window: Window) {            // скрывает панель навигации
    val uiOptions: Int = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    window.decorView.systemUiVisibility = uiOptions
}

fun hidingPanel(dialog: Dialog) {            // скрывает панель навигации в диалоге
    val uiOptions: Int = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    dialog.window?.decorView?.systemUiVisibility = uiOptions
}