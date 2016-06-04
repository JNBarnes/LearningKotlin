package challenges.easy

import java.io.File

/**
 * Created by jbarnes on 04/06/2016.
 * Challenge: https://www.reddit.com/r/dailyprogrammer/comments/pih8x/easy_challenge_1/
 */
fun main(args: Array<String>) {
    val name:String = getName();
    val age:Int = getAge();
    val username:String = getUsername();

    val greeting = "Your name is $name, you are $age years old and your username is $username";

    println(greeting);
    writeToFile(greeting);
}

fun getAge():Int{
    println("How old are you?");
    val buffer = readLine();
    if (buffer.isNullOrBlank()) {
        println("Dont want to say huh? Fine, I'm going to assume you are 7. Yep. 7.");
        return 7;
    }else{
        try{
            return buffer!!.toInt(); //already checked for null
        }catch(e:NumberFormatException){
            println("You cant even type properly, you must be senile. You are 103 years old.");
            return 103;
        }
    }
}

fun getName():String{
    println("What is your name?");
    val buffer = readLine();
    if (buffer.isNullOrBlank()) {
        println("Oooo Mysterious. Spooooooky");
        return "a mystery to all"
    }else{
        return buffer.orEmpty();
    }
}

fun getUsername():String{
    println("What is your username?");
    val buffer = readLine();
    if (buffer.isNullOrBlank()) {
        println("You don't want to tell me? Fine.");
        return "MysteriousStranger"
    }else{
        return buffer.orEmpty();
    }
}

fun writeToFile(greeting:String){
    File("saved_greetings.txt").printWriter().use{
        it.println(greeting);
    }
}