package com.example.notewave

import android.content.res.Configuration
import android.os.Bundle
import android.view.WindowInsetsController
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.notewave.databinding.ActivityMainBinding

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
        val isDarkMode =
            resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
        if (isDarkMode) {
            setDarkModeColors()
        } else {
            setLightModeColors()
        }
    }

    private fun setDarkModeColors() {
        binding.topAppNameTv.setTextColor(ContextCompat.getColor(this, R.color.white))
        binding.searchView.background.setTint(
            ContextCompat.getColor(
                this,
                R.color.searchBarDarkModeColor
            )
        )
    }

    private fun setLightModeColors() {
        binding.topAppNameTv.setTextColor(ContextCompat.getColor(this, R.color.black))
        binding.searchView.background.setTint(
            ContextCompat.getColor(
                this,
                R.color.searchBarLightModeColor
            )
        )
    }

    private fun setClickListeners() {

    }

    private fun handleBackPressed() {
        onBackPressedDispatcher.addCallback(this) {
            finish()
        }
    }

    private fun setStatusBarContentVisibility() {
        val insetsController = window.insetsController
        insetsController?.setSystemBarsAppearance(
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        )
    }


}
