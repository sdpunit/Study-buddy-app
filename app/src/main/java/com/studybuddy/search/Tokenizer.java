package com.studybuddy.search;

import static java.lang.Character.isUpperCase;

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
        if (buffer == null || buffer.isEmpty()) {
            currentToken = null;
            return;
        }

        buffer.trim();

        if(buffer.charAt(0) == ','){
            next();
        }

        else if (isAllUpperCase(buffer) && buffer.contains("college=")) {
            currentToken = new Token(buffer, Token.Type.COLLEGE);
        }
        else if (isValidInteger(getIntegerString(buffer))) {
            currentToken = new Token(getIntegerString(buffer), Token.Type.CODE);
        }
        else if (buffer.contains("course:")) {
            currentToken = new Token(buffer, Token.Type.COURSE);
        }
        else if (buffer.contains("convener=")) {
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
    private boolean isValidInteger(String input) {
        char[] ch = input.toCharArray();
        if(ch[0] == '0'){
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