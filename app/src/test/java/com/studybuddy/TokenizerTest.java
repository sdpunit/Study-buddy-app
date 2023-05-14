package com.studybuddy;


import static com.studybuddy.search.Token.Type.CODE;
import static com.studybuddy.search.Token.Type.COLLEGE;
import static com.studybuddy.search.Token.Type.COLLEGECODE;
import static com.studybuddy.search.Token.Type.CONVENER;
import static com.studybuddy.search.Token.Type.COURSE;
import static org.junit.Assert.assertEquals;

import com.studybuddy.search.Token;
import com.studybuddy.search.Tokenizer;

import org.junit.Test;

public class TokenizerTest {

    private static Tokenizer tokenizer;
    private static final String A = "2100";
    private static final String B = "COMP";
    private static final String C = "college= COMP";
    private static final String D = "COMP 2100";
    private static final String E = "2100, Software Design Methodologies, COMP";
    private static final String F = "COMP 2100, Software Design Methodologies, convener= bernando";
    private static final String G = "Software Design Methodologies";
    private static final String H = "convener= bernando";
    private static final String errorToken = "wrong token, current token is ";
    private static final String errorType = "wrong token type, current token type is ";

    @Test(timeout=1000)
    public void testTokenizeCode() {
        tokenizer = new Tokenizer(A);
        assertEquals(errorType+tokenizer.current().getType(), CODE, tokenizer.current().getType());
        assertEquals(errorToken+tokenizer.current().getToken(), A, tokenizer.current().getToken());
    }

    @Test(timeout=1000)
    public void testTokenizeCollege() {
        tokenizer = new Tokenizer(B);
        assertEquals(errorType+tokenizer.current().getType(), COLLEGE, tokenizer.current().getType());
        assertEquals(errorToken+tokenizer.current().getToken(), B, tokenizer.current().getToken());

        tokenizer = new Tokenizer(C);
        assertEquals(errorType+tokenizer.current().getType(), COLLEGE, tokenizer.current().getType());
        assertEquals(errorToken+tokenizer.current().getToken(), C, tokenizer.current().getToken());
    }

    public void testTokenizeCollegeCode() {
        tokenizer = new Tokenizer(D);
        assertEquals(errorType+tokenizer.current().getType(), COLLEGECODE, tokenizer.current().getType());
        assertEquals(errorToken+tokenizer.current().getToken(), D, tokenizer.current().getToken());
    }

    @Test(timeout=1000)
    public void testTokenizeCourse() {
        tokenizer = new Tokenizer(G);
        assertEquals(errorType+tokenizer.current().getType(), COURSE, tokenizer.current().getType());
        assertEquals(errorToken+tokenizer.current().getToken(), G, tokenizer.current().getToken());
    }

    @Test(timeout=1000)
    public void testTokenizeConvener() {
        tokenizer = new Tokenizer(H);
        assertEquals(errorType+tokenizer.current().getType(), CONVENER, tokenizer.current().getType());
        assertEquals(errorToken+tokenizer.current().getToken(), H, tokenizer.current().getToken());
    }

    @Test(timeout=1000)
    public void testTokenizeMixed() {
        tokenizer = new Tokenizer(E);
        assertEquals(errorType+tokenizer.current().getType(), CODE, tokenizer.current().getType());
        assertEquals(errorToken+tokenizer.current().getToken(), "2100", tokenizer.current().getToken());
        tokenizer.next();
        assertEquals(errorType+tokenizer.current().getType(), COURSE, tokenizer.current().getType());
        assertEquals(errorToken+tokenizer.current().getToken(), "Software Design Methodologies", tokenizer.current().getToken());
        tokenizer.next();
        assertEquals(errorType+tokenizer.current().getType(), COLLEGE, tokenizer.current().getType());
        assertEquals(errorToken+tokenizer.current().getToken(), "COMP", tokenizer.current().getToken());

        tokenizer = new Tokenizer(F);
        assertEquals(errorType+tokenizer.current().getType(), COLLEGECODE, tokenizer.current().getType());
        assertEquals(errorToken+tokenizer.current().getToken(), "COMP 2100", tokenizer.current().getToken());
        tokenizer.next();
        assertEquals(errorType+tokenizer.current().getType(), COURSE, tokenizer.current().getType());
        assertEquals(errorToken+tokenizer.current().getToken(), "Software Design Methodologies", tokenizer.current().getToken());
        tokenizer.next();
        assertEquals(errorType+tokenizer.current().getType(), CONVENER, tokenizer.current().getType());
        assertEquals(errorToken+tokenizer.current().getToken(), "convener= bernando", tokenizer.current().getToken());
    }
}
