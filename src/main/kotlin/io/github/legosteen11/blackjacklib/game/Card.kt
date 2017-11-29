package io.github.legosteen11.blackjacklib.game

enum class Card(vararg val values: Int) {
    Boer(10),
    Vrouw(10),
    Heer(10),
    Aas(1, 11),
    Tien(10),
    Negen(9),
    Acht(8),
    Zeven(7),
    Zes(6),
    Vijf(5),
    Vier(4),
    Drie(3),
    Twee(2)
}