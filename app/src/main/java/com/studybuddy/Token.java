package com.studybuddy;

import java.util.HashMap;

public class Token {

    public enum Type {SUBJECT, CODE, COURSE, CONVENER, AND, OR}

    private final String token; // Token representation in String form.
    private final Type type;    // Type of the token.

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


    // create new hashmap to store the token and its type
}
