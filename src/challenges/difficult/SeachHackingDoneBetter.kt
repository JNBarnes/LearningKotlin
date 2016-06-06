package challenges.difficult.searchhackingv2

import challenges.difficult.CommonSegment
import java.io.File
import java.util.*
import kotlin.system.measureTimeMillis

/**
 * Created by jbarnes on 06/06/2016.
 */

fun main(args: Array<String>) {
    var lines = File("C:\\dump\\oneliners.txt").readLines()
//    var lines = File("C:\\dump\\onelinerssmallx.txt").readLines()
//    var lines = File("C:\\dump\\control.txt").readLines()
    var strippedLines: MutableList<String> = mutableListOf()

    var segments: MutableSet<String> = mutableSetOf()
    var matchedSegments: HashMap<String, MutableList<Int>> = hashMapOf()

    for (i in lines.indices) {
        val stripped = stripPunctuationWhitespace(lines[i].toLowerCase())
        strippedLines.add(stripped)
        val lineSegments = segmentString(stripped, 5)
        segments.addAll(lineSegments)
    }


    var timeElapsed = measureTimeMillis {
        for (segment in segments) {
            for (i in strippedLines.indices) {
                if (strippedLines[i].contains(segment)) {
                    var lineMatches = matchedSegments.getOrPut(segment) { mutableListOf() }
                    lineMatches.add(i)
                }
            }
        }
    }

    println("Matched ${segments.size} to ${lines.size} in ${timeElapsed}ms")

    println("Reducing Matches")

    var matches = getCoverage(lines.size, matchedSegments)

    println("Reduced Matches to ${matches.size}")

}

fun stripPunctuationWhitespace(text: String): String {
    val regex = Regex(pattern = """[^a-z]""")
    return regex.replace(text, "") //remove non letters
}

fun segmentString(string: String, segmentLength: Int): MutableSet<String> {
    val segments: MutableSet<String> = hashSetOf()
    for (i in 0..string.length - (segmentLength)) {
        val segment = string.substring(i..i + segmentLength - 1)
        segments.add(segment)
    }
    return segments
}

fun getCoverage(lineCount: Int, matchedSegments: HashMap<String, MutableList<Int>>):MutableList<String>{
    var coverage: BooleanArray = BooleanArray(lineCount)
    var coverageBuffer: BooleanArray = BooleanArray(lineCount)
    coverage.fill(false)
    coverageBuffer.fill(false)
    var matchList = matchedSegments.toList();
    matchList = matchList.sortedBy { it.component2().size }
    matchList = matchList.reversed()

    val segments: MutableList<String> = mutableListOf();

    loop@for (match in matchList) {
        for (lineNumber in match.component2()) {
            coverageBuffer[lineNumber] = true;
        }
        if (coverageBuffer.count { it == true } > coverage.count { it == true }) {
            coverage = coverageBuffer.copyOf()
            segments.add(match.component1())
        } else {
            coverageBuffer = coverage.copyOf()
        }

        if (coverage.count { it == true } == lineCount) {
            break@loop;
        }


    }
    val trueCount = coverage.count { it == true }
    println("Coverage: $trueCount / $lineCount (${(trueCount.toDouble() / lineCount.toDouble()) * 100.00}%)")
    return segments;
}