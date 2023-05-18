package com.studybuddy.search;

public class SearchParser {
    Tokenizer tokenizer;

    public SearchParser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    /**
     * validates the input and returns the parsed expression.
     * @return Query object as a result of parsing the input.
     * @author Steven and Lana (u7103031) 
     */
    public Query parseQuery() {
        Query query = new Query();
        Token currentToken = tokenizer.current(); //get the current token
        if (tokenizer.hasNext() && (currentToken != null)) { //if there is a next token
            if (currentToken.getType() == Token.Type.COLLEGE) { //if the current token is a college, first token must be a college
                query.setCollege(currentToken.getToken()); //set the college
                tokenizer.next(); //move to the next token

            } else if (currentToken.getType() == Token.Type.COLLEGECODE) {
                if (currentToken.getToken().split(" ").length==1) { //if COLLLEGECODE is separated by a space
                    query.setCollege(currentToken.getToken().trim().substring(0,4)); //set the college
                    query.setCode(Integer.parseInt(currentToken.getToken().trim().substring(4))); //set the code
                }
                else { // if no space
                    query.setCollege(currentToken.getToken().split(" ")[0].trim()); //set the college
                    query.setCode(Integer.parseInt(currentToken.getToken().split(" ")[1].trim())); //set the code
                }
                tokenizer.next(); //move to the next token
            }
            return parseTerm(query); //parse the rest, (term)
        }
        throw new IllegalArgumentException("You entered a null token: " + tokenizer.current());
    }

    /**
     * validates the input and returns the continued term following the grammar requiremnts.
     * @return Query object as a result of parsing the input.
     * @Author Steven (u7108792)
     */
    private Query parseTerm(Query query) {

        if (tokenizer.hasNext()) {
            Token currentToken = tokenizer.current();
            if (currentToken.getType() == Token.Type.CODE && query.getCode() == 0) {
                if(query.getCode() == 0) {
                    query.setCode(Integer.parseInt(currentToken.getToken()));
                    tokenizer.next();
                    return parseTerm(query);
                }
                else {
                    return query;
                }

            } else if (currentToken.getType() == Token.Type.COURSE) {
                query.setCourse(currentToken.getToken());
                tokenizer.next();
                return parseTerm(query);

            } else if (currentToken.getType() == Token.Type.CONVENER) {
                query.setConvener(currentToken.getToken().split("=")[1].trim());
                tokenizer.next();
                return parseTerm(query);

            } else {
                throw new IllegalArgumentException("Expected CODE, COURSE, or CONVENER got: " + tokenizer.current().getToken());
            }
        }
        return query;
    }

}
