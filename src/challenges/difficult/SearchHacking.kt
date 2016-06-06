package challenges.difficult

import java.io.Console
import java.io.File

/**
 * Created by jbarnes on 05/06/2016.
 * Challenge: https://www.reddit.com/r/dailyprogrammer/comments/47o4b6/20160226_challenge_255_hard_hacking_a_search/
 */
val segmentTracker = SegmentTracker();

fun main(args: Array<String>) {
    var lines = File("C:\\dump\\oneliners.txt").readLines()
//    var lines = File("C:\\dump\\onelinerssmallx.txt").readLines()
//    var lines = File("C:\\dump\\control.txt").readLines()
    var segmentedLines: MutableList<SegmentedLine> = mutableListOf()

    for (i in lines.indices) {
        val stripped = stripPunctuationWhitespace(lines[i].toLowerCase())
        val segments = segmentString(stripped, 5)
        segmentedLines.add(SegmentedLine(i, segments, stripped))
    }

    val commonSegments: MutableList<CommonSegment> = mutableListOf();

    for (i in segmentedLines.indices) { //for each line
        for (segment in segmentedLines[i].segments) { //check each segment
            for (segmentedLine in segmentedLines) { //against each other line's segments
                if (segmentedLine.orig.contains(segment)) {
                    segmentTracker.addSegment(segment, segmentedLine.lineNumber)
                }
            }
        }
        println("$i of ${segmentedLines.indices}")
    }

    val coveragelist = segmentTracker.getFullCoverage(lines.size);
    println(coveragelist.size)
    coveragelist.sortedBy { it.segment }.forEach { println(it.segment) }
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

class SegmentTracker(val commonSegments: MutableList<CommonSegment> = mutableListOf()) {

    val size: Int
        get() = commonSegments.size;

    fun addSegment(segment: String, lineNumber: Int) {
        var commonSegment = findSegment(segment)
        if (commonSegment == null) {
            commonSegment = CommonSegment(segment);
            commonSegments.add(commonSegment)
        }
        commonSegment.lineNumbers.add(lineNumber);

    }

    fun findSegment(segment: String): CommonSegment? {
        return commonSegments.find { it.segment == segment }
    }

    fun containsSegment(segment: String): Boolean {
        return commonSegments.filter { it.segment == segment }.size > 0
    }

    fun getFullCoverage(lineCount: Int): List<CommonSegment> {
        var coverage: BooleanArray = BooleanArray(lineCount)
        var coverageBuffer: BooleanArray = BooleanArray(lineCount)
        coverage.fill(false)
        coverageBuffer.fill(false)
        commonSegments.sortBy { it.lineNumbers.size }
        commonSegments.reverse();

        val segments: MutableList<CommonSegment> = mutableListOf();

        loop@for (commonSegment in commonSegments) {
            for (lineNumber in commonSegment.lineNumbers) {
                coverageBuffer[lineNumber] = true;
            }
            if (coverageBuffer.count { it == true } > coverage.count { it == true }) {
                coverage = coverageBuffer.copyOf()
                segments.add(commonSegment)
            } else {
                coverageBuffer = coverage.copyOf()
            }

            if (coverage.count { it == true } == lineCount) {
                break@loop;
            }
            val trueCount = coverage.count { it == true }
            println("Coverage: $trueCount / $lineCount (${(trueCount.toDouble() / lineCount.toDouble()) * 100.00}%)")

        }
        val trueCount = coverage.count { it == true }
        println("Coverage: $trueCount / $lineCount (${(trueCount.toDouble() / lineCount.toDouble()) * 100.00}%)")
        return segments;
    }
}

data class SegmentedLine(val lineNumber: Int, val segments: MutableSet<String>, val orig: String)
data class CommonSegment(val segment: String, val lineNumbers: MutableSet<Int> = mutableSetOf())