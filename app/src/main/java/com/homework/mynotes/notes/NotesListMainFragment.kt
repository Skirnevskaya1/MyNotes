package com.homework.mynotes.notes

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.homework.mynotes.MainActivity
import com.homework.mynotes.R
import com.homework.mynotes.SwipeToDeleteCallback
import com.homework.mynotes.adapters.NoteRecyclerAdapter
import com.homework.mynotes.dataNotes.CardData
import com.homework.mynotes.dataNotes.CardsSourceFirebaseImpl
import com.homework.mynotes.interfases.CardsSource
import com.homework.mynotes.interfases.CardsSourceResponse


class NotesListMainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_list, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        val recyclerAdapter = NoteRecyclerAdapter(layoutInflater.context)
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(recyclerAdapter))

        val dataAdapter = CardsSourceFirebaseImpl().init(object : CardsSourceResponse {
            @SuppressLint("NotifyDataSetChanged")
            override fun initialized(cardsData: CardsSourceFirebaseImpl) {
                recyclerAdapter.notifyDataSetChanged()
            }

            override fun added(cardsData: CardsSourceFirebaseImpl) {
                recyclerAdapter.notifyItemInserted(cardsData.size())
            }

            override fun modified(position: Int) {
                recyclerAdapter.notifyItemChanged(position)
            }

            override fun removed(position: Int) {
                recyclerAdapter.notifyItemRemoved(position)
            }
        })

        recyclerAdapter.dataSource = dataAdapter

        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = recyclerAdapter
        }
        setHasOptionsMenu(true)

        itemTouchHelper.attachToRecyclerView(recyclerView)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intiFABAdd()
    }


    private fun intiFABAdd() {
        view?.findViewById<View>(R.id.fab_add)?.setOnClickListener {
            val curActivity = requireActivity()
            if (curActivity is MainActivity) {
                curActivity.openNewNotes()
            }
        }
    }
}