package util.random

import util.range.span
import java.util.*

/**
 * Created by jbarnes on 21/06/2016.
 */

fun randomInt(range:IntRange):Int{
    val rng = Random(System.nanoTime())
    return range.first + rng.nextInt(range.span())
}