package com.example.notewave.ui.home

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var noteDao: NoteDao
    private lateinit var noteAdapter: NotesAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager2: StaggeredGridLayoutManager
    private val scope = lifecycleScope
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setStatusBarAppearance(window.decorView.rootView)
        handlingMethods()
    }

    private fun handlingMethods() {
        setClickListeners()
        initiatingLateinitProperties()
        settingRecyclerView()
        deleteAllNote()
        animations()
        handleBackPressed()
    }

    private fun setClickListeners() {
        binding.createNoteButton.setOnClickListener {
            newActivity(this, AddNotesActivity::class.java)
        }
    }

    private fun initiatingLateinitProperties() {
        noteDao = NoteDatabase.getDatabase(this).getNoteDao()
        noteAdapter = NotesAdapter { id -> deleteNoteById(id) }

    }

    private fun settingRecyclerView() {
        recyclerView = binding.recyclerView
        layoutManager2 = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        recyclerView.apply {
            layoutManager = layoutManager2
            adapter = noteAdapter
        }
    }

    private fun gettingInitialNotes(){
        lifecycleScope.launch(Dispatchers.IO) {
            noteAdapter.submitList(noteDao.getAllNotes())
            launch(Dispatchers.Main){
                delay(150)
                recyclerView.scrollToPosition(0)
            }
        }
    }

    private fun deleteAllNote() {
        binding.deleteNote.setOnClickListener {
            CustomDialog.showDialog(this@MainActivity) {
                scope.launch(Dispatchers.IO) {
                    noteDao.deleteAllNote()
                    noteAdapter.submitList(noteDao.getAllNotes())
                }
            }
        }
    }

    private fun deleteNoteById(id: Int) {
        scope.launch(Dispatchers.IO) {
            noteDao.deleteById(id)
            noteAdapter.submitList(noteDao.getAllNotes())
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

    private fun handleBackPressed() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val builder = AlertDialog.Builder(this@MainActivity)
                with(builder) {
                    setMessage("Close application!")
                    setPositiveButton("Yes") { _, _ ->
                        exitProcess(0)
                    }
                    setNegativeButton("No") { dialog, _ ->
                        dialog.cancel()
                    }
                    setCancelable(false)
                    create()
                    show()
                }

            }
        })
    }

    override fun onResume() {
       gettingInitialNotes()
        super.onResume()
    }

}

