package com.ensody.rockpaperscissors

data class GameStats(val results: Map<FightResult, Int>)

fun play(times: Int): GameStats {
    val results = mutableMapOf<FightResult, Int>().withDefault { 0 }
    repeat(times) {
        val result = Action.values().random().fight(Action.ROCK)
        results[result] = results.getValue(result) + 1
    }
    return GameStats(results)
}

fun GameStats.format(): String = """
    Player 1 wins: ${results[FightResult.WIN] ?: 0}
    Player 2 wins: ${results[FightResult.LOSE] ?: 0}
    Draw: ${results[FightResult.DRAW] ?: 0}
    """.trimIndent().trim()
