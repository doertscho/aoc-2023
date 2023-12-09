package day01

import println
import readInput

fun sumOfDigits(input: List<String>): Int {
    val numbers = input.map { line -> line.toCharArray().filter { it.isDigit() }.map { it.digitToInt() } }
    val resultingNumbers = numbers.map { it.first() * 10 + it.last() }
    numbers.println()
    resultingNumbers.println()
    return resultingNumbers.sum()
}

fun part1(input: List<String>): Int {
    return sumOfDigits(input)
}

fun part2(input: List<String>): Int {
    // as per the example, "twone" maps to 2 and to 1,
    // so we can't just replace all occurrences of "one" first.
    // this is not the cleanest solution but probably a quick one
    val inputWithResolvedDigits = input.map {
        it.replace("one", "one1one")
            .replace("two", "two2two")
            .replace("three", "three3three")
            .replace("four", "four4four")
            .replace("five", "five5five")
            .replace("six", "six6six")
            .replace("seven", "seven7seven")
            .replace("eight", "eight8eight")
            .replace("nine", "nine9nine")
    }
    inputWithResolvedDigits.println()
    return sumOfDigits(inputWithResolvedDigits)
}

fun main() {
    val input = readInput("day01/input")
    println("solution part 1: ${part1(input)}")
    println("solution part 2: ${part2(input)}")
}
