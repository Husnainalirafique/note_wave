package com.example.notewave.adapter

import android.os.Bundle
import androidx.recyclerview.widget.ListAdapter
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.notewave.R
import com.example.notewave.db.Note
import com.example.notewave.ui.addNote.AddNotesActivity
import com.example.notewave.utils.newActivity
import com.google.android.material.card.MaterialCardView
import java.util.Random

class NotesAdapter(private val callBack: (Int) -> Unit) : ListAdapter<Note, NotesAdapter.MyViewHolder>(MyDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return MyViewHolder(view,callBack)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MyViewHolder(itemView: View, private val callBack:(Int) -> Unit) : RecyclerView.ViewHolder(itemView) {

        private val title: TextView = itemView.findViewById(R.id.tv_title)
        private val noteDescription: TextView = itemView.findViewById(R.id.tv_note)
        private val cardView: MaterialCardView = itemView.findViewById(R.id.notes_layout)
        private val delImg:ImageView = itemView.findViewById(R.id.deleteSweep)
        fun bind(data: Note) {
            title.text = data.title
            noteDescription.text = data.noteDescription
            cardView.setCardBackgroundColor(itemView.context.getColor(getRandomColor()))
            delImg.setOnClickListener {
                callBack.invoke(data.id)
            }
            itemView.setOnClickListener {
                val bundle = Bundle().apply {
                    putInt("id",data.id)
                    putString("title",data.title)
                    putString("noteDescription",data.noteDescription)
                }
                newActivity(itemView.context,AddNotesActivity::class.java,bundle)
            }
        }

        private fun getRandomColor(): Int{
            val colorList = listOf(
                R.color.notec1,
                R.color.notec2,
                R.color.notec3,
                R.color.notec4,
                R.color.notec5,
                R.color.notec8,
                R.color.notec9,
                R.color.notec10,
            )
            val randomIndex = Random().nextInt(colorList.size)
            return colorList[randomIndex]
        }
    }

    class MyDiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }
}
