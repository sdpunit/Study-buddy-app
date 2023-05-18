package com.studybuddy.search;

/**
 * This class represents a token in the search query.
 * @author Steven (u7108792) and Lana (u7103031)
 */
public class Token {

    public enum Type {COLLEGE, CODE, COURSE, CONVENER, COLLEGECODE}

    private final String token; // Token representation in String form.
    private final Type type;   // Type of the token.

    public Token(String token, Type type) {
        this.token = token;
        this.type = type;
    }
    public String getToken() {
        return token;
    }

    public Type getType() {
        return type;
    }

}
