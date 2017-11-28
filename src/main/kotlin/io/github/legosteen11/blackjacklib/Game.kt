package io.github.legosteen11.blackjacklib

import io.github.legosteen11.blackjacklib.game.Card
import io.github.legosteen11.blackjacklib.game.GameType
import io.github.legosteen11.blackjacklib.probability.IProbability
import io.github.legosteen11.blackjacklib.probability.SimpleChance

data class Game(val gameType: GameType, val safety: IProbability = SimpleChance(0.5)) {
    private val playersCards = arrayListOf<Card>()
    private var bankCardsAmount = 0

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

    fun getTotalValueLeft() = getCardsLeft().sumBy { it.value }

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
    fun getWon() = playersCards.sumBy { it.value }.let { sum ->
        gameType.winningValues.any {
            it == sum
        }
    }

    /**
     * Check whether you've lost the game
     *
     * @return Returns true if you've lost (sum of cards > max sum)
     */
    fun getLost() = playersCards.sumBy { it.value } > gameType.maxValue

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