package util.game

/**
 * Created by jbarnes on 14/06/2016.
 */
enum class GameValue {
    X, O, EMPTY, SUGGESTION
}

fun parsePosition(char: String): GameValue {
    return when (char) {
        "X", "x" -> GameValue.X
        "O", "o" -> GameValue.O
        "-" -> GameValue.EMPTY
        "*" -> GameValue.SUGGESTION
        else -> GameValue.EMPTY
    }
}

/**
 * Prints a game board to string. Assumes non jagged 2d array
 */
fun printGameBoard(board: Array<Array<GameValue>>): String {
    var printed: String = "";
    for (i in 0..(board.size - 1)) {
        for (j in 0..board[0].size - 1) {
            printed += when (board [i][j]) {
                GameValue.X -> "X"
                GameValue.O -> "O"
                GameValue.EMPTY -> "-"
                GameValue.SUGGESTION -> "*"
            }
        }
        printed += "\n"
    }
    return printed
}