import kotlin.math.max

class Draw(val red: Int, val green: Int, val blue: Int) {
    companion object {
        fun parse(rgb: String): Draw {
            val chunks = rgb.split(",")
                .map { piece -> piece.trim().split(" ", limit = 2) }
                .map { p -> Pair(p[1], p[0].toInt()) }
            val red = chunks.find { c -> c.first == "red" }?.second ?: 0
            val green = chunks.find { c -> c.first == "green" }?.second ?: 0
            val blue = chunks.find { c -> c.first == "blue" }?.second ?: 0

            return Draw(red, green, blue)
        }
    }
}

class RGBCount(val red: Int, val green: Int, val blue: Int)

class Game(val id: Int, private val draws: List<Draw>) {
    fun possible(red: Int, green: Int, blue: Int): Boolean {
        val impossibleDraw = draws.firstOrNull() { d -> d.red > red || d.green > green || d.blue > blue }

        return impossibleDraw != null
    }

    fun minCubes(): RGBCount = draws.fold(RGBCount(0, 0, 0)) { acc, draw ->
        RGBCount(
            max(acc.red, draw.red),
            max(acc.green, draw.green),
            max(acc.blue, draw.blue)
        )
    }

    fun minPower(): Int = power(minCubes())

    companion object {
        // The power of a set of cubes is equal to the numbers of red, green, and blue cubes multiplied together.
        fun power(count: RGBCount) = count.red * count.green * count.blue

        fun parse(line: String): Game {
            // Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
            val (game, draws) = line.split(":", limit = 2)
            val id = game.removePrefix("Game ").toInt()

            val drawsParsed = draws.split(";").map { d -> Draw.parse(d) }

            return Game(id, drawsParsed)
        }
    }
}

fun main() {

    fun part1(input: List<String>): Int {
        /*
        * Determine which games would have been possible if the bag had been loaded with only
        * 12 red cubes, 13 green cubes, and 14 blue cubes.
        * What is the sum of the IDs of those games?
        */
        val games = input.map { l -> Game.parse(l) }
        return games.filterNot { g -> g.possible(12, 13, 14) }.sumOf { g -> g.id }
    }

    fun part2(input: List<String>): Int {
        val games = input.map { l -> Game.parse(l) }

        return games.sumOf { g -> g.minPower() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}