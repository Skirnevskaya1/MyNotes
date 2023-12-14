package com.homework.mynotes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.homework.mynotes.MainActivity
import com.homework.mynotes.R
import com.homework.mynotes.dataNotes.CardData
import com.homework.mynotes.dataNotes.CardsSourceFirebaseImpl
import com.homework.mynotes.dataNotes.NotesData
import com.homework.mynotes.interfases.CardsSource


class NoteRecyclerAdapter(val context: Context) :
    RecyclerView.Adapter<NoteRecyclerAdapter.Holder>() {

    internal interface Listener {
        fun notesListItemClicked(id: Int, idCard: String)
    }

    var dataSource: CardsSource = CardsSourceFirebaseImpl()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var mRecentlyDeletedItem: CardData? = null
    private var mRecentlyDeletedItemPosition: Int? = null

    class Holder(itemView: View, context: Context, private val dataSource: CardsSource) :
        RecyclerView.ViewHolder(itemView) {
        private val titleView: TextView = itemView.findViewById(R.id.title)
        private val descriptionView: TextView = itemView.findViewById(R.id.description)
        var cardId = ""
//        private val dateView: TextView = itemView.findViewById(R.id.date)

        private val listener: Listener = context as Listener

        fun bind(position: Int) {
            val notesData = dataSource.getCardData(position)
            titleView.text = if(notesData.title == null) "" else notesData.title
            descriptionView.text = if(notesData.description == null) "" else notesData.description
            cardId = notesData.id
//            dateView.text = DateFormat.getDateInstance().format(notesData.dateOFCreation.time)
        }

        fun setListener(id: Int, idCard: String) {
            itemView.setOnClickListener {
                listener.notesListItemClicked(id, idCard)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.note_list_item,
                parent, false
            ), context, dataSource
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
        holder.setListener(position, holder.cardId)
    }

    override fun getItemCount(): Int {
        return dataSource.size()
    }

    fun deleteItem(adapterPosition: Int) {
        mRecentlyDeletedItem = dataSource.getCardData(adapterPosition)
        mRecentlyDeletedItemPosition = adapterPosition
        dataSource.deleteCardData(adapterPosition)
        notifyItemRemoved(adapterPosition)
        showUndoSnack()
    }

    private fun showUndoSnack() {
        if (context is MainActivity) {
            val view: View = context.findViewById(R.id.main_frag)

            val snack: Snackbar = Snackbar.make(
                view, R.string.snack_bar_text,
                Snackbar.LENGTH_LONG
            )
            snack.setAction(R.string.snack_bar_undo) { undoDelete() }
            snack.show()
        }

    }

    private fun undoDelete() {
        dataSource.addCardData(
            mRecentlyDeletedItem!!
        )
//        notifyItemInserted(mRecentlyDeletedItemPosition!!)
        notifyItemInserted(dataSource.size())
    }


}