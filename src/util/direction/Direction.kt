package util.direction

/**
 * Created by jbarnes on 14/06/2016.
 */

fun transformPoint(point: Point, direction: Direction): Point {
    with(point) {
        return when (direction) {
            Direction.UP -> Point(x, y + 1)
            Direction.DOWN -> Point(x, y - 1)
            Direction.LEFT -> Point(x - 1, y)
            Direction.RIGHT -> Point(x + 1, y)
            Direction.UP_LEFT -> Point(x - 1, y + 1)
            Direction.UP_RIGHT -> Point(x + 1, y + 1)
            Direction.DOWN_LEFT -> Point(x - 1, y - 1)
            Direction.DOWN_RIGHT -> Point(x + 1, y - 1)
        }
    }
}

data class Point(var x: Int = 0, var y: Int = 0)
enum class Direction {
    UP, DOWN, LEFT, RIGHT,
    UP_LEFT, UP_RIGHT,
    DOWN_LEFT, DOWN_RIGHT
}