import kotlin.math.pow

fun main() {
    fun part1(input: List<String>): Int =
        parseInput(input)
            .asSequence()
            .map { card ->
                card.numbers.filter { it in card.winningNumbers }
            }
            .map {
                2.0.pow(it.size.dec()).toInt()
            }
            .sum()

    fun part2(input: List<String>): Int =
        parseInput(input)
            .map { card ->
                card.numbers.filter { it in card.winningNumbers }.size
            }
            .foldIndexed(List(input.size) { 1 }) {index, acc, matches ->
                val range = index + 1 .. index + matches
                acc.mapIndexed { idx, e ->
                    if (idx in range) e + acc[index]
                    else e
                }
            }
            .sum()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    part1(testInput).println()
    check(part1(testInput) == 13)
    part2(testInput).println()
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}

private fun parseInput(input: List<String>): List<Card> =
    input.map { line ->
        val (id, winningNumbers, numbers) = line.split(':', '|')
        Card(
            id = id,
            numbers = numbers.trim().split("\\D+".toRegex()).map { it.toInt() },
            winningNumbers = winningNumbers.trim().split("\\D+".toRegex()).map { it.toInt() }
        )
    }

data class Card(val id: String, val numbers: List<Int>, val winningNumbers: List<Int>)