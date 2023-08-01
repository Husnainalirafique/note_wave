package com.example.notewave.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View

fun setClickListener(view: View, onClick: () -> Unit) {
    view.setOnClickListener { onClick() }
}

fun newActivity(context: Context, targetActivity: Class<out Activity>, dataBundle: Bundle? = null) {
    val intent = Intent(context, targetActivity)
    if (dataBundle != null) {
        intent.putExtras(dataBundle)
    }
    context.startActivity(intent)
}