package com.homework.mynotes.interfases

import com.homework.mynotes.dataNotes.CardsSourceFirebaseImpl

interface CardsSourceResponse {
    fun initialized(cardsData: CardsSourceFirebaseImpl)
    fun added(cardsData: CardsSourceFirebaseImpl)
    fun modified(position: Int)
    fun removed(position: Int)
}