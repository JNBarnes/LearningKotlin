package challenges.difficult

import java.io.Console
import java.io.File

/**
 * Created by jbarnes on 05/06/2016.
 * Challenge: https://www.reddit.com/r/dailyprogrammer/comments/47o4b6/20160226_challenge_255_hard_hacking_a_search/
 */

fun main(args: Array<String>) {
    var lines = File("C:\\oneliners.txt").readLines()
    var segmentedLines: MutableList<SegmentedLine> = mutableListOf()
    val segmentTracker = SegmentTracker();

    for (i in lines.indices) {
        val stripped = stripPunctuationWhitespace(lines[i].toLowerCase())
        val segments = segmentString(stripped, 5)
        segmentedLines.add(SegmentedLine(i, segments))
    }

    for (i in segmentedLines.indices) { //for each line
        for (segment in segmentedLines[i].segments) { //check each segment
            for (j in segmentedLines.indices) {//against every other lines' segments
                if (i == j) continue;
                if (segmentedLines[j].segments.contains(segment)) {
                    segmentTracker.addSegment(segment, j)
                }
            }
        }
        println("$i of ${segmentedLines.indices}")
    }

    val coveragelist = segmentTracker.getFullCoverage(lines.size);
    println(coveragelist.size)
//    coveragelist.forEach { println(it.segment) }
}


fun stripPunctuationWhitespace(text: String): String {
    val regex = Regex(pattern = """[^a-z]""")
    return regex.replace(text, "") //remove non letters
}

fun segmentString(string: String, segmentLength: Int): MutableSet<String> {
    val segments: MutableSet<String> = hashSetOf()
    for (i in 0..string.length - (segmentLength + 1)) {
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

    fun getFullCoverage(lineCount: Int):List<CommonSegment> {
        val coverage: BooleanArray = BooleanArray(lineCount)
        coverage.fill(false)
        commonSegments.removeAll { it.lineNumbers.size < 2 }
        commonSegments.sortBy { it.lineNumbers.size }
        commonSegments.reverse();

        val segments :MutableList<CommonSegment> = mutableListOf();

       loop@for (i in commonSegments.indices){
            for(lineNumber in commonSegments[i].lineNumbers){
                coverage[lineNumber] = true;
            }
           segments.add(commonSegments[i])
           if (!coverage.contains(false)){
               break@loop
           }
        }
        val trueCount = coverage.count {it == true}
        println("Coverage: $trueCount / $lineCount (${(trueCount.toDouble()/lineCount.toDouble())*100.00}%)")
        return segments;
    }
}

data class SegmentedLine(val lineNumber: Int, val segments: MutableSet<String>)
data class CommonSegment(val segment: String, val lineNumbers: MutableSet<Int> = mutableSetOf())