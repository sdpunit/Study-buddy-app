package com.studybuddy;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchToken {
    private static final Pattern TERM_PATTERN = Pattern.compile("[a-zA-Z]+");
    private static final Pattern OPERATOR_PATTERN = Pattern.compile("AND|OR");

    public List<String> tokenize(String input) {
        List<String> tokens = new ArrayList<>();

        Matcher termMatcher = TERM_PATTERN.matcher(input);
        Matcher operatorMatcher = OPERATOR_PATTERN.matcher(input);

        while (termMatcher.find()) {
            tokens.add(termMatcher.group());
        }

        while (operatorMatcher.find()) {
            tokens.add(operatorMatcher.group());
        }

        return tokens;
    }
}
