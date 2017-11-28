package io.github.legosteen11.blackjacklib.game

enum class Deck(val cards: Array<Card>) {
    VereenvoudigdDeck(arrayOf(
            Card.Acht, // 2x acht
            Card.Acht,
            Card.Vier, // 2x vier
            Card.Vier,
            Card.Drie, // 4x drie
            Card.Drie,
            Card.Drie,
            Card.Drie,
            Card.Twee, // 4x twee
            Card.Twee,
            Card.Twee,
            Card.Twee
    ))

    ;

    fun getCardsWithoutDuplicates() = setOf(*cards).toTypedArray()
}