package kr.ac.kumoh.s20191091.s24w12carddealer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import kr.ac.kumoh.s20191091.s24w12carddealer.CardModel.Companion.NUMBER_OF_CARDS
import kotlin.random.Random

class CardViewModel : ViewModel() {
    private val cardModel = CardModel()

    private val _cards = MutableLiveData<List<String>>()
    val cards: LiveData<List<String>>
        get() = _cards

    private val _cardRank = MutableLiveData<String>("")
    val cardRank: LiveData<String>
        get() = _cardRank

    init{
       val newCards = listOf(
           "c_10_of_spades",
           "c_jack_of_spades2",
           "c_queen_of_spades2",
           "c_king_of_spades2",
           "c_ace_of_spades"
       )
        _cards.value = newCards
    }

    fun shuffle(count: Int = NUMBER_OF_CARDS) {
        val newCards = cardModel.dealCards()
        _cards.value = newCards.sorted().map { getCardName(it) }
    }

    fun check() {
        val modelCards = cardModel.cards
        val numbers = modelCards.map { getNumber(it) }
        val shapes = modelCards.map { getShape(it) }

        _cardRank.value = when {
            isStraightFlush(numbers, shapes) -> "스트레이트 플러시"
            isFourOfAKind(numbers) -> "포카드"
            isFullHouse(numbers) -> "풀 하우스"
            isFlush(shapes) -> "플러시"
            isStraight(numbers) -> "스트레이트"
            isThreeOfAKind(numbers) -> "트리플"
            isTwoPair(numbers) -> "투페어"
            isOnePair(numbers) -> "원페어"
            else -> "노페어"
        }
    }

    private fun isStraightFlush(numbers: List<String>, shapes: List<String>): Boolean {
        return isStraight(numbers) && isFlush(shapes)
    }

    private fun isFourOfAKind(numbers: List<String>): Boolean {
        return numbers.groupBy { it }.any { it.value.size == 4 }
    }

    private fun isFullHouse(numbers: List<String>): Boolean {
        val grouped = numbers.groupBy { it }
        return grouped.any { it.value.size == 3 } && grouped.any { it.value.size == 2 }
    }

    private fun isFlush(shapes: List<String>): Boolean {
        return shapes.distinct().size == 1
    }

    private fun isStraight(numbers: List<String>): Boolean {
        val sorted = numbers.sorted()
        return sorted.zipWithNext().all { it.second == it.first + 1 }
    }

    private fun isThreeOfAKind(numbers: List<String>): Boolean {
        return numbers.groupBy { it }.any { it.value.size == 3 }
    }

    private fun isTwoPair(numbers: List<String>): Boolean {
        return numbers.groupBy { it }.filter { it.value.size == 2 }.size == 2
    }

    private fun isOnePair(numbers: List<String>): Boolean {
        return numbers.groupBy { it }.any { it.value.size == 2 }
    }

    private fun getShape(c: Int): String{
        val shape = when (c / 13) {
            0 -> "spades"
            1 -> "diamonds"
            2 -> "hearts"
            3 -> "clubs"
            else -> "error"
        }
        return shape
    }

    private fun getNumber(c: Int): String{
        val number = when (c % 13) {
            0 -> "ace"
            in 1..9 -> (c % 13 + 1).toString()
            10 -> "jack"
            11 -> "queen"
            12 -> "king"
            else -> "error"
        }
        return number
    }

    private fun getCardName(c: Int): String {
        val shape = getShape(c)
        val number = getNumber(c)

        return if (c % 13 in 10..12)
            "c_${number}_of_${shape}2"
        else
            "c_${number}_of_${shape}"
    }
}