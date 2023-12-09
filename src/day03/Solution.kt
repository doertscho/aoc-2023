package day03

import readInput

data class Position(
    val line: Int,
    val column: Int,
)

data class NumberCandidate(
    val number: Int,
    val positions: List<Position>,
)

data class Symbol(
    val symbol: String,
    val position: Position,
    val ratio: Int = 0
)

fun findNumbers(line: IndexedValue<String>): List<NumberCandidate> {
    val matches = Regex("[0-9]+").findAll(line.value)
    return matches.toList().map { mr ->
        val parsedNumber = mr.value.toInt()
        NumberCandidate(parsedNumber, mr.range.map { col -> Position(line.index, col) })
    }
}

fun findSymbols(line: IndexedValue<String>): List<Symbol> {
    val matches = Regex("[^0-9.]").findAll(line.value)
    return matches.toList().map { mr ->
        Symbol(mr.value, Position(line.index, mr.range.first))
    }
}

fun parseInput(lines: List<String>): Pair<List<NumberCandidate>, List<Symbol>> {
    val numbers = lines.withIndex().flatMap(::findNumbers)
    val symbols = lines.withIndex().flatMap(::findSymbols)
    return Pair(numbers, symbols)
}

fun isAdjacent(posA: Position, posB: Position): Boolean {
    return posA.line in (posB.line - 1) .. (posB.line + 1)
            && posA.column in (posB.column - 1) .. (posB.column + 1)
}

fun part1(input: List<String>): Int {
    val (numbers, symbols) = parseInput(input)
    val partNumbers = numbers.filter { nc ->
        nc.positions.any { numberPos ->
            symbols.any { isAdjacent(it.position, numberPos) }
        }
    }
    return partNumbers.sumOf { it.number }
}

fun part2(input: List<String>): Int {
    val (numbers, symbols) = parseInput(input)
    val gears = symbols.filter { it.symbol == "*" }.mapNotNull { sym ->
        val adjacentNumbers = numbers.filter { num ->
            num.positions.any { isAdjacent(it, sym.position) }
        }
        if (adjacentNumbers.size != 2) null else {
            sym.copy(ratio = adjacentNumbers.first().number * adjacentNumbers.last().number)
        }
    }
    return gears.sumOf { it.ratio }
}

fun main() {
    val input = readInput("day03/input")
    println("solution part 1: ${part1(input)}")
    println("solution part 2: ${part2(input)}")
}
