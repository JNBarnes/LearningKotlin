package challenges.difficult




import util.arrays.array2d
import util.game.GameValue

import util.direction.Direction
import util.direction.Point
import util.direction.transformPoint
import util.game.parsePosition
import util.game.printGameBoard

/**
 * Created by jbarnes on 14/06/2016.
 * Challenge: https://www.reddit.com/r/dailyprogrammer/comments/4nga90/20160610_challenge_270_hard_alien_invasion/
 */
fun main(args: Array<String>) {
    AlienInvasion().main(args)
}

class AlienInvasion {
    val input = """
            8
            --X----X
            -----X--
            X--X----
            --X-----
            X--X----
            XXXX----
            --X-----
            --X---X-
            """.trimIndent().lines();

    lateinit var field: Array<Array<GameValue>>

    fun main(args: Array<String>) {
        val fieldSize = getFieldSize(input)
        field = array2d(fieldSize, fieldSize) { GameValue.EMPTY }
        parseField(input)
        println(printGameBoard(field))
    }

    fun parseField(input: List<String>) {
        for (i in 1..(input.size - 1)) {
            for (j in 0..7) {
                field[i - 1][j] = parsePosition(input[i][j].toString())
            }
        }
    }

    fun getFieldSize(input: List<String>): Int {
        return input[0].toInt()
    }
}
