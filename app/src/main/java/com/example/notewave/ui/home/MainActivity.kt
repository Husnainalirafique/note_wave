package com.example.notewave.ui.home

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.notewave.R
import com.example.notewave.utils.newActivity
import com.example.notewave.databinding.ActivityMainBinding
import com.example.notewave.ui.addNote.AddNotesActivity
import com.example.notewave.utils.setStatusBarAppearance

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //Functions
        setClickListeners()
        handleBackPressed()
        setStatusBarAppearance(window.decorView.rootView)
    }

    private fun setClickListeners() {
        binding.createNoteButton.setOnClickListener {
            newActivity(this, AddNotesActivity::class.java)
        }
    }

    private fun handleBackPressed() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

}

