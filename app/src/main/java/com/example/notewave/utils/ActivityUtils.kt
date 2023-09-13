package com.example.notewave.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.widget.Button
import com.example.notewave.R

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

class CustomDialog {
    companion object {
        @SuppressLint("MissingInflatedId")
        fun showDialog(context: Context, message: String? = null, callback: () -> Unit) {
            val dialog = Dialog(context)
            dialog.apply {
                setContentView(R.layout.cutom_delete_dialog)
                setCancelable(false)
                window?.setBackgroundDrawableResource(R.drawable.custom_dialog_bg)
                findViewById<Button>(R.id.deleteAllButton).setOnClickListener {
                    callback.invoke()
                    dismiss()
                }
                findViewById<Button>(R.id.cancelButton).setOnClickListener {
                    cancel()
                }
                show()
            }
        }
    }
}