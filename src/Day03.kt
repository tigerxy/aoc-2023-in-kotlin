fun main() {
    fun part1(input: List<String>): Int =
        parseInput(input)
            .filter { it.symbols.isNotEmpty() }
            .sumOf { it.number }

    fun part2(input: List<String>): Int =
        parseInput(input)
            .asSequence()
            .filter { part -> part.symbols.any { it is Gear } }
            .groupBy { it.symbols.first() }
            .filter { it.value.size == 2 }
            .map { comp ->
                comp.value
                    .map { it.number }
                    .reduce(Int::times)
            }
            .sum()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    part1(testInput).println()
    check(part1(testInput) == 4361)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}

private fun parseInput(input: List<String>): MutableList<PartNumber> {
    val field = input
        .map { it.toCharArray() }
        .mapIndexed { y, line ->
            line.mapIndexed { x, it ->
                when {
                    it.isDigit() -> Digit(it.digitToInt())
                    it == '*' -> Gear(x, y)
                    it == '.' -> Nothing
                    else -> Component
                }
            }
        }
    val partNumbers = mutableListOf<PartNumber>()
    val yMax = input.lastIndex
    val xMax = input[0].lastIndex

    var num = 0
    val symbols = mutableSetOf<Pos>()

    for (y in 0..yMax) {
        for (x in 0..xMax) {
            val current = field[y][x]
            if (current is Digit) {
                num = num * 10 + current.value
                for (dy in y - 1..y + 1)
                    for (dx in x - 1..x + 1) {
                        val maybeSymbol = field.getOrNull(dy)?.getOrNull(dx)
                        if (maybeSymbol != null && (maybeSymbol is Gear || maybeSymbol is Component)) {
                            symbols.add(maybeSymbol)
                        }
                    }
            } else {
                if (num > 0) {
                    partNumbers.add(PartNumber(num, symbols.toSet()))
                }
                symbols.clear()
                num = 0
            }
        }
        if (num > 0) {
            partNumbers.add(PartNumber(num, symbols.toSet()))
        }
        num = 0
        symbols.clear()
    }
    return partNumbers
}

data class PartNumber(val number: Int, val symbols: Set<Pos>)
sealed interface Pos
data class Digit(val value: Int) : Pos
data class Gear(val x: Int, val y: Int) : Pos
data object Nothing : Pos
data object Component : Pos
