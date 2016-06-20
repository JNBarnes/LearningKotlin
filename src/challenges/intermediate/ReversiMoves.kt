package challenges.intermediate


import util.direction.Direction
import util.game.GameValue
import util.arrays.array2d
import util.direction.Point
import util.direction.transformPoint
import util.game.parsePosition
import util.game.printGameBoard


/**
 * Created by jbarnes on 06/06/2016.
 * Challenge: https://www.reddit.com/r/dailyprogrammer/comments/468pvf/20160217_challenge_254_intermediate_finding_legal/
 */

val input = """
            X
            --------
            --------
            ---OX---
            --XXXO--
            --XOO---
            ---O----
            --------
            --------
            """.trimIndent().lines();

val board = array2d(8, 8) { GameValue.EMPTY }
var legalMoveCount: Int = 0;


fun main(args: Array<String>) {
    parseGameBoard(input)
    markLegalMoves()
    println("$legalMoveCount legal moves for ${getActivePlayer()}")
    println(printGameBoard(board))
    board.forEach { }

}

fun parseGameBoard(input: List<String>) {
    for (i in 1..(input.size - 1)) {
        for (j in 0..7) {
            board[i - 1][j] = parsePosition(input[i][j].toString())
        }
    }
}

fun getActivePlayer(): GameValue {
    return GameValue.valueOf(input[0])
}

fun getOtherPlayer(): GameValue {
    return if (getActivePlayer() == GameValue.X) GameValue.O else GameValue.X
}

fun markLegalMoves() {
    val activePlayer = getActivePlayer()
    for (i in 1..(board.size - 1)) {
        for (j in 0..7) { // for each position
            if (board[i][j] == activePlayer) //only start with players tile
                loop@for (direction in Direction.values()) { //look in all directions
                    val suggestion: Point? = findSuggestionInDirection(direction, Point(i, j))
                    if (suggestion != null) {
                        board[suggestion.x][suggestion.y] = GameValue.SUGGESTION
                        legalMoveCount++
                    }
                }
        }
    }
}

/**
 * Keeps looking in a given direction until a blank tile is found.
 * Returns True if blank tile found, else False.
 */
fun findSuggestionInDirection(direction: Direction, position: Point, depth: Int = 0): Point? {
    val next = transformPoint(position, direction);
    try {
        return when (board[next.x][next.y]) {
            getOtherPlayer() -> findSuggestionInDirection(direction, next, depth + 1)
            GameValue.EMPTY -> if (depth > 0) next else null
            else -> null
        }
    } catch(e: IndexOutOfBoundsException) {
        return null
    }
}



