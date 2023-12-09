package day02

import readInput

data class Draw(
    val red: Int,
    val blue: Int,
    val green: Int
)

data class Game(
    val id: Int,
    val draws: List<Draw>
)

fun parseGame(line: String): Game {
    val (idPart, drawsPart) = line.split(":")
    val id = idPart.substring(5).toInt()
    val draws = drawsPart.split(";").map { drawText ->
        val drawsByColour = drawText.split(",").map {
            val (number, colour) = it.trim().split(" ")
            Pair(colour, number.toInt())
        }

        fun find(search: String) = drawsByColour.find { (colour, _) -> colour == search }?.second ?: 0
        Draw(find("red"), find("blue"), find("green"))
    }

    return Game(id, draws)
}

fun part1(input: List<String>): Int {
    val games = input.map(::parseGame)
    val possibleGames = games.filter { game ->
        game.draws.none { it.red > 12 || it.blue > 14 || it.green > 13 }
    }
    return possibleGames.sumOf { it.id }
}

fun calculatePower(game: Game): Int {
    val maxRed = game.draws.maxOfOrNull { it.red } ?: error("""no red 😱""")
    val maxBlue = game.draws.maxOfOrNull { it.blue } ?: error("""no blue 😱""")
    val maxGreen = game.draws.maxOfOrNull { it.green } ?: error("""no green 😱""")
    println("${game.id}: $maxRed $maxBlue $maxGreen")
    return maxRed * maxBlue * maxGreen
}

fun part2(input: List<String>): Int {
    val games = input.map(::parseGame)
    val powers = games.map(::calculatePower)
    return powers.sum()
}

fun main() {
    val input = readInput("day02/input")
    println("solution part 1: ${part1(input)}")
    println("solution part 2: ${part2(input)}")
}
