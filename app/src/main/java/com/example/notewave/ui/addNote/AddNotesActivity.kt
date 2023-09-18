package com.example.notewave.ui.addNote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.notewave.R
import com.example.notewave.databinding.ActivityAddNotesBinding
import com.example.notewave.db.Note
import com.example.notewave.db.NoteDao
import com.example.notewave.db.NoteDatabase
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
        setStatusBarAppearance(window.decorView.rootView)
        noteDao = NoteDatabase.getDatabase(this).getNoteDao()


        //Functions
        setOnClickListener()
        setNotes()
        updateNote()
        handleBackPressed()
    }

    private fun setOnClickListener() {
        binding.noteTitleEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.noteDescriptionEditText.requestFocus()
                binding.noteDescriptionEditText.setSelection(binding.noteDescriptionEditText.text.length)
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun setNotes() {
        binding.noteTitleEditText.requestFocus()
        binding.noteSaveButton.setOnClickListener {
            val title = binding.noteTitleEditText.text
            val note = binding.noteDescriptionEditText.text
            if (title.toString().isNotEmpty() && note.toString().isNotEmpty()) {
                scope.launch(Dispatchers.IO) {
                    noteDao.insert(Note(0, title.toString(), note.toString()))
                    finish()
                }
            } else {
                Toast.makeText(this@AddNotesActivity, "Fill both fields!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun updateNote() {
        intent.extras?.apply {
            val id = getInt("id")
            val title = getString("title")
            val noteDescription = getString("noteDescription")

            binding.apply {
                val titleEt = noteTitleEditText
                val noteEt = noteDescriptionEditText
                titleEt.setText(title)
                noteEt.setText(noteDescription)
                titleEt.setSelection(titleEt.text.length)
                titleEt.requestFocus()
                noteSaveButton.setOnClickListener {
                    scope.launch(Dispatchers.IO) {
                        noteDao.updateNote(id, titleEt.text.toString(), noteEt.text.toString())
                        finish()
                    }
                }
            }
        }

    }

    private fun handleBackPressed() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }

        })
        binding.backButton.setOnClickListener {
            finish()
        }
    }
}
