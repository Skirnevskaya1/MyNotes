package com.homework.mynotes.notes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.ListFragment
import com.homework.mynotes.dataNotes.NotesData

class NotesListFragment : ListFragment() {

    internal interface Listener {
        fun notesListItemClicked(id: Int)
    }

    private var listener: Listener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val names = Array(NotesData.listNotes.size) { i ->
            NotesData.listNotes[i].title
        }

        listAdapter = ArrayAdapter(
            inflater.context, android.R.layout.simple_list_item_1,
            names
        )

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as Listener
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        listener?.notesListItemClicked(position)
    }
}