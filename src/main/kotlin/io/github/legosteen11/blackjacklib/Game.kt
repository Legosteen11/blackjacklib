package io.github.legosteen11.blackjacklib

import io.github.legosteen11.blackjacklib.game.Card
import io.github.legosteen11.blackjacklib.game.GameType
import io.github.legosteen11.blackjacklib.maths.probability.IProbability
import io.github.legosteen11.blackjacklib.maths.probability.SimpleChance
import java.io.Serializable


data class Game(val gameType: GameType, val safety: IProbability = SimpleChance(0.5)): Serializable {
    private val playersCards = arrayListOf<Card>()
    private var bankCardsAmount = 0

    fun getPlayersCards() = playersCards.toTypedArray()

    /**
     * Get the cards that are left in this game
     *
     * @return Returns a list of cards that are still in this game.
     */
    fun getCardsLeft(): List<Card> {
        val cards = arrayListOf(*gameType.deck.cards)

        playersCards.forEach {
            cards.remove(it)
        }

        return cards
    }

    /**
     * Get the scores with your cards
     *
     * @return Returns a set of scores
     */
    fun getScore(): Set<Int> {
        // yes, I know this code is a mess :)

        val score = hashMapOf<Int, Int>()

        val indexAtCard = hashMapOf<Int, Int>()

        if(playersCards.isEmpty())
            return setOf(0)

        val totalItems = getTotalItems(playersCards) - 1

        val maxItems = 1 + playersCards.sumBy { it.values.size -1 }

        var uppedItems = 0
        var currentCardIndex = 0
        var currentRun = 0
        (0..totalItems).forEach {
            if(currentCardIndex >= playersCards.size) {
                currentCardIndex = 0
                currentRun++
            }

            val card = playersCards[currentCardIndex]
            val currentIndex = indexAtCard[currentCardIndex] ?: 0
            if(card.values.size > currentIndex+1 && currentRun+1 > uppedItems) {
                indexAtCard.put(currentCardIndex, currentIndex + 1)
                uppedItems++
            }
            val cardScore = card.values.getOrNull(currentIndex) ?: 0

            score.put(currentRun, (score[currentRun] ?: 0) + cardScore)
            currentCardIndex++
        }

        return score.values.reversed().let { it.drop((it.size - maxItems).let { if(it > 0) it else 0 }).toSet() }
    }

    private fun getTotalItems(remainingCards: List<Card>, currentPossibilities: Int = 0): Int {
        if(remainingCards.isEmpty())
            return currentPossibilities

        val currentItemSize = remainingCards.first().values.size

        val newPossibilities = currentPossibilities * currentItemSize

        return getTotalItems(remainingCards.drop(1), newPossibilities+currentItemSize)
    }

    /**
     * Register a card you drew
     *
     * @param card The card you drew
     */
    fun drewCard(card: Card) {
        playersCards.add(card)
    }

    /**
     * Register that the bank drew a card
     */
    fun bankDrewCard() {
        bankCardsAmount++
    }

    /**
     * Check whether you've won the game
     *
     * @return Returns true if you've won (winning values contains the sum of your cards), false if you lost or haven't won yet.
     */
    fun getWon() = getScore().any { score ->
        gameType.winningValues.any { winValue ->
            score == winValue
        }
    }

    /**
     * Check whether you've lost the game
     *
     * @return Returns true if you've lost (sum of cards > max sum)
     */
    fun getLost() = getScore().none { score ->
        score <= gameType.maxValue
    }

    /**
     * Get the chance of not losing a game
     *
     * @return Returns an IProbability object
     */
    fun chanceOfNotLosing(): IProbability = SimpleChance(Math.random()) // TODO: Get a formula to calculate the chance of not winning

    /**
     * Check whether you should continue or not
     */
    fun shouldContinue() = chanceOfNotLosing().higherThan(safety)
}