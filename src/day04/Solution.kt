package day04

import readInput
import kotlin.math.pow

data class Card(
    val id: Int,
    val winningNumbers: List<Int>,
    val numbersYouHave: List<Int>,
)

data class CardWithValue(
    val card: Card,
    val winCount: Int,
    val value: Int,
)

fun parseNumbers(line: String): List<Int> =
    line.split(" ").filter { it != "" }.map { it.toInt() }

fun parseCard(line: String): Card {
    val (idPart, rest) = line.split(":")
    val id = idPart.substring(4).trim().toInt()
    val (winningNumbersPart, numbersYouHavePart) = rest.split("|")
    val winningNumbers = parseNumbers(winningNumbersPart)
    val numbersYouHave = parseNumbers(numbersYouHavePart)
    return Card(id, winningNumbers, numbersYouHave)
}

fun evaluateCard(card: Card): CardWithValue {
    val winCount = card.numbersYouHave.count { card.winningNumbers.contains(it) }
    if (winCount == 0) return CardWithValue(card, 0, 0)
    return CardWithValue(card, winCount, 2.0.pow(winCount.toDouble() - 1.0).toInt())
}

fun part1(input: List<String>): Int {
    val cards = input.map(::parseCard)
    val withValues = cards.map(::evaluateCard)
    return withValues.sumOf { it.value }
}

fun part2(input: List<String>): Int {
    val cards = input.map(::parseCard)
    val withValues = cards.map(::evaluateCard)

    val numberOfCardsById = cards.associate { it.id to 1 }.toMutableMap()
    for (cv in withValues) {
        val copiesOfThis = numberOfCardsById[cv.card.id] ?: 1
        val wonCopies = generateSequence(cv.card.id + 1) { it + 1 }.take(cv.winCount).toList()
        println("I have $copiesOfThis instances of card ${cv.card.id} which has score ${cv.winCount} and wins an extra copy of cards $wonCopies")
        for (id in wonCopies) {
            numberOfCardsById.compute(id) { _, v -> copiesOfThis + (v ?: 0) }
        }
    }
    return numberOfCardsById.values.sum()
}

fun main() {
    val input = readInput("day04/input")
    println("solution part 1: ${part1(input)}")
    println("solution part 2: ${part2(input)}")
}
