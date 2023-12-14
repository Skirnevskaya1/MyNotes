package com.homework.mynotes.dataNotes

import android.util.Log
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.homework.mynotes.interfases.CardsSource
import com.homework.mynotes.interfases.CardsSourceResponse


class CardsSourceFirebaseImpl : CardsSource {

    private val CARDS_COLLECTION = "Notes"
    private val TAG = "[CardsSourceFirebaseImpl]"
    private val store = FirebaseFirestore.getInstance()
    private val collection = store.collection(CARDS_COLLECTION)
    private var cardsData: List<CardData> = ArrayList()

    companion object{
        lateinit var currentFireBase: CardsSourceFirebaseImpl
    }

    override fun init(cardsSourceResponse: CardsSourceResponse): CardsSourceFirebaseImpl {

        collection.addSnapshotListener { value, error ->

            if (error != null) {
                Log.e("FireBase error", error.message.toString())
                return@addSnapshotListener
            }

            value?.documentChanges?.forEach {
                val doc = it.document.data
                val id = it.document.id
                val cardData = CardDataMapping.toCardData(id, doc)
                when (it.type) {
                    DocumentChange.Type.ADDED -> {
                        (cardsData as ArrayList<CardData>).add(cardData)
                        cardsSourceResponse.added(this)
                    }
                    DocumentChange.Type.MODIFIED -> {
                        val elementArray = cardsData.find { elArray -> elArray.id == cardData.id }
                        if (elementArray != null) {
                            elementArray.title = cardData.title
                            elementArray.description = cardData.description
                            cardsSourceResponse.modified(cardsData.indexOf(elementArray))
                        }
                    }
                    DocumentChange.Type.REMOVED -> {
                        val elementArray = cardsData.find { elArray -> elArray.id == cardData.id }
                        if (elementArray != null) {
                            val indexRemove = cardsData.indexOf(elementArray)
                            (cardsData as ArrayList<CardData>).removeAt(indexRemove)
                            cardsSourceResponse.removed(indexRemove)
                        }

                    }
                    else -> {
                        Log.e("Firebase unknown event", it.type.toString())
                    }
                }
            }
            cardsSourceResponse.initialized(this)

        }
        currentFireBase = this
        return this
    }


    override fun getCardData(position: Int): CardData {
        return cardsData[position]
    }

    override fun size(): Int {
        return cardsData.size
    }

    override fun deleteCardData(position: Int) {
        collection.document(cardsData[position].id).delete()
        (cardsData as ArrayList<CardData>).removeAt(position)
    }

    override fun updateCardData(cardData: CardData) {
        collection.document(cardData.id).set(cardData)
        val elementArray = cardsData.find { elArray -> elArray.id == cardData.id }
        if (elementArray != null) {
            elementArray.title = cardData.title
            elementArray.description = cardData.description
        }
    }

    override fun addCardData(cardData: CardData) {
        collection.add(CardDataMapping.toDocument(cardData))
            .addOnSuccessListener { documentReference ->
                cardData.id = documentReference.id
            }
    }

    override fun clearCardData() {
        cardsData.forEach { collection.document(it.id).delete() }
        cardsData = ArrayList()
    }

    fun replaceNote(cardData: CardData) {

        if (cardData.id.isEmpty()) {
            addCardData(cardData)
        } else {
            updateCardData(cardData)
        }
    }
}