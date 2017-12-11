package io.github.legosteen11.blackjacklib.maths.probability

import java.io.Serializable

/**
 * A simple chance of something happening
 *
 * @param chance The chance of something happening between 0 and 1
 */
data class SimpleChance(val chance: Double): IProbability, Serializable {
    override fun getChanceInPercent(): Int = (chance*100).toInt()

    fun invert() = SimpleChance(1.000-chance)
}