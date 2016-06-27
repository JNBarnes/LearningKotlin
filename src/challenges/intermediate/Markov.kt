package challenges.intermediate

import util.random.randomInt
import java.util.*

/**
 * Created by jbarnes on 20/06/2016.
 * Challenge:
 */

fun main(args: Array<String>) {
    Markov(3, """
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
    var nGramMap: MutableMap<NGram, MutableList<String>> = mutableMapOf()
    val NON_WORD = "NON_WORD"

    init {
        input = trainingSet.split(Regex("""\b""")).toMutableList()
        input = input.filter {
            Regex("""\w*""").matches(it)
        } as MutableList<String>

        for (i in 0..prefixLength - 1) {
            input.add(i, NON_WORD)
        }
        input.add(NON_WORD)// last ngram's suffix

        buildNGramList()
        nGramMap;
    }

    fun buildNGramList() {
        for (i in 0..input.size - prefixLength - 1) {
            val prefixes = getPrefixAsArray(i)
            val suffixes = nGramMap.getOrPut(NGram(prefixes)) {
                mutableListOf()
            }
            suffixes.add(input[i + prefixLength])
        }
    }

    fun getPrefixAsArray(startingIndex: Int): Array<String> {
        val prefixes = Array<String>(prefixLength, { "" })
        for (i in 0..prefixLength - 1) {
            prefixes[i] = input[startingIndex + i]
        }
        return prefixes
    }

    fun getPrefixList(startingIndex: Int): MutableList<String> {
        val prefixList: MutableList<String> = mutableListOf()
        for (i in 0..prefixLength - 1) {
            prefixList.add(input[startingIndex + i])
        }
        return prefixList
    }

    fun generateText(maxWordCount: Int = input.size): String {
        var output = ""
        var wordCount = 0;
        var prefixes = getPrefixAsArray(randomInt(0..input.size - prefixLength))
        while (wordCount < maxWordCount) {
            var suffix: String
            val suffixes = nGramMap[NGram(prefixes)]

            if (suffixes != null) {
                suffix = suffixes[randomInt(0..suffixes.size)]
            } else {
                suffix = input[randomInt(0..input.size)]
                println("No suffix found, selecting random word.")
            }
            output = appendToOutput(suffix, output)
            prefixes = prefixes.plus(suffix)
            prefixes = prefixes.sliceArray(1..prefixes.size - 1)
            wordCount++
        }
        return output
    }

    fun appendToOutput(append: String, output: String): String {
        return if (!append.equals(NON_WORD)) "$output $append" else "$output"
    }

    data class NGram(val prefixes: Array<String>) {
        override fun equals(other: Any?): Boolean {
            if (other is NGram) {
                return Arrays.equals(prefixes, other.prefixes)
            } else return false
        }

        override fun hashCode(): Int {
            return Arrays.hashCode(prefixes)
        }
    }
}

