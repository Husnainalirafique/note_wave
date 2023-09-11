package com.example.notewave.ui.addNote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.notewave.R
import com.example.notewave.databinding.ActivityAddNotesBinding
import com.example.notewave.utils.setStatusBarAppearance

class AddNotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNotesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_notes)

        //Functions
        setClickListeners()
        setStatusBarAppearance(window.decorView.rootView)

    }

    private fun setClickListeners() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }
}
