package com.example.notewave.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notewave.R
import com.example.notewave.dataBase.Note
import java.util.Random

class NotesAdapter(
    context: Context,
    private val itemClickListener: NotesClickListener
) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    private val noteList = ArrayList<Note>()
    private val fullList = ArrayList<Note>()

    interface NotesClickListener {
        fun onItemClick(note: Note)
        fun onLongItemClick(note: Note, cardView: CardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemPosition = noteList[position]
        holder.title.text = itemPosition.title
        holder.title.isSelected = true
        holder.note.text = itemPosition.note
        holder.date.text = itemPosition.date
        holder.date.isSelected = true
        holder.noteLayout.setCardBackgroundColor(
            holder.itemView.resources.getColor(
                randomColor(),
                null
            )
        )
        holder.noteLayout.setOnClickListener {
            itemClickListener.onItemClick(noteList[holder.adapterPosition])
        }
        holder.noteLayout.setOnLongClickListener {
            itemClickListener.onLongItemClick(noteList[holder.adapterPosition], holder.noteLayout)
            true
        }
    }

    fun update(newList: List<Note>) {
        fullList.clear()
        fullList.addAll(newList)

        noteList.clear()
        noteList.addAll(fullList)
        notifyDataSetChanged()
    }

    fun filterList(search: String) {
        noteList.clear()
        for (item in fullList) {
            if (item.title?.lowercase()?.contains(search.lowercase()) == true ||
                item.note?.lowercase()?.contains(search.lowercase()) == true
            ) {
                noteList.add(item)
            }
        }
        notifyDataSetChanged()
    }

    private fun randomColor(): Int {
        val list = ArrayList<Int>()
        val randomIndex: Int
        list.apply {
            add(R.color.notec1)
            add(R.color.notec2)
            add(R.color.notec3)
            add(R.color.notec4)
            add(R.color.notec5)
            add(R.color.notec6)

            val seed = System.currentTimeMillis()
            randomIndex = Random(seed).nextInt(list.size)
        }
        return list[randomIndex]
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val noteLayout: CardView = itemView.findViewById(R.id.notes_layout)
        val title: TextView = itemView.findViewById(R.id.tv_title)
        val note: TextView = itemView.findViewById(R.id.tv_note)
        val date: TextView = itemView.findViewById(R.id.tv_date)
    }

}

