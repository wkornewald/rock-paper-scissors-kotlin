package com.ensody.rockpaperscissors

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isIn
import assertk.assertions.isNotIn
import org.junit.jupiter.api.Test

class RockPaperScissorsTest {
    @Test
    fun rockPaperScissors() {
        val wins = setOf(
            Action.ROCK to Action.SCISSORS,
            Action.PAPER to Action.ROCK,
            Action.SCISSORS to Action.PAPER
        )

        for (fighter in Action.values()) {
            for (enemy in Action.values()) {
                val result = fighter.fight(enemy)
                when {
                    fighter == enemy -> assertThat(result).isEqualTo(FightResult.DRAW)
                    fighter to enemy in wins -> assertThat(result).isEqualTo(FightResult.WIN)
                    else -> assertThat(result).isEqualTo(FightResult.LOSE)
                }
            }
        }
    }
}
