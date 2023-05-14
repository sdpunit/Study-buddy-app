package com.studybuddy;


import static com.studybuddy.search.Token.Type.COLLEGE;
import static org.junit.Assert.assertEquals;

import com.studybuddy.search.Token;
import com.studybuddy.search.Tokenizer;

import org.junit.Test;

public class TokenizerTest {

    private static Tokenizer tokenizer;
    private static final String A = "2100";
    private static final String B = "COMP";
    private static final String C = "COMP, 2100";
    private static final String D = "2100 Software Design Methodologies COMP";
    private static final String E = "COMP 2100 Software Design Methodologies, bernando";
    private static final String F = "college= COMP";
    // add , as delimiter in search query or it might get too confusing.


    @Test(timeout=1000)
    public void testAddToken() {
//        tokenizer = new Tokenizer(A);
//        assertEquals("wrong token type, current token is "+tokenizer.getCurrent().getType(), Token.Type.CODE, tokenizer.getCurrent().getType());
//
//        tokenizer = new Tokenizer(B);
//        assertEquals("wrong token type, current token is "+tokenizer.getCurrent().getType(), COLLEGE, tokenizer.getCurrent().getType());

//        tokenizer = new Tokenizer(F);
//        assertEquals("wrong token type, current token is "+tokenizer.getCurrent().getType(), COLLEGE, tokenizer.getCurrent().getType());

        tokenizer = new Tokenizer(C);
        assertEquals("wrong token value", COLLEGE, tokenizer.getCurrent().getToken());
    }
}
