package com.homework.mynotes.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.homework.mynotes.R
import com.homework.mynotes.dataNotes.CardsSourceFirebaseImpl
import com.homework.mynotes.dataNotes.NotesData


class NotesFragment : Fragment() {

    companion object {
        fun getNewID(): Int {
            return NotesData.getNewIdNote()
        }

        const val NOTE_ID = "noteId"
    }

    private var noteId = 0
    var cardId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            noteId = savedInstanceState.getInt(NOTE_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onStart() {
        super.onStart()
        fillViews()
    }

    private fun fillViews() {
        if (NotesData.haveId(noteId)) {
            val cardData = CardsSourceFirebaseImpl.currentFireBase.getCardData(noteId)
            view?.findViewById<TextView>(R.id.titleNotes)!!.text = cardData.title
            view?.findViewById<TextView>(R.id.detailNotes)!!.text =
                cardData.description
        }

    }

    fun setNoteId(id: Int) {
        noteId = id
    }

    fun setIdCard(id: String) {
        cardId = id
    }

    fun getTitle(): String {
        return requireView().findViewById<TextView>(R.id.titleNotes).text.toString()
    }

    fun getDescription(): String {
        return requireView().findViewById<TextView>(R.id.detailNotes).text.toString()
    }

    fun getIdNote(): Int {
        return noteId
    }

    fun getIDCard(): String {
        return cardId
    }

}