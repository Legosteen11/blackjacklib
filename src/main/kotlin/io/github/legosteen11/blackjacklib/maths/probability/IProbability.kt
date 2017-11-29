package io.github.legosteen11.blackjacklib.maths.probability

interface IProbability {
    /**
     * Get the chance of something happening
     *
     * @return The chance of something happening in percents.
     */
    fun getChanceInPercent(): Double

    /**
     * Check whether the chance of this is higher than the chance of [probability]
     *
     * @param probability The other probability
     *
     * @return Returns true if this chance is higher
     */
    fun higherThan(probability: IProbability) = getChanceInPercent() > probability.getChanceInPercent()
}