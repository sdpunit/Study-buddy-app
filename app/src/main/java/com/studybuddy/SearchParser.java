package com.studybuddy;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.security.auth.Subject;

import kotlin.jvm.Throws;

public class SearchParser {
    Tokenizer tokenizer;

    int lb = 0;
    int rb = 0;

//    public SearchParser(List<String> tokens) {
//        this.tokens = tokens;
//        this.index = 0;
//    }

    public SearchParser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public static void main(String[] args) {
        // Create a scanner to get the user's input.
        Scanner scanner = new Scanner(System.in); //replace with editable text id later

        System.out.println("Provide a mathematical string to be parsed:");
        while (scanner.hasNext()) {
            String input = scanner.nextLine();

            // Check if 'quit' is provided.
            if (input.equals("q"))
                break;

            // Create an instance of the tokenizer.
            Tokenizer tokenizer = new Tokenizer(input);

//            // Print out the expression from the parser.
//            SearchParser parser = new SearchParser(tokenizer);
//            Exp expression = parser.parseExp();
//            System.out.println("Parsing: " + expression.show());
//            System.out.println("Evaluation: " + expression.evaluate());
        }
    }

    public Query parseQuery() {
        Query exp = parseExp();
        if (tokenizer.hasNext()) {
            Token currentToken = tokenizer.current();
            if (currentToken.getType() == Token.Type.CODE) {
                tokenizer.next();
            } else {
                throw new IllegalArgumentException("Expected SUBJECT all caps, found: " + tokenizer.getCurrent().getToken());
            }
        }
        return exp;
    }
    private Query parseExp() {
        Query subject = parseSubject();
        if (tokenizer.hasNext()){
            Token currentToken = tokenizer.current();
            if (currentToken.getType() == Token.Type.SUBJECT) {
                tokenizer.next();
            } else {
                throw new IllegalArgumentException("Expected SUBJECT all caps, found: " + tokenizer.getCurrent().getToken());
            }
        }

        return subject;
    }

    private Query parseSubject() {
        return null;
    }

    private void parseConvener() {
    }

    private void parseAnd() {
    }
}



//    public Subject parseSubject() throws IllegalProductionException {
//       if(tokenizer.hasNext()) {
//
//       }
//       else {
//           throw new IllegalArgumentException("Expected SUBJECT all caps, found: " + tokenizer.getCurrent().getToken());
//       }
//       return new Subject(tokenizer.getCurrent().getToken());
//    }
//
//    public String parseCode() {
//        String code = tokens.get(index);
//        index++;
//        return code;








////    public SearchQuery parse() {
////        SearchQuery query = new SearchQuery();
////
////        query.addTerm(parseTerm());
////
////        while (index < tokens.size()) {
////            String operator = parseOperator();
////            String term = parseTerm();
////            query.addTerm(term, operator);
////        }
////
////        return query;
////    }
//    public String parse(List<String> tokens) {
//        List<String> query = new ArrayList<String>();
//
//        query.add(parseTerm());
//        while (index < tokens.size()) {
//            String operator = parseOperator();
//            String term = parseTerm();
//            query.add(term);
//            query.add(operator);
//        }
//
//        return query.toString();
//    }
//
//    private String parseOperator() {
//        if (index >= tokens.size()) {
//            throw new IllegalArgumentException("Expected operator, found end of input");
//        }
//
//        String operator = tokens.get(index++);
//
//        if (!operator.equals("AND") && !operator.equals("OR")) {
//            throw new IllegalArgumentException("Expected operator, found " + operator);
//        }
//
//        return operator;
//    }
//
//    private String parseTerm() {
//        if (index >= tokens.size()) {
//            throw new IllegalArgumentException("Expected term, found end of input");
//        }
//
//        String term = tokens.get(index++);
//
//        if (!term.matches("[a-zA-Z]+")) {
//            throw new IllegalArgumentException("Expected term, found " + term);
//        }
//
//        return term;
//    }