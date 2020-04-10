package com.ensody.rockpaperscissors

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class GamesTest {
    @Test
    fun numberOfGames() {
        val times = 8
        assertThat(play(times).results.values.sum()).isEqualTo(times)
    }

    @Test
    fun formatGameStats() {
        val stats = GameStats(mapOf(FightResult.WIN to 10))
        val formatted = stats.format() + "\n"
        assertThat(formatted).contains("Player 1 wins: 10\n")
        assertThat(formatted).contains("Player 2 wins: 0\n")
        assertThat(formatted).contains("Draw: 0\n")
    }
}
