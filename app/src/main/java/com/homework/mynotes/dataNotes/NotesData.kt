package com.homework.mynotes.dataNotes

import java.util.*


class NotesData(
    var title: String,
    var description: String,
    var dateOFCreation: Calendar = Calendar.getInstance()
) {

    companion object {
        private val currentTime = Calendar.getInstance()
        val listNotes = arrayListOf(
            NotesData("Список покупок", "Молоко\nКорову", currentTime),
            NotesData("Выбросить мусор", "", currentTime)
        )

        fun getNewIdNote(): Int {
            return listNotes.size
        }

        fun haveId(id: Int): Boolean {
            return id < listNotes.size
        }

        fun addNote(notesData: NotesData) {
            if  (notesData.title.isNotEmpty()) listNotes.add(notesData)
        }

        fun getSize(): Int {
            return listNotes.size
        }

        fun findNoteById(id: Int): NotesData {
            return listNotes[id]
        }

        fun replaceNote(id: Int, notesData: NotesData) {
            if (haveId(id)) {
                editNote(id, notesData)
            } else {
                addNote(notesData)
            }
        }

        private fun editNote(id: Int, notesData: NotesData) {
            listNotes[id].title = notesData.title
            listNotes[id].description = notesData.description
        }

        fun remove(id: Int) {
            listNotes.removeAt(id)
        }
    }

}