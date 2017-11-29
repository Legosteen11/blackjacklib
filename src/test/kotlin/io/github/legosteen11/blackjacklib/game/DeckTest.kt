package io.github.legosteen11.blackjacklib.game

import org.junit.Test

import org.junit.Assert.*

class DeckTest {

    @Test
    fun getCards() {
        assertEquals(12, Deck.VereenvoudigdDeck.cards.size)
        assertEquals(52, Deck.BlackJackDeck.cards.size)
    }
}