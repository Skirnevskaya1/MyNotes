package com.homework.mynotes.interfases

import com.homework.mynotes.dataNotes.CardData

interface CardsSource {
    fun init(cardsSourceResponse: CardsSourceResponse): CardsSource
    fun getCardData(position: Int): CardData
    fun size(): Int
    fun deleteCardData(position: Int)
    fun updateCardData(cardData: CardData)
    fun addCardData(cardData: CardData)
    fun clearCardData()
}