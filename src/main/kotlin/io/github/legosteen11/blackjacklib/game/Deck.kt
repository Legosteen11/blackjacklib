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
    )),
    BlackJackDeck(
       fullDeck()
    )
    ;

    fun getCardsWithoutDuplicates() = setOf(*cards).toTypedArray()
}

fun arrayOfFour(card: Card) = arrayOf(card, card, card, card)

fun fullDeck(): Array<Card> {
    val cards = arrayListOf<Card>()

    Card.values().map { arrayOfFour(it) }.forEach {
        cards.plusAssign(it)
    }

    return cards.toTypedArray()
}