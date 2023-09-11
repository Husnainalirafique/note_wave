package com.example.notewave.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController

fun newActivity(context: Context, targetActivity: Class<out Activity>, dataBundle: Bundle? = null) {
    val intent = Intent(context, targetActivity)
    if (dataBundle != null) {
        intent.putExtras(dataBundle)
    }
    context.startActivity(intent)
}

fun setStatusBarAppearance(view: View) {
    val insetsController = view.windowInsetsController
    insetsController?.setSystemBarsAppearance(
        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
    )
}