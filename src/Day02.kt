fun main() {

    fun part1(input: List<String>): Int =
        parseInput(input)
            .filter { game ->
                game.rounds.none { round ->
                    round.any {
                        when (it) {
                            is Cube.Red -> it.num > 12
                            is Cube.Green -> it.num > 13
                            is Cube.Blue -> it.num > 14
                        }
                    }
                }
            }
            .sumOf { it.id }

    fun part2(input: List<String>): Int =
        parseInput(input)
            .sumOf { game ->
                game.rounds
                    .flatten()
                    .groupBy { it::class }
                    .mapValues { it.value.maxOf { it.num } }
                    .values
                    .reduce { a, b -> a * b }
            }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    part1(testInput).println()
    check(part1(testInput) == 8)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

private fun parseInput(input: List<String>) = input
    .map { line ->
        val split = line.split(':', ';').toMutableList()
        val game = split.removeFirst().split(' ').last().toInt()
        val rounds = split.map { round ->
            round.split(',').map {
                val (num, type) = it.trim().split(' ')
                when (type) {
                    "red" -> Cube.Red(num.toInt())
                    "blue" -> Cube.Blue(num.toInt())
                    "green" -> Cube.Green(num.toInt())
                    else -> throw Exception()
                }
            }
        }
        Game(game, rounds)
    }

data class Game(val id: Int, val rounds: List<List<Cube>>)
sealed interface Cube {
    val num: Int

    data class Red(override val num: Int) : Cube
    data class Blue(override val num: Int) : Cube
    data class Green(override val num: Int) : Cube
}