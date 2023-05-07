package com.studybuddy;

import static java.lang.Character.isUpperCase;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class Tokenizer {

    private String buffer;          // String to be transformed into tokens each time next() is called.
    private Token currentToken;     // The current token. The next token is extracted when next() is called.

    public Token getCurrent() {
        return currentToken;
    }

    public Tokenizer(String text) {
        buffer = text;          // save input text (string)
        next();                 // extracts the first token.
    }


    public void next() {

        buffer = buffer.trim();     // remove whitespace

        if (buffer.isEmpty()) {
            currentToken = null;    // if there's no string left, set currentToken null and return
            return;
        }
        else if (isAllUpperCase(buffer)) {
            currentToken = new Token(buffer, Token.Type.SUBJECT);
        }
        else if (isInteger(buffer)) {
            currentToken = new Token(buffer, Token.Type.CODE);
        }
        else if (!isAllUpperCase(buffer) && !isInteger(buffer)) {
            currentToken = new Token(buffer, Token.Type.COURSE);
        }
        else if (buffer.contains("convener= ")) {
            currentToken = new Token(buffer, Token.Type.CONVENER);
        }
        else {
            throw new IllegalArgumentException(
                    "Expected SUBJECT all caps || CODE as Integer || COURSE as Course Name || convener:Name , found: " + buffer);
        }

    }
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
    private boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
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