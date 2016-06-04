package Challenges

/**
 * Created by jbarnes on 03/06/2016.
 * Challenge: https://www.reddit.com/r/dailyprogrammer/comments/4jom3a/20160516_challenge_267_easy_all_the_places_your/
 */

fun main(args: Array<String>) {
    var output:String = "";
    var myPlace: Int? = try {
        args[0].toInt();
    } catch(e: NumberFormatException) {
        null;
    };

    if (myPlace != null) {
        for (i in 1..100) {
            if (i != myPlace) {
                output += "${i.toString()}${getSuffix(i)}, "
            }
        }
        print(output.substring(0..output.length-3)); //trim last comma + space and print
    }
}

fun getSuffix(num: Int): String {
    var suffix: String = "";
    if(num in 11..13){
        return "th";
    }
    when (num % 10) {
        0 -> suffix = "th";
        1 -> suffix = "st";
        2 -> suffix = "nd";
        3 -> suffix = "rd";
        in 4..9 -> suffix = "th";
    }
    return suffix;
}