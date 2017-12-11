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
        val scores = hashSetOf<Int>()

        val scoresSize = playersCards.sumBy { it.values.size - 1 }

        val alternativeCardValues = hashMapOf<Int, Int>()

        for(currentRun in 0..scoresSize) {
            // score for current run
            var score = 0

            // there should only be one added alternative card value per run
            var addedAlternativeCardValue = false

            playersCards.forEachIndexed { index, card ->
                // the current index for the value, if an alternative is not set, it will default to 0 (first item)
                val valueIndex = alternativeCardValues[index] ?: 0
                // the current (alternative) value for the card
                val value = card.values[valueIndex]

                // add the value of the card to the score
                score += value

                /*
                Only up the alternative card value if:
                    there is more than one value for this card
                    the current valueIndex+1 is less than the amount of values for this card
                    there wasn't already another alternative card value added this run
                 */
                if(card.values.size > 1 && valueIndex+1 < card.values.size && !addedAlternativeCardValue) {
                    // add the alternative value index
                    alternativeCardValues.put(index, valueIndex+1)
                    // set the register
                    addedAlternativeCardValue = true
                }
            }

            scores.add(score)
        }

        if(scores.isEmpty())
            scores.add(0)

        return scores
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
     * @return Returns a SimpleChance object
     */
    fun chanceOfNotLosing(): SimpleChance = chanceOfLosing().invert()

    /**
     * Get the chance of losing a game
     *
     * @return Returns a SimpleChance object
     */
    fun chanceOfLosing(): SimpleChance = SimpleChance(getCardsLeft().count { cardWouldLoseTheGame(it) }.toDouble() / getCardsLeft().size.toDouble())

    private fun cardWouldLoseTheGame(card: Card) = (this.getScore().sorted().first() + (card.values.sorted().firstOrNull() ?: 0)) > gameType.maxValue

    /**
     * Check whether you should continue or not
     */
    fun shouldContinue() = chanceOfNotLosing().higherThan(safety)
}