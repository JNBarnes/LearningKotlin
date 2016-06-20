package util.arrays

/**
 * Created by jbarnes on 06/06/2016.
 */
inline fun <reified INNER> array2d(sizeOuter: Int, sizeInner: Int, noinline innerInit: (Int) -> INNER): Array<Array<INNER>> = Array(sizeOuter) { Array<INNER>(sizeInner, innerInit) }

fun array2dOfInt(sizeOuter: Int, sizeInner: Int): Array<IntArray> = Array(sizeOuter) { IntArray(sizeInner) }
fun array2dOfLong(sizeOuter: Int, sizeInner: Int): Array<LongArray> = Array(sizeOuter) { LongArray(sizeInner) }
fun array2dOfByte(sizeOuter: Int, sizeInner: Int): Array<ByteArray> = Array(sizeOuter) { ByteArray(sizeInner) }
fun array2dOfChar(sizeOuter: Int, sizeInner: Int): Array<CharArray> = Array(sizeOuter) { CharArray(sizeInner) }

inline fun <T> Array<Array<T>>.forEach2d(action: (T) -> Unit): Unit {
    for (outer in this) {
        for (inner in outer) {
            action(inner)
        }
    }
}
