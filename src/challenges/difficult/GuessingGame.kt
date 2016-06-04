package challenges.difficult

/**
 * Created by jbarnes on 04/06/2016.
 * Challenge: https://www.reddit.com/r/dailyprogrammer/comments/pii6j/difficult_challenge_1/
 */

fun main(args: Array<String>) {
    Guesser().begin();
}

class Guesser(range: IntRange = 1..100) {


    private var upperLimit: Int = range.last;
    private var lowerLimit: Int = range.first;
    private var turnCount: Int = 0;
    private var lastGuess: Int = 0;
    private var lastResponse: Response = Response.UNKNOWN;

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

    fun calcNextGuess(): Int {
        var roundingFactor = 0;

        when (lastResponse) {
            Response.HIGHER -> {
                lowerLimit = lastGuess;
                roundingFactor = 1; //compensates for rounding down
            }
            Response.LOWER -> {
                upperLimit = lastGuess;
            }
        }
        return (lowerLimit + ((upperLimit - lowerLimit) / 2)) + roundingFactor;
    }

    fun presentNextGuess(): Response {
        val nextGuess = calcNextGuess();
        println("Is your number $nextGuess?");
        val response = readLine();
        lastGuess = nextGuess;
        return parseResponse(response);
    }

    fun parseResponse(response: String?): Response {
        return when (response?.toLowerCase()) {
            "higher", "h" -> Response.HIGHER;
            "lower", "l" -> Response.LOWER;
            "yes", "y" -> Response.CORRECT;
            else -> Response.UNKNOWN
        }
    }


    fun printIntro() {
        println("""
        Think of a number between 1 and 100. I am going to try and guess it.
        If i guess correctly respond with 'yes' (or 'y')"
        Otherwise respond to with either 'higher' (or 'h') or 'lower' (or 'l')
        Okay, here we go...
        """.trimIndent());
    }

    enum class Response {
        HIGHER, LOWER, CORRECT, UNKNOWN
    }
}

