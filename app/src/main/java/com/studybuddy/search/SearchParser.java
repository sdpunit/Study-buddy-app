package com.studybuddy.search;

import java.util.Scanner;

public class SearchParser {
    Tokenizer tokenizer;


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

    //flowing the grammar:
    /*
    college: COMP, code: 1110, name: Structured Programming, convener: Patrik Haslum"

        <exp>       ::= "college:" <college> | "college:" <college> "," <term>
        <term>      ::= <factor> | <factor> "," <factor> | <factor> "," <factor> "," <factor>
        <factor>    ::= <code> | <name> | <convener>
        <code>      ::= "code:" four-digit Integer
        <name>      ::= "name:" String
        <convener>  ::= "convener:" String
        <college>   ::= "COMP" | "MATH" | "PHYS" | "STATS" | ...

        There must be a 'college:' first. Otherwise, it should throw an exception saying that the
        user should specify the college first. If there are no other things in the query, it returns
        all the results in that tree. If a 'code:' is also provided, search for the exact node of
        the code. If 'college' and 'name' are provided, use that dictionary. if 'college' and
        'convener' are provided, use another dictionary. If 'code', 'name' and 'college' all exist
        or two of them exist, they will be processed separately, which means it search for the code
        and the name and the convener and returns all the not duplicate results.
     */


    // validates the input and returns the parsed expression.
    // @return Query object as a result of parsing the input.
    public Query parseQuery() {
        Query query = new Query();
        Token currentToken = tokenizer.current(); //get the current token
//        if(currentToken.getType() == Token.Type.CODE){
//            query.setCode(Integer.parseInt(currentToken.getToken())); //set the code
//            return query;
//        }
//        else
        if (tokenizer.hasNext()) { //if there is a next token
            if (currentToken.getType() == Token.Type.COLLEGE) { //if the current token is a college, first token must be a college
                query.setCollege(currentToken.getToken()); //set the college
                tokenizer.next(); //move to the next token
                parseTerm(query); //parse the rest, (term)
            } else {
                throw new IllegalArgumentException("Expected COLLEGE all caps, found: " + tokenizer.getCurrent().getToken());
            }
        }
        return null;
    }
    private Query parseTerm(Query query) {

        if (tokenizer.hasNext()) {
            Token currentToken = tokenizer.current();
            if (currentToken.getType() == Token.Type.CODE && query.getCode() == 0) {
                query.setCode(Integer.parseInt(currentToken.getToken()));
                tokenizer.next();
                return parseTerm(query);

            } else if (currentToken.getType() == Token.Type.COURSE) {
                query.setCourse(currentToken.getToken());
                tokenizer.next();
                return parseTerm(query);

            } else if (currentToken.getType() == Token.Type.CONVENER) {
                query.setConvener(currentToken.getToken());
                tokenizer.next();
                return parseTerm(query);

            } else {
                throw new IllegalArgumentException("Expected SUBJECT all caps, found: " + tokenizer.getCurrent().getToken());
            }
        }
        return query;
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