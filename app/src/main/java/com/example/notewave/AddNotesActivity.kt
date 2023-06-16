package com.example.notewave

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.view.WindowInsetsController
import androidx.databinding.DataBindingUtil
import com.example.notewave.databinding.ActivityAddNotesBinding

class AddNotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNotesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_notes)

        //Functions
        setClickListeners()
        statusBarContentVisibility()
    }

    private fun setClickListeners() {
        binding.backButton.setOnClickListener { finish() }
    }

    private fun statusBarContentVisibility() {
      window.insetsController?.setSystemBarsAppearance(
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        )
    }
}
