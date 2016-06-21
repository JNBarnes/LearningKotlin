package challenges.intermediate

import sun.security.util.Length
import util.random.randomInt
import java.util.*

/**
 * Created by jbarnes on 20/06/2016.
 * Challenge:
 */

fun main(args: Array<String>) {
    Markov(2, """
    Blessed are the poor in spirit,
    for theirs is the kingdom of heaven.
    Blessed are those who mourn,
    for they will be comforted.
    Blessed are the meek,
    for they will inherit the earth.
    Blessed are those who hunger and thirst for righteousness,
    for they will be filled.
    Blessed are the merciful,
    for they will be shown mercy.
    Blessed are the pure in heart,
    for they will see God.
    Blessed are the peacemakers,
    for they will be called sons of God.""")
}

class Markov(val prefixLength: Int = 2, trainingSet: String) {
    var input: MutableList<String>
    var nGrams: MutableList<NGram> = mutableListOf()
    val NON_WORD = "NON_WORD"

    init {
        input = trainingSet.split(Regex("""\b""")).toMutableList()
        input = input.filter {
            if (it != null) {
                (Regex("""\w*""").matches(it))
            } else false
        } as MutableList<String>

        for (i in 0..prefixLength - 1) {
            input.add(i, NON_WORD)
        }
        input.add(NON_WORD)// last ngram's suffix

        buildNGramList()
        nGrams
        println(generateText())
    }

    fun buildNGramList() {
        for (i in 0..input.size - prefixLength - 1) {
            var nGram = NGram(getPrefixList(i), mutableListOf(input[i + prefixLength]))
            var existing = nGrams.indexOf(nGram)
            if (existing >= 0) {
                nGrams[existing].suffixes.addAll(nGram.suffixes)
            } else {
                nGrams.add(nGram)
            }
        }
    }

    fun getPrefixList(startingIndex: Int): MutableList<String> {
        val prefixList: MutableList<String> = mutableListOf()
        for (i in 0..prefixLength - 1) {
            prefixList.add(input[startingIndex + i])
        }
        return prefixList
    }

    fun generateText(): String {
        var output = ""
        var suffixes = 0;
        var prefixList = getPrefixList(0)
        while (suffixes < input.size) {
            var nGram = findNGram(prefixList)
            if (nGram != null) {
                var suffix = nGram.suffixes[randomInt(0..nGram.suffixes.size)]
                output = appendToOutput(suffix, output)
                prefixList.add(suffix)
                prefixList = prefixList.subList(1, prefixList.size)

            }
            suffixes++
        }
        return output
    }

    fun appendToOutput(append: String, output: String): String {
        return if (!append.equals(NON_WORD)) "$output $append" else "$output"
    }

    fun findNGram(prefixes: MutableList<String>): NGram? {
        return try {
            nGrams.first { it.prefix.containsAll(prefixes) }
        } catch(e: NoSuchElementException) {
            null
        }
    }

    data class NGram(var prefix: MutableList<String>, var suffixes: MutableList<String>) {
        override fun equals(other: Any?): Boolean {
            if (other is NGram) {
                return other.prefix.containsAll(this.prefix)
            } else return false
        }
    }
}

