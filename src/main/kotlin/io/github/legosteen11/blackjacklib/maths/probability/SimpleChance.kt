package io.github.legosteen11.blackjacklib.maths.probability

/**
 * A simple chance of something happening
 *
 * @param chance The chance of something happening between 0 and 1
 */
class SimpleChance(val chance: Double): IProbability {
    override fun getChanceInPercent(): Double = chance*100
}