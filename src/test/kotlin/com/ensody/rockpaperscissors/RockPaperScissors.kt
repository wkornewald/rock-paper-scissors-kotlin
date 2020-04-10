package com.ensody.rockpaperscissors

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isIn
import assertk.assertions.isNotIn
import org.junit.jupiter.api.Test

class RockPaperScissorsTest {
    @Test
    fun rockPaperScissors() {
        assertThat(Action.ROCK.fight(Action.ROCK)).isEqualTo(FightResult.DRAW)
        assertThat(Action.ROCK.fight(Action.PAPER)).isEqualTo(FightResult.LOSE)
        assertThat(Action.ROCK.fight(Action.SCISSORS)).isEqualTo(FightResult.WIN)
        assertThat(Action.PAPER.fight(Action.ROCK)).isEqualTo(FightResult.WIN)
        assertThat(Action.PAPER.fight(Action.PAPER)).isEqualTo(FightResult.DRAW)
        assertThat(Action.PAPER.fight(Action.SCISSORS)).isEqualTo(FightResult.LOSE)
        assertThat(Action.SCISSORS.fight(Action.ROCK)).isEqualTo(FightResult.LOSE)
        assertThat(Action.SCISSORS.fight(Action.PAPER)).isEqualTo(FightResult.WIN)
        assertThat(Action.SCISSORS.fight(Action.SCISSORS)).isEqualTo(FightResult.DRAW)
    }
}
