fun main() {
    fun part1(input: List<String>): Int =
        input.sumOf { line ->
            "${
                line.first {
                    it.isDigit()
                }
            }${
                line.last {
                    it.isDigit()
                }
            }".toInt()
        }

    fun part2(input: List<String>): Int {
        val textNumbers = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
        val numbers =  (1..9).map { it.toString() } + textNumbers
        return input
            .sumOf { line ->
                val first = line.findAnyOf(numbers)!!.second
                val second = line.findLastAnyOf(numbers)!!.second

                val convertToNum = fun(s: String): Int = s.toIntOrNull() ?: (textNumbers.indexOf(s) + 1)

                convertToNum(first) * 10 + convertToNum(second)
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    part2(testInput).println()
    check(part2(testInput) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
