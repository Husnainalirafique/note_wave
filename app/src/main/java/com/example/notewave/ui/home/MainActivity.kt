package com.example.notewave.ui.home

import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notewave.R
import com.example.notewave.adapter.NotesAdapter
import com.example.notewave.utils.newActivity
import com.example.notewave.databinding.ActivityMainBinding
import com.example.notewave.db.NoteDao
import com.example.notewave.db.NoteDatabase
import com.example.notewave.ui.addNote.AddNotesActivity
import com.example.notewave.utils.CustomDialog
import com.example.notewave.utils.setStatusBarAppearance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var noteAdapter: NotesAdapter
    private lateinit var noteDao: NoteDao
    private val scope = lifecycleScope
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setStatusBarAppearance(window.decorView.rootView)
        handlingMethods()
    }

    private fun handlingMethods() {
        initLateProperties()
        setClickListeners()
        handleBackPressed()
        settingRecyclerView()
        deleteNote()
        animations()
    }

    private fun deleteNote() {
        binding.deleteNote.setOnClickListener {
            CustomDialog.showDialog(this@MainActivity) {
                scope.launch(Dispatchers.IO) {
                    noteDao.deleteNote()
                    noteAdapter.submitList(noteDao.getAllNotes())
                }
            }
        }
    }

    private fun setClickListeners() {
        binding.createNoteButton.setOnClickListener {
            newActivity(this, AddNotesActivity::class.java)
        }
    }

    private fun animations() {
        val createNoteButton = binding.createNoteButton
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private var isButtonVisible = true
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val buttonHeight = createNoteButton.height.toFloat()

                if (dy > 0 && isButtonVisible) {
                    createNoteButton.animate()
                        .translationY(buttonHeight)
                        .setDuration(200)
                        .withEndAction { createNoteButton.visibility = View.GONE }

                    isButtonVisible = false
                } else if (dy < 0 && !isButtonVisible) {
                    createNoteButton.visibility = View.VISIBLE
                    createNoteButton.translationY = buttonHeight
                    createNoteButton.animate().translationY(0f).duration = 200
                    isButtonVisible = true
                }
            }
        })
    }

    private fun initLateProperties() {
        noteAdapter = NotesAdapter()
        noteDao = NoteDatabase.getDatabase(this).getNoteDao()
    }

    private fun settingRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = noteAdapter
        }
    }

    override fun onResume() {
        lifecycleScope.launch(Dispatchers.IO) {
            noteAdapter.submitList(noteDao.getAllNotes())
        }
        super.onResume()
    }

    private fun handleBackPressed() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }


}

