package com.studybuddy.search;

import static java.lang.Character.isUpperCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Tokenizer {

    private StringTokenizer tokenizer;

    private Token currentToken;

    public Token getCurrent() {
        return currentToken;
    }

    public Tokenizer(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Text cannot be null");
        }
        tokenizer = new StringTokenizer(text, ",");
        next();
    }

    public void next() {
        Colleges collegeList = new Colleges();
        if (tokenizer.hasMoreTokens()) {
            String delta = tokenizer.nextToken().trim().toLowerCase();


            if (isValidInteger(delta)) {
                currentToken = new Token(delta, Token.Type.CODE);
            }
            else if(collegeList.contains(getCollegeString(delta)) && isValidInteger(getIntegerString(delta))) { // change uppercase methods to check college list instead
                currentToken = new Token(delta, Token.Type.COLLEGECODE);
            }
            else if(delta.contains("college=") && collegeList.contains(delta.split(" ")[1])) {
                currentToken = new Token(delta, Token.Type.COLLEGE);
            }
            else if(collegeList.contains(delta)) {
                currentToken = new Token(delta, Token.Type.COLLEGE);
            }
            else if(delta.contains("convener") ) {
                currentToken = new Token(delta, Token.Type.CONVENER);
            }
            else if(isLetters(delta)) {
                currentToken = new Token(delta, Token.Type.COURSE);
            }
            else if (delta.contains("(") || delta.contains(")")) {
                // ignore
                next();
//                throw new IllegalArgumentException("Expected CODE, COLLEGE, COLLEGECODE, CONVENER, or COURSE, found: " + delta);
            }

        } else {
            currentToken = null;
        }
    }

    /*
    Methods	Description
        boolean hasMoreTokens()	        It checks if there is more tokens available.
        String nextToken()	            It returns the next token from the StringTokenizer object.
        String nextToken(String delim)	It returns the next token based on the delimiter.
        boolean hasMoreElements()	    It is the same as hasMoreTokens() method.
        Object nextElement()	        It is the same as nextToken() but its return type is Object.
        int countTokens()	            It returns the total number of tokens.
     */




//    private String buffer;          // String to be transformed into tokens each time next() is called.
//    private String[] tokens;
//
//    private int index = 0;          // Index of the current token in the tokens array.
//
//    private Token currentToken;     // The current token. The next token is extracted when next() is called.
//
//    public Token getCurrent() {
//        return currentToken;
//    }
//
//    public Tokenizer(String text) {
//        buffer = text;          // save input text (string)
//        tokens = buffer.split(","); // split the string into tokens (comma separated
//
//        next();                 // extracts the first token.
//    }
//
//
//    public void next() {
//        if (buffer == null || buffer.isEmpty()) {
//            currentToken = null;
//            return;
//        }
//
////       buffer.trim();
//        tokens[index] = tokens[index].trim();
//        buffer = tokens[index];
//        index++;
//
//        if (buffer.isEmpty()) {
//            next();
//        }
//
//        else if (isAllUpperCase(buffer) || buffer.contains("college=")) { // checks is college is all caps or contains college= if college is not all caps
//            currentToken = new Token(buffer.replace("college=",""), Token.Type.COLLEGE);
//        }
//        else if (isValidInteger(getIntegerString(buffer))) { // checks if the string contains an integer
//            currentToken = new Token(getIntegerString(buffer), Token.Type.CODE);
//        }
//        else if (buffer.contains("course:")) {
//            currentToken = new Token(buffer, Token.Type.COURSE);
//        }
//        else if (buffer.contains("convener=")) {
//            currentToken = new Token(buffer, Token.Type.CONVENER);
//        }
//        else {
//            throw new IllegalArgumentException(
//                    "Expected SUBJECT all caps || CODE as Integer || COURSE as Course Name || convener:Name , found: " + buffer);
//        }


    private boolean isAllUpperCase(String input) {
        boolean b = true;
        char[] ch = input.toCharArray();
        for (char c : ch) {
            if (!isUpperCase(c)){
                b = false;
                break;
            }
        }
        return b;
    }

    private boolean containsInteger(String input){
        boolean b = false;
        char[] ch = input.toCharArray();
        for (char c : ch) {
            if (Character.getNumericValue(c) >= 0 && Character.getNumericValue(c) <= 9){
                b = true;
                break;
            }
        }
        return b;
    }

    private String getIntegerString(String input){
        String s = "";
        char[] ch = input.toCharArray();
        for (char c : ch) {
            if (Character.getNumericValue(c) >= 0 && Character.getNumericValue(c) <= 9){
                s += c;
            }
        }
        return s;
    }

    private String getCollegeString(String input){
        String s = "";
        char[] ch = input.toCharArray();
        for (char c : ch) {
            if (Character.isLetter(c)){
                s += c;
            }
        }
        return s;
    }
    private boolean isValidInteger(String input) {
        char[] ch = input.toCharArray();
        if(ch.length>0 && ch[0] == '0'){
            return false;
        }
//        if(Character.getNumericValue(ch[0]) < 1 || Character.getNumericValue(ch[0]) > 7){
//            return false;
//        }
        if(ch.length != 4){
            return false;
        }

        try {
            Integer.parseInt(input);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }


    }

    public boolean isLetters(String name) {
        char[] chars = name.toCharArray();
        for (char c : chars) {
            if(!Character.isLetter(c) && !Character.isSpaceChar(c)) {
                return false;
            }
        }
        return true;
    }

    public Token current() {
        return currentToken;
    }

    /**
     * Check whether tokenizer still has tokens left
     * **** please do not modify this part ****
     *
     * @return type: boolean
     */
    public boolean hasNext() {
        return currentToken != null;
    }


}



//    public List<String> tokenize(String input) {
//        List<String> tokens = new ArrayList<>();
//
//        Matcher termMatcher = TERM_PATTERN.matcher(input);
//        Matcher operatorMatcher = OPERATOR_PATTERN.matcher(input);
//
//        while (termMatcher.find()) {
//            tokens.add(termMatcher.group());
//        }
//
//        while (operatorMatcher.find()) {
//            tokens.add(operatorMatcher.group());
//        }
//
//        return tokens;
//    }