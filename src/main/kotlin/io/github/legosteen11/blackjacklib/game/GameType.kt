package io.github.legosteen11.blackjacklib.game

enum class GameType(val deck: Deck, val winningValues: Set<Int>, val maxValue: Int, val niceName: String) {
    VereenvoudigdGame(
            Deck.VereenvoudigdDeck,
            setOf(10, 11),
            11,
            "Vereenvoudigd Spel")

    ;

    override fun toString(): String = niceName
}