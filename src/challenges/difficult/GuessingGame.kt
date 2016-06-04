package challenges.difficult

/**
 * Created by jbarnes on 04/06/2016.
 * Challenge: https://www.reddit.com/r/dailyprogrammer/comments/pii6j/difficult_challenge_1/
 */

fun main(args:Array<String>) {
    Guesser().begin();
}

class Guesser(range:IntRange) {

    private var range = range;
    private var upperLimit:Int = range.last;
    private var lowerLimit:Int = range.first;
    private var turnCount:Int = 0;
    private var lastGuess:Int = 0;
    private var lastResponse:Response = Response.UNKNOWN;

    constructor():this(1..100)

    fun begin() {
        printIntro();
        while (lastResponse != Response.CORRECT) {
            lastResponse = presentNextGuess();
            turnCount++;
            if (lastResponse == Response.CORRECT) {
                println("I guessed your number in $turnCount turns.");
            }
            if (lastResponse == Response.UNKNOWN) {
                println("I'm sorry, I didn't understand that.");
            }
        }
    }

    fun calcNextGuess():Int {
        var roundingFactor = 0;
        if (lastResponse == Response.HIGHER) {
            lowerLimit = lastGuess;
            roundingFactor = 1; //compensates for rounding down
        } else if (lastResponse == Response.LOWER) {
            upperLimit = lastGuess;
        }
        return (lowerLimit + ((upperLimit - lowerLimit) / 2)) + roundingFactor;
    }

    fun presentNextGuess():Response {
        val nextGuess = calcNextGuess();
        println("Is your number $nextGuess?");
        val response = readLine();
        lastGuess = nextGuess;
        return parseResponse(response);
    }

    fun parseResponse(response:String?):Response {
        when {
            response.equals("higher", ignoreCase = true) ||response.equals("h", ignoreCase = true) ->
                return Response.HIGHER;
            response.equals("lower", ignoreCase = true) || response.equals("l", ignoreCase = true) ->
                return Response.LOWER;
            response.equals("yes", ignoreCase = true) || response.equals("y", ignoreCase = true) ->
                return Response.CORRECT
            else -> return Response.UNKNOWN;
        }
    }


    fun printIntro() {
        println("Think of a number between 1 and 100. I am going to try and guess it.");
        println("If i guess correctly respond with 'yes' (or 'y')");
        println("Otherwise respond to with either 'higher' (or 'h') or 'lower' (or 'l')");
        println("Okay, here we go...");
    }

    enum class Response {
        HIGHER, LOWER, CORRECT, UNKNOWN
    }
}

