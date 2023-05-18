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
    public Token current() {return currentToken;}

    /**
     * Check whether tokenizer still has tokens left
     * @return type: boolean
     */
    public boolean hasNext() {
        return currentToken != null;
    }

    //Tokenizer constructor
    public Tokenizer(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Text cannot be null");
        }
        tokenizer = new StringTokenizer(text, ",");
        next();
    }

    /**
     * Creates tokens
     * @author Steven and Lana
     */
    public void next() {
        Colleges collegeList = new Colleges();
        if (tokenizer.hasMoreTokens()) {
            String delta = tokenizer.nextToken().trim().toLowerCase();
            if (isValidInteger(delta)) { //CODE
                currentToken = new Token(delta, Token.Type.CODE);
            }
            else if(collegeList.contains(getCollegeString(delta)) && isValidInteger(getIntegerString(delta))) { //COLLEGECODE
                currentToken = new Token(delta, Token.Type.COLLEGECODE);
            }
//            else if(delta.contains("college=") && collegeList.contains(delta.split(" ")[1])) { //COLLEGE
//                currentToken = new Token(delta, Token.Type.COLLEGE);
//            }
            else if(collegeList.contains(delta)) {
                currentToken = new Token(delta, Token.Type.COLLEGE);
            }
            else if(delta.contains("convener=") ) {
                currentToken = new Token(delta, Token.Type.CONVENER);
            }
            else if(isLetters(delta)) {
                currentToken = new Token(delta, Token.Type.COURSE);
            }
            else if (delta.contains("(") || delta.contains(")")) {
                // ignore
                //next();
                 throw new IllegalArgumentException("Expected CODE, COLLEGE, COLLEGECODE, CONVENER, or COURSE, found: " + delta);
            }

        } else {
            currentToken = null;
        }
    }

    /**
     * Extracts numbers from a string
     * @param input input string
     * @return a string of numerical values
     * @author Steven
     */
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

    /**
     * Extracts characters from a string
     * @param input input string
     * @return a string containg only letters
     * @author Lana
     */
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

    /**
     * Determines if a string is a number
     * @param input input string
     * @return boolean
     * @author Steven
     */
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

    /**
     * Extracts numbers from a string
     * @param input input string
     * @return a string contagion letters and spaces
     * @author Lana
     */
    public boolean isLetters(String input) {
        char[] chars = input.toCharArray();
        for (char c : chars) {
            if(!Character.isLetter(c) && !Character.isSpaceChar(c)) {
                return false;
            }
        }
        return true;
    }

}
