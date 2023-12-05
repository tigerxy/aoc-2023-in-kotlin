import kotlin.time.measureTimedValue

fun main() {
    fun part1(input: List<String>): Int {
        val seeds = input[0]
            .split("\\D+".toRegex())
            .drop(1)
            .map { it.toLong() }

        return input.drop(2)
            .splitWhen { it.isEmpty() }
            .map { row ->
                row
                    .drop(1)
                    .map { s ->
                        val (des, src, lth) = s.split("\\D+".toRegex()).map { it.toLong() }
                        Pair(src until src + lth, des - src)
                    }
            }
            .fold(seeds) { acc, function1s ->
                acc.map { p -> p + (function1s.firstOrNull { p in it.first }?.second ?: 0) }
            }
            .min()
            .toInt()

    }

    fun part2(input: List<String>): Int = 0

    val day = "05"
    val testExpected = listOf(35, 46)

// test if implementation meets criteria from the description, like:
    val inputs = listOf(readInput("Day${day}_test"), readInput("Day${day}"))
    val (test, result) = inputs.map {
        listOf(
            measureTimedValue {
                part1(it)
            },
            measureTimedValue {
                part2(it)
            }
        )
    }

    println("======AoC Day $day======")
    test.forEachIndexed { index, it ->
        val expected = testExpected[index]
        println("Test ${index + 1} is ${if (it.value == expected) '✅' else "❌ expected $expected but is ${it.value}"} in ${it.duration}")
    }
    println("----------------------")
    result.forEachIndexed { index, it ->
        println("Result ${index + 1} is ${it.value} in ${it.duration} ")
    }
    println("======================")
}

inline fun <T> List<T>.splitWhen(predicate: (T) -> Boolean): List<List<T>> =
    foldIndexed(mutableListOf<MutableList<T>>()) { index, list, string ->
        when {
            predicate(string) -> if (index < size - 1 && !predicate(get(index + 1))) list.add(mutableListOf())
            list.isNotEmpty() -> list.last().add(string)
            else -> list.add(mutableListOf(string))
        }
        list
    }