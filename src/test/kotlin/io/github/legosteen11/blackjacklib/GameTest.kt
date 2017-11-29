package io.github.legosteen11.blackjacklib

import io.github.legosteen11.blackjacklib.game.Card
import io.github.legosteen11.blackjacklib.game.GameType
import org.junit.Test

import org.junit.Assert.*

class GameTest {

    @Test
    fun getCardsLeft() {
        val gameSimple = Game(GameType.VereenvoudigdGame)
        assertEquals(GameType.VereenvoudigdGame.deck.cards.toList(), gameSimple.getCardsLeft())
        assertEquals(GameType.VereenvoudigdGame.deck.cards.toMutableList().apply { remove(Card.Acht) }, gameSimple.apply { drewCard(Card.Acht) }.getCardsLeft())
    }

    @Test
    fun getScore() {
        val gameSimple = Game(GameType.VereenvoudigdGame)
        assertEquals(setOf(0), gameSimple.getScore())
        gameSimple.drewCard(Card.Acht)
        assertEquals(setOf(8), gameSimple.getScore())
        gameSimple.drewCard(Card.Drie)
        assertEquals(setOf(11), gameSimple.getScore())
        gameSimple.drewCard(Card.Acht)
        assertEquals(setOf(19), gameSimple.getScore())

        val gameBlackJack = Game(GameType.BlackJackGame)
        assertEquals(setOf(0), gameBlackJack.getScore())
        gameBlackJack.drewCard(Card.Acht)
        assertEquals(setOf(8), gameBlackJack.getScore())
        gameBlackJack.drewCard(Card.Acht)
        assertEquals(setOf(16), gameBlackJack.getScore())
        gameBlackJack.drewCard(Card.Drie)
        assertEquals(setOf(19), gameBlackJack.getScore())
        gameBlackJack.drewCard(Card.Aas)
        assertEquals(setOf(20, 30), gameBlackJack.getScore()) // 19 + 1 || 19 + 11
        gameBlackJack.drewCard(Card.Aas)
        assertEquals(setOf(21, 31, 41), gameBlackJack.getScore()) // 19 + 1 + 1 || 19 + 11 + 1 || 19 + 11 + 11
        gameBlackJack.drewCard(Card.Aas)
        assertEquals(setOf(22, 32, 42, 52), gameBlackJack.getScore()) // 19 + 1 + 1 + 1 || 19 + 11 + 1 + 1 || 19 + 11 + 11 + 1 || 19 + 11 + 11 + 11
        gameBlackJack.drewCard(Card.Aas)
        assertEquals(setOf(23, 33, 43, 53, 63), gameBlackJack.getScore()) // 19 + 1 + 1 + 1 || 19 + 11 + 1 + 1 + 1 || 19 + 11 + 11 + 1 + 1 || 19 + 11 + 11 + 11 + 1 || 19 + 11 + 11 + 11 + 11
    }

    @Test
    fun drewCard() {
        val gameSimple = Game(GameType.VereenvoudigdGame)

        assertEquals(emptyArray<Card>(), gameSimple.getPlayersCards())
        gameSimple.drewCard(Card.Acht)
        assertEquals(arrayOf(Card.Acht), gameSimple.getPlayersCards())
        gameSimple.drewCard(Card.Drie)
        assertEquals(arrayOf(Card.Acht, Card.Drie), gameSimple.getPlayersCards())
    }

    @Test
    fun getWon() {
        val gameBlackJack = Game(GameType.BlackJackGame)

        assertEquals(false, gameBlackJack.getWon())
        gameBlackJack.drewCard(Card.Tien)
        assertEquals(false, gameBlackJack.getWon())
        gameBlackJack.drewCard(Card.Aas)
        assertEquals(true, gameBlackJack.getWon())
        gameBlackJack.drewCard(Card.Aas)
        assertEquals(false, gameBlackJack.getWon())

        val gameBlackJack2 = Game(GameType.BlackJackGame)
        assertEquals(false, gameBlackJack2.getWon())
        gameBlackJack2.drewCard(Card.Tien)
        assertEquals(false, gameBlackJack2.getWon())
        gameBlackJack2.drewCard(Card.Negen)
        assertEquals(false, gameBlackJack2.getWon())
        gameBlackJack2.drewCard(Card.Twee)
        assertEquals(true, gameBlackJack2.getWon())
        gameBlackJack2.drewCard(Card.Aas)
        assertEquals(false, gameBlackJack2.getWon())
    }

    @Test
    fun getLost() {

        val gameBlackJack = Game(GameType.BlackJackGame)

        assertEquals(false, gameBlackJack.getLost())
        gameBlackJack.drewCard(Card.Tien)
        assertEquals(false, gameBlackJack.getLost())
        gameBlackJack.drewCard(Card.Aas)
        assertEquals(false, gameBlackJack.getLost())
        gameBlackJack.drewCard(Card.Aas)
        assertEquals(false, gameBlackJack.getLost())
        gameBlackJack.drewCard(Card.Tien)
        assertEquals(true, gameBlackJack.getLost())

        val gameBlackJack2 = Game(GameType.BlackJackGame)
        assertEquals(false, gameBlackJack2.getLost())
        gameBlackJack2.drewCard(Card.Tien)
        assertEquals(false, gameBlackJack2.getLost())
        gameBlackJack2.drewCard(Card.Negen)
        assertEquals(false, gameBlackJack2.getLost())
        gameBlackJack2.drewCard(Card.Twee)
        assertEquals(false, gameBlackJack2.getLost())
        gameBlackJack2.drewCard(Card.Aas)
        assertEquals(true, gameBlackJack2.getLost())
    }

    @Test
    fun chanceOfNotLosing() {
    }

    @Test
    fun shouldContinue() {
    }

    @Test
    fun getPlayersCards() {
        val gameSimple = Game(GameType.VereenvoudigdGame)

        assertEquals(emptyArray<Card>(), gameSimple.getPlayersCards())
        gameSimple.drewCard(Card.Acht)
        assertEquals(arrayOf(Card.Acht), gameSimple.getPlayersCards())
        gameSimple.drewCard(Card.Drie)
        assertEquals(arrayOf(Card.Acht, Card.Drie), gameSimple.getPlayersCards())
    }
}