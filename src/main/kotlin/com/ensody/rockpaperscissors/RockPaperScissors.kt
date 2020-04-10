package com.ensody.rockpaperscissors

enum class FightResult {
    WIN, LOSE, DRAW
}

enum class Action {
    ROCK, PAPER, SCISSORS
}

// Rules map winner to loser
private val rules = setOf(
    Action.ROCK to Action.SCISSORS,
    Action.PAPER to Action.ROCK,
    Action.SCISSORS to Action.PAPER
)

/** Plays a game of Rock-Paper-Scissors against [enemy] and returns [FightResult]. */
fun Action.fight(enemy: Action): FightResult = when {
    this == enemy -> FightResult.DRAW
    this to enemy in rules -> FightResult.WIN
    else -> FightResult.LOSE
}
