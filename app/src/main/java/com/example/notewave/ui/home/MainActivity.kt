package com.example.notewave.ui.home

import android.opengl.Visibility
import android.os.Bundle
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
import com.example.notewave.utils.setStatusBarAppearance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var noteAdapter: NotesAdapter
    private lateinit var noteDao: NoteDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setStatusBarAppearance(window.decorView.rootView)

        noteAdapter = NotesAdapter()
        noteDao = NoteDatabase.getDatabase(this).getNoteDao()

        //Functions
        setClickListeners()
        handleBackPressed()
        settingNoteAdapter()
    }

    private fun settingNoteAdapter() {
        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = noteAdapter
        }
    }

    private fun setClickListeners() {

        binding.createNoteButton.setOnClickListener {
            newActivity(this, AddNotesActivity::class.java)
        }

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

