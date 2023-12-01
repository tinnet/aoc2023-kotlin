fun main() {

    fun firstDigit(input: String): Int {
        return input.first { c -> c.isDigit() }.toString().toInt()
    }

    fun lastDigit(input: String): Int {
        return input.last { c -> c.isDigit() }.toString().toInt()
    }

    fun part1(input: List<String>): Int {
        return input.map { l -> "${firstDigit(l)}${lastDigit(l)}" }.sumOf { n -> n.toInt() }
    }

    val digitStrings = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )

    val digitKeys = digitStrings.keys.joinToString("|")
    val digitRegex = "(?=($digitKeys|\\d))".toRegex()

    fun parseDigit(input: String): Int {
        return digitStrings.getOrElse(input) { input.toInt() }
    }

    fun findFirstLast(line: String): Pair<Int, Int> {
        val matches = digitRegex.findAll(line)

        // TODO how to get rid of the empty group for the lookahead?
        //return parseDigit(matches.first().value) to parseDigit(matches.last().value)
        return parseDigit(matches.first().groupValues.last()) to parseDigit(matches.last().groupValues.last())
    }

    fun part2(input: List<String>): Int {
        return input.asSequence().map { l -> findFirstLast(l) }
            .map { (first, last) -> "${first}${last}" }
            .sumOf {d -> d.toInt()}
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val testInput2 = readInput("Day01_part2_test")
    check(part2(testInput2) == 281)

    check(part2(listOf("nhp3zdc")) == 33)
    check(part2(listOf("3one8ncctmbsixeighttwonegb")) == 31)

        val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
