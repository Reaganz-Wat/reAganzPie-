package com.example.todolistapp;

public class myFunctions {
    // This is the class for all the functions i want to reuse in the applicaions

    // for shortening the text
    public static String shortenText(String text, int maxLength){
        if (text.length() <= maxLength){
            return text;
        } else {
            return text.substring(0, maxLength) + "...";
        }
    }

    // You can define all other functions as well here ...

}
