package com.studybuddy;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class SearchParser {
    private List<String> tokens;
    private int index;

    public SearchParser(List<String> tokens) {
        this.tokens = tokens;
        this.index = 0;
    }

//    public SearchQuery parse() {
//        SearchQuery query = new SearchQuery();
//
//        query.addTerm(parseTerm());
//
//        while (index < tokens.size()) {
//            String operator = parseOperator();
//            String term = parseTerm();
//            query.addTerm(term, operator);
//        }
//
//        return query;
//    }

    private String parseTerm() {
        if (index >= tokens.size()) {
            throw new IllegalArgumentException("Expected term, found end of input");
        }

        String term = tokens.get(index++);

        if (!term.matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("Expected term, found " + term);
        }

        return term;
    }


}
