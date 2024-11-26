package kr.ac.kumoh.s20191091.s24w12carddealer

import kotlin.random.Random

class CardModel {
    companion object {
        const val TOTAL_CARDS = 52
        const val NUMBER_OF_CARDS = 5
    }

    private val _cards = mutableSetOf<Int>()
    //    public val cards: Set<Int>
    //        get() = _cards

    fun dealCards(n: Int = NUMBER_OF_CARDS): Set<Int> {
        _cards.clear()

        while (_cards.size < n) {
            _cards.add(Random.nextInt(TOTAL_CARDS))
        }

        return _cards
    }
}