package com.example.notewave.ui.addNote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.notewave.R
import com.example.notewave.databinding.ActivityAddNotesBinding
import com.example.notewave.db.Note
import com.example.notewave.db.NoteDao
import com.example.notewave.db.NoteDatabase
import com.example.notewave.ui.home.noteAdapter
import com.example.notewave.utils.setStatusBarAppearance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddNotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNotesBinding
    private lateinit var noteDao: NoteDao
    private var scope = lifecycleScope
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_notes)
        //Functions
        initLateProperties()
        setNotes()
        backButton()
        setStatusBarAppearance(window.decorView.rootView)
        handleBackPressed()
    }

    private fun setNotes() {
        binding.noteSaveButton.setOnClickListener {
            val title = binding.noteTitleEditText.text
            val note = binding.noteDescriptionEditText.text
            if (title.toString().isNotEmpty() && note.toString().isNotEmpty()) {
                scope.launch(Dispatchers.IO) {
                    val newNote = Note(0, title.toString(), note.toString())
                    noteDao.insert(newNote)
                    finish()
                }
            } else {
                Toast.makeText(this@AddNotesActivity, "Fill both fields!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun initLateProperties() {
        noteDao = NoteDatabase.getDatabase(this).getNoteDao()
    }

    private fun backButton() {
        binding.backButton.setOnClickListener {
            finish()
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
