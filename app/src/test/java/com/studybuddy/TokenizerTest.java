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
    private static final String errorToken = "wrong token";
    private static final String errorType = "wrong token type";

    @Test(timeout=1000)
    public void testTokenizeCode() {
        tokenizer = new Tokenizer(A);
        assertEquals(errorType, CODE, tokenizer.current().getType());
        assertEquals(errorToken, A, tokenizer.current().getToken());
    }

    @Test(timeout=1000)
    public void testTokenizeCollege() {
        tokenizer = new Tokenizer(B);
        assertEquals(errorType, COLLEGE, tokenizer.current().getType());
        assertEquals(errorToken, B, tokenizer.current().getToken());

        tokenizer = new Tokenizer(C);
        assertEquals(errorType, COLLEGE, tokenizer.current().getType());
        assertEquals(errorToken, C, tokenizer.current().getToken());
    }

    @Test(timeout=1000)
    public void testTokenizeCollegeCode() {
        tokenizer = new Tokenizer(D);
        assertEquals(errorType, COLLEGECODE, tokenizer.current().getType());
        assertEquals(errorToken, D, tokenizer.current().getToken());
    }

    @Test(timeout=1000)
    public void testTokenizeCourse() {
        tokenizer = new Tokenizer(G);
        assertEquals(errorType, COURSE, tokenizer.current().getType());
        assertEquals(errorToken, G, tokenizer.current().getToken());
    }

    @Test(timeout=1000)
    public void testTokenizeConvener() {
        tokenizer = new Tokenizer(H);
        assertEquals(errorType, CONVENER, tokenizer.current().getType());
        assertEquals(errorToken, H, tokenizer.current().getToken());
    }

    @Test(timeout=1000)
    public void testTokenizeMixed() {
        tokenizer = new Tokenizer(E);
        assertEquals(errorType, CODE, tokenizer.current().getType());
        assertEquals(errorToken, "2100", tokenizer.current().getToken());
        tokenizer.next();
        assertEquals(errorType, COURSE, tokenizer.current().getType());
        assertEquals(errorToken, "Software Design Methodologies", tokenizer.current().getToken());
        tokenizer.next();
        assertEquals(errorType, COLLEGE, tokenizer.current().getType());
        assertEquals(errorToken, "COMP", tokenizer.current().getToken());

        tokenizer = new Tokenizer(F);
        assertEquals(errorType, COLLEGECODE, tokenizer.current().getType());
        assertEquals(errorToken, "COMP 2100", tokenizer.current().getToken());
        tokenizer.next();
        assertEquals(errorType, COURSE, tokenizer.current().getType());
        assertEquals(errorToken, "Software Design Methodologies", tokenizer.current().getToken());
        tokenizer.next();
        assertEquals(errorType, CONVENER, tokenizer.current().getType());
        assertEquals(errorToken, "convener= bernando", tokenizer.current().getToken());
    }
}
