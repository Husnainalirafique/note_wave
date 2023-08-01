package com.example.notewave.ui.home

import android.content.res.Configuration
import android.os.Bundle
import android.view.WindowInsetsController
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.notewave.R
import com.example.notewave.utils.newActivity
import com.example.notewave.databinding.ActivityMainBinding
import com.example.notewave.ui.addNote.AddNotesActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //Functions
        setupUI()
        setClickListeners()
        handleBackPressed()
        setStatusBarContentVisibility()
    }

    private fun setupUI() {
        val isDarkMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
        if (isDarkMode) {
            setDarkModeColors()
        } else {
            setLightModeColors()
        }
    }

    private fun setDarkModeColors() {
        binding.topAppNameTv.setTextColor(ContextCompat.getColor(this, R.color.white))
        binding.searchView.background.setTint(resources.getColor(R.color.searchBarDarkModeColor,null))
    }

    private fun setLightModeColors() {
        binding.topAppNameTv.setTextColor(ContextCompat.getColor(this, R.color.black))
        binding.searchView.background.setTint(resources.getColor(R.color.searchBarLightModeColor,null))
    }

    private fun setClickListeners() {
        binding.createNoteButton.setOnClickListener {
//            startActivity(Intent(this,AddNotesActivity::class.java))
            newActivity(this, AddNotesActivity::class.java)
        }
    }

    private fun handleBackPressed() {
        onBackPressedDispatcher.addCallback(this) {
            finish()
        }
    }

    private fun setStatusBarContentVisibility() {
        window.insetsController?.setSystemBarsAppearance(
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        )
    }
}
